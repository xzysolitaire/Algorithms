package aips;

/*
 * Davis-putnam Algorithm
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DP {
  private String inputFile;
  private String outputFile;
  //the value of the map: 1 --- true / 2 --- false
  private List<Map<Integer, Boolean>> clauses = 
      new ArrayList<Map<Integer, Boolean>>();
  private Set<Integer> atoms = new HashSet<Integer>();
  
  public DP(String inputFile, String outputFile) throws IOException {
    this.inputFile = inputFile;
    this.outputFile = outputFile;
    init();
  }
  
  class Pair {
    int key;
    String value;
    Pair(int key, String value) {
      this.key = key;
      this.value = value;
    }
  }
  
  //return false only when there is a conflict
  private boolean resolution(Map<Integer, Boolean> determined, 
                             List<Map<Integer, Boolean>> clause) {
    int i = 0;
    while (i < clause.size()) {
      int toremove = -1; 
      for (Integer key: clause.get(i).keySet()) {
        if (determined.containsKey(key)) {
          if (determined.get(key) == clause.get(i).get(key)) {
            clause.remove(i);
            i --;
          } else {
            if (clause.get(i).size() == 1) { //there is unresolved conflict
              return false;
            } else {
              toremove = key;
              break;
            }
          }
        }
      }
      
      if (toremove != -1) {
        clause.get(i).remove(toremove);
        i --;
      }   
      
      i++;
    }

    return true;
  }
  
  /*
   * restore the determined map
   */
  void restoreMap(Map<Integer, Boolean> t, Map<Integer, Boolean> s) {
    t.clear();
    
    for (Integer key: s.keySet()) {
      t.put(key, s.get(key));
    }
  }
  
  /*
   * initialize the clauses: False means Not
   */
  void init() throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
    String line = br.readLine();
    
    while (!line.equals("0")) {
      String tmp[] = line.split(" ");
      Map<Integer, Boolean> tmp_map = new HashMap<Integer, Boolean>();
      
      for (int i = 0; i < tmp.length; i++) {
        if (tmp[i].charAt(0) == '-') {
          atoms.add(Integer.parseInt(tmp[i].substring(1)));
          tmp_map.put(Integer.parseInt(tmp[i].substring(1)), false);
        } else {
          atoms.add(Integer.parseInt(tmp[i]));
          tmp_map.put(Integer.parseInt(tmp[i]), true); 
        }
      }
      
      clauses.add(tmp_map);
      line = br.readLine();
    }
    //System.out.println(atoms.size() + " atoms\n" + clauses.size() + " clauses\n");
  }
  
  /*
   * copy the clause list
   */
  private List<Map<Integer, Boolean>> copyList(List<Map<Integer, Boolean>> map) {
    List<Map<Integer, Boolean>> r = new ArrayList<>();
    
    for (int i = 0; i < map.size(); i++) {
      r.add(new HashMap<>(map.get(i)));
    }
    
    return r;
  }
  
  /*
   * DP_recur
   */
  public boolean DP_Recur(Map<Integer, Boolean> determined,
                          List<Integer> undetermined,
                          List<Map<Integer, Boolean>> clauses) {     
    Map<Integer, Boolean> tmp_determined = new HashMap<>(determined);
    boolean hasOne = false; 
    
    for (int i = 0; i < clauses.size(); i++) {
      if (clauses.get(i).keySet().size() == 1) {
        int key = 0;
        hasOne = true;
        for (Integer index: clauses.get(i).keySet()) {
          key = index;
        }        

        if (determined.containsKey(key)) {
          //conflict
          if (!determined.get(key).equals(clauses.get(i).get(key))) {
            restoreMap(determined, tmp_determined);
            return false;
          }
        } else {
          determined.put(key, clauses.get(i).get(key));
          
          //remove the atom from the undetermined list
          for (int j = 0; j < undetermined.size(); j++) {
            if (undetermined.get(j) == key) {
              undetermined.remove(j);
              break;
            }
          }
        }
      }
    }
    
    //if there are some clauses which contain only one atom
    if (hasOne) {
      List<Map<Integer, Boolean>> tmp_clauses = new ArrayList<>(clauses);
      if (resolution(determined, tmp_clauses)) {
        if (tmp_clauses.size() == 0) {
          return true;
        }
        
        if (DP_Recur(determined, new ArrayList<>(undetermined), copyList(tmp_clauses))) {
          return true;
        } else {
          restoreMap(determined, tmp_determined);
          return false;          
        }
      } else {
        restoreMap(determined, tmp_determined);     
        return false;
      }
    } else {
      int curAtom = undetermined.get(0);
      undetermined.remove(0);

      determined.put(curAtom, true);
      List<Map<Integer, Boolean>> tmp_clauses = copyList(clauses);
      if (resolution(determined, tmp_clauses)) {
        if (tmp_clauses.size() == 0) {
          System.out.println(determined.size() + " has result");
          return true;
        }

        if (DP_Recur(determined, new ArrayList<>(undetermined), copyList(tmp_clauses))) {
          return true;
        }
      }
      
      restoreMap(determined, tmp_determined);
      tmp_clauses = copyList(clauses);
      determined.put(curAtom, false);
      if(resolution(determined, tmp_clauses)) {
        if (tmp_clauses.size() == 0) {
          System.out.println(determined.size() + " has result");
          return true;
        }
        
        if (DP_Recur(determined, new ArrayList<>(undetermined), new ArrayList<>(tmp_clauses))) {
          return true; 
        } else {
          restoreMap(determined, tmp_determined);     
          return false;            
        }
      } else {
        restoreMap(determined, tmp_determined); 
        return false;
      }
    }
  }
  
  /*
   * get the result from the determiend map
   */
  public void getResult() throws IOException {
    OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(outputFile));
    Map<Integer, Boolean> determined = new HashMap<>();
    List<Integer> undetermined = new ArrayList<Integer>();
    for (Integer key: atoms) {
      undetermined.add(key);
    }
    
    if (DP_Recur(determined, undetermined, clauses)) {
      System.out.println(undetermined.size() + "\thas results\t" + clauses.size());
      Queue<Pair> pq = new PriorityQueue<Pair>(10, new Comparator<Pair>() {
        @Override
        public int compare(Pair arg0, Pair arg1) {
          if (arg0.key < arg1.key) {
            return -1;
          } else if (arg0.key > arg1.key) {
            return 1;
          } else {
            return 0;
          } 
        }
      });
      
      for (Integer key: determined.keySet()) {
        pq.add(new Pair(key, determined.get(key).toString()));
      }
      
      for (int i = 0; i < undetermined.size(); i++) {
        if (!determined.containsKey(undetermined.get(i))) {
          pq.add(new Pair(undetermined.get(i), "true/false"));
        }
      }
      
      while (pq.peek() != null) {
        Pair tmp = pq.poll();
        output.write(tmp.key + "\t" + tmp.value + "\n");
        System.out.println(tmp.key + "\t" + tmp.value);
      }

      System.out.println(0);
    } else {
      System.out.println(0);
      System.out.println("There are no results.");      
    }
  }
  
  public static void main(String[] args) throws IOException {
    String inputFile = args[1];
    String outputFile = args[2];
    DP test = new DP(inputFile, outputFile);
    test.getResult();
  }

}
