#include <cstdlib>
#include <iostream>
#include <map>
#include <set>

using namespace std;


bool dfs(map<char, string> &m, set<string>& s, int start_p, int start_t,
         const string& P, const string& T) {
     if (start_p == P.length() && start_t == T.length()) {
         return true;
     }
                        
     if (m.find(P[start_p]) != m.end()) {
         if (T.length() - start_t < m[P[start_p]].length()) { 
             return false; 
         } else {
             //match the substring with the string in the map
             if (T.substr(start_t, m[P[start_p]].length()) != m[P[start_p]]) {  
                 return false;
             } else {
                 if (dfs(m, s, start_p + 1, start_t + m[P[start_p]].length(), P, T)) {
                     return true;  
                 }   
             }   
         }            
     } else {
         for (int i = start_t + 1; i <= T.length(); i++) {
             if (s.find(T.substr(start_t, i - start_t)) == s.end()) {
                 m[P[start_p]] = T.substr(start_t, i - start_t); 
                 s.insert(T.substr(start_t, i - start_t)); 
                 if (dfs(m, s, start_p + 1, i, P, T)) {
                    return true;
                 }
                 s.erase(T.substr(start_t, i - start_t));
                 m.erase(m.find(P[start_p]));      
             }
         }            
     }
     
     return false;
}

bool isMatch(string P, string T) {
     if (T.length() < P.length() || (
         P.length() == 0 && T.length() != 0)) {
         return false;
     }
     
     map<char, string> m;
     set<string> used;
     return dfs(m, used, 0, 0, P, T);
}

int main(int argc, char *argv[])
{   
    string P[] = {"abba", "abba", "aaaa", "abba", ""};
    string T[] = {"redbluebluered", "redblueyellowred",
                  "redredredred", "redredredred", "abc"};
    
    for (int i = 0; i < 5; i ++) {
        cout << isMatch(P[i], T[i]) << endl;
    }
    
    system("PAUSE");
    return EXIT_SUCCESS;
}
