//Minimum Window Substring

//Cannot pass the large dataset
public class Solution {
  public boolean check(Map<Character, Integer> num, Map<Character, Integer> pattern) {
  for (Character c: num.keySet()) {
    if (num.get(c) < pattern.get(c)) {
    return false;
    }
  }
  return true;
  }
  
  public String minWindow(String S, String T) {
  if (S == null || T == null || T.length() > S.length()) {
    return "";
  }
  
  Map<Character, Integer> map = new HashMap<Character, Integer>();
  Map<Character, Integer> pattern = new HashMap<Character, Integer>();
  
  for (int i = 0; i < T.length(); i++) {
    map.put(T.charAt(i), 0);
    if (pattern.containsKey(T.charAt(i))) {
    pattern.put(T.charAt(i), pattern.get(T.charAt(i)) + 1);
    } else {
    pattern.put(T.charAt(i), 1);
    }
  }
  int i = 0, j = 0, start = 0, end = Integer.MAX_VALUE;
  
  while (i < S.length()) {
    while (i < S.length()) {
    if (map.containsKey(S.charAt(i))) {
      map.put(S.charAt(i), map.get(S.charAt(i)) + 1);
      if (check(map, pattern)) {
      i ++;
      break; 
      }
    }
    i ++;
    }
    
    if (!check(map, pattern)) {
    break;
    }
    
    while (j < T.length() && j <= i) {
    if (map.containsKey(S.charAt(j))) {
      map.put(S.charAt(j), map.get(S.charAt(j)) - 1);
      if (!check(map, pattern)) {
      j ++;
      break;
      }
    }
    j ++;
    }
    if (end - start > i - j) {
    start = j - 1;
    end = i - 1;
    }    
  } 
  if (j == 0 && i == S.length()) {
    return "";
  } 
  return S.substring(start, end + 1);
  }
}

//Solution that works
//use a found variable to record how many characters in the pattern string that the current substring contains
public String minWindow(String S, String T) {
    int[] srcHash = new int[100];
    for(int i = 0; i < T.length(); i++){
      srcHash[T.charAt(i)]++;
    }
    int start = 0,i= 0;
    int[] destHash = new int[100];
    int found = 0;
    int begin = -1, end = S.length(), minLength = 1 + S.length();
    for(start = i = 0; i < S.length(); i++){
      if(srcHash[S.charAt(i)]!=0){
        destHash[S.charAt(i)]++;
        if(destHash[S.charAt(i)] <= srcHash[S.charAt(i)]) found++;
        if(found == T.length()){  //find the first window
          while(start < i){
            if(srcHash[S.charAt(start)] == 0 || (srcHash[S.charAt(start)] != 0 && (--destHash[S.charAt(start)]) >= srcHash[S.charAt(start)])) {
              start++;
            }else {
              break;
            }
          }
          if(i - start + 1< minLength){
            minLength = i - start + 1;
            begin = start;
            end = i;
          }
          found--;
          start++;
        }
      }
    }
    return begin == -1 ? "" : S.substring(begin,end + 1);
  }