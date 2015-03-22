package Airbnb;

/*
 * Using Trie to solve the edit distance with K problem
 */

import java.util.ArrayList;
import java.util.List;

public class EditDistanceK {
  private Trie root = new Trie();
  
  public void buildTrie(String[] dict) {
    for (String s: dict) {
      root.insert(s);
    }
  }
  
  public List<String> searchWordK(String target, int k) {
    int[] tmp = new int[target.length() + 1];
    List<String> result = new ArrayList<String>();
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = i;
    }
    
    for (Character key: root.children.keySet()) {
      searchCurrent(tmp, target, k, 1, root.children.get(key), key, result, sb);
    }
    
    tmp = null;
    return result;
  }
  
  public void searchCurrent(int[] lastRow, 
                            String target, 
                            int k, 
                            int num,
                            Trie cur, 
                            char c, 
                            List<String> result,
                            StringBuilder sb) {
    int[] tmp = new int[target.length() + 1];
    tmp[0] = num;
    sb.append(c);
    
    for (int i = 1; i < tmp.length; i++) {
      if (c == target.charAt(i - 1)) {
        tmp[i] = lastRow[i - 1];
      } else {
        tmp[i] = Math.min(Math.min(tmp[i - 1], lastRow[i]), lastRow[i - 1]) + 1;
      }
    }
    
    if (tmp[target.length()] <= k && cur.isEnd) {
        result.add(new String(sb));
    }
    
    for (Character key: cur.children.keySet()) {
      searchCurrent(tmp, target, k, num + 1, cur.children.get(key), key, result, sb);
    }
    
    tmp = null;
    sb.setLength(sb.length() - 1);
  }
  
  public static void main(String[] args) {
    String dict[] = {
        "abcd",
        "a",
        "abc",
        "bc",
        "abdasd",
        "abb"
    };
    
    EditDistanceK test = new EditDistanceK();
    test.buildTrie(dict);
    String target = "aba";
    List<String> r = test.searchWordK(target, 1);
    
    for (String str: r) {
      System.out.println(str);
    }
  }

}
