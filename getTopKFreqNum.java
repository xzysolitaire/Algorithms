import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


public class topKNum {
  static class Pair implements Comparable {
    int num;
    int freq;
    Pair(int n, int f) {
      num = n;
      freq = f;
    }
    
    @Override
    public int compareTo(Object arg0) {
      Pair arg1 = (Pair)arg0;
      if (arg1.freq < this.freq) {
        return 1;
      } else if (arg1.freq == this.freq){
        return 0;
      } else {
        return -1;
      }
    }
  }
  
  public static List<Integer> getTopK(int[] num, int k) {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    Queue<Pair> pq = new PriorityQueue<Pair>();
    List<Integer> r = new ArrayList<Integer>();
    
    for (int i = 0; i < num.length; i++) {
      if (map.containsKey(num[i])) {
        map.put(num[i], map.get(num[i]) + 1);
      } else {
        map.put(num[i], 1);
      }
    }
    
    for (Integer index: map.keySet()) {
      if (pq.size() < k) {
        pq.add(new Pair(index, map.get(index)));
      } else {
        pq.add(new Pair(index, map.get(index)));
        pq.poll();
      }
    }
    
    while (pq.peek() != null) {
      r.add(pq.poll().num);
    }
    
    return r;
  }
  
  public static void main(String[] args) {
    int num[] = {1, 1, 1, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 10};
    List<Integer> r = getTopK(num, 4);
    for (Integer i: r) {
      System.out.println(i);
    }
  }

}
