package CrackingtheCodingInterview;

public class KMP {
  
  public static int getMatch(String P, String T) {
    int kmp[] = new int[P.length()];
    kmp[0] = -1;
    for (int i = 1; i < kmp.length; i++) {
      if (kmp[i - 1] != -1) {
        if (P.charAt(i) == P.charAt(kmp[i - 1] + 1)) {
          kmp[i] = kmp[i - 1] + 1;
        } else {
          kmp[i] = -1;
        }
      } else {
        if (P.charAt(i) == P.charAt(0)) {
          kmp[i] = 0;
        } else {
          kmp[i] = -1;
        }
      }
    }
    
    int t_index = 0, p_index = 0;
    while (t_index < T.length() && p_index < P.length()) {
      if (P.charAt(p_index) == T.charAt(t_index)) {
        p_index ++;
        t_index ++;
      } else {
        if (p_index == 0) {
          t_index++;
        } else if (kmp[p_index - 1] == -1) {
          p_index = 0;
        } else {
          p_index = kmp[p_index - 1] + 1;
        }
      }
      
      if (p_index == P.length()) {
        return t_index - P.length();
      }
    }
    
    return -1;
  }
  
  public static void main(String[] args) {
     String P = "annanna";
     String T = "annbcdanacadsannannabnna";
     System.out.println(getMatch(P, T));
  }

}
