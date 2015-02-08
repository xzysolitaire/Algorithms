
// Java implementation of Trie
Class TrieNode {
  private boolean isLeaf;
  private Map<Character, TrieNode>() Trie = new HashMap<Character, TrieNode>():
  
  public TrieNode(boolean isLeaf) {
    this.isLead = false;
  }
  
  public void insert(String str) {
    if (str.length() == 1) {
      isLeaf = true;
    } else {
      Trie.put(str.charAt(0), new TrieNode());
      Trie.get(str.charAt(0)).insert(str.substring(1);
    }    
  }
  
  public boolean search(String str) {
    if (str.length() == 1) {
      return isLeaf;
    } else {
      if (Trie.containsKey(str.charAt(0))) {
        return Trie.get(str.charAt(0)).search(str.substring(1));
      } else {
        return false;
      }
    }
  }
  
  public void delete(String str) {
    if (str.length() == 1) {
      isLeaf = false;
    } else {
      if (Trie.containsKey(str.charAt(0))) {
        Trie.get(str.charAt(0)).delete(str.substring(1));
      } 
    }
  }
}