package Airbnb;

import java.util.HashMap;
import java.util.Map;

public class Trie {
  public boolean isEnd;
  public Map<Character, Trie> children =
      new HashMap<Character, Trie>();
  
  public Trie() {
    isEnd = false;
  }
  
  public void insert(String word) {
    if (word.length() == 0) {
      isEnd = true;
      return;
    }
    
    insertRecur(word, 0);
  }
  
  public void insertRecur(String word, int index) {
    if (word.length() == index) {
      isEnd = true;
      return;
    }
    
    if (!children.containsKey(word.charAt(index))) {
      children.put(word.charAt(index), new Trie());
    }  

    children.get(word.charAt(index)).insertRecur(word, index + 1);
  }
  
  public boolean search(String word) {
    if (word.length() == 0) {
      return isEnd;
    }
    
    return searchRecur(word, 0);
  }
  
  public boolean searchRecur(String word, int index) {
    if (word.length() == index) {
      return isEnd;
    }
    
    if (children.containsKey(word.charAt(index))) {
      return children.get(word.charAt(index)).searchRecur(word, index + 1);
    } else {
      return false;
    }
  }
}
