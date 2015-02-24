/*
* Post Correspondence Problem using iterative deepening search method
*/

#include <cstdlib>
#include <iostream>
#include <fstream>
#include <vector>
#include <set>

using namespace std;

struct domino {
  int index;   
  string top;
  string bottom;
    
  domino(int i, string a, string b) {index = i; top = a; bottom = b;}   
};

struct state {
  bool pos;  //true: tailing of the top/ false: tailing of the bottom
  string tailing;
  int addition;    
  state* prevState;  //previous state
    
  state(){pos = false; tailing = "";}
};

//get a tailing string; return an empty string if no match
string getTailing(string a, string b, bool& isTop) {
  int i = a.length() - 1, j = b.length() - 1;
  while (i >= 0 && j >= 0 && a[i] == b[j]) {
    i--;
    j--;  
  }
    
  if (i == -1 || j == -1) {
    if (a.length() > b.length()) {
      isTop = true;
      return a.substr(0, a.length() - b.length());  
    } else {
      isTop = false;
      return b.substr(0, b.length() - a.length());   
    }
  } else {
    return "";       
  }
}

//print the result 
void printResult(state& result, 
                 const vector<domino>& dominos, 
                 bool outputType) {
  state* temp = &result;
  string fstring = "";
  
  cout << "The dominos sequence that solves the problem is: \n";

  while (temp->prevState != NULL) {
    cout << "D" << temp->addition + 1 << " ";      
    fstring += dominos[temp->addition].top;
    temp = temp->prevState;
  }
  cout << "\nThe result string sequence is: " << fstring << endl;
  
  cout << endl;
  if (outputType) {
    cout << "The flag of printing the sequence of states is set: " << endl;
    cout << "(Print from the end to the start)" << endl;
    temp = &result;
    
    cout << "Result state --->" << endl;
    temp = temp->prevState;
    if (temp != NULL) {
      while (temp->prevState != NULL) {
        if (temp->pos) {
          cout << "Top tailing: " << temp->tailing << " --->" << endl;       
        } else {
          cout << "Bottom tailing: " << temp->tailing << " --->" << endl;      
        } 
          
        temp = temp->prevState;  
      }             
    }
     
    cout << "Empty state" << endl;
  }
}

//depth first search
void dfs(const vector<domino>& dominos, 
         state* tmp_state, 
         bool& hasResult, 
         int depth,
         bool outputType) {      
  if (depth > 0 && !hasResult) {         
    for (int i = 0; i < dominos.size(); i++) {
      state result_state;
      if (tmp_state->pos) {
        //get a result
        if (dominos[i].top + tmp_state->tailing == dominos[i].bottom) {
          result_state.tailing = "";
          result_state.addition = i;
          result_state.prevState = tmp_state;
          hasResult = true;
          printResult(result_state, dominos, outputType);
          return;
        } else {
          bool isTop;
          string tmp_tailing = getTailing(dominos[i].top + tmp_state->tailing, 
                                          dominos[i].bottom, isTop);
          
          if (tmp_tailing != "") {
            state* add_state = new state;  
            add_state->addition = i;
            add_state->tailing = tmp_tailing;   
            add_state->pos = isTop;
            add_state->prevState = tmp_state;
            dfs(dominos, add_state, hasResult, depth - 1, outputType);
            delete add_state;
          }
        }  
      } else {
        //get a result
        if (dominos[i].bottom + tmp_state->tailing == dominos[i].top) {
          result_state.tailing = "";
          result_state.addition = i;
          result_state.prevState = tmp_state;
          hasResult = true;
          printResult(result_state, dominos, outputType);
          return;                   
        } else {
          bool isTop;
          string tmp_tailing = getTailing(dominos[i].top, 
                                          dominos[i].bottom + tmp_state->tailing,
                                          isTop);
          
          if (tmp_tailing != "") {
            state* add_state = new state;  
            add_state->addition = i;
            add_state->tailing = tmp_tailing;   
            add_state->pos = isTop;
            add_state->prevState = tmp_state;
            dfs(dominos, add_state, hasResult, depth - 1, outputType);
            delete add_state;
          }     
        }//end of second else   
      }//end of first else
    }//end of for    
  }//end of if
}

//iterative deepening search 
void iterative_dfs(int depth_limit, 
                   const vector<domino>& dominos,
                   const vector<state*>& states, 
                   bool& hasResult,
                   bool outputType) {
  for (int j = 1; j <= depth_limit; j++) {
    for (int i = 0; i < states.size(); i++) {
      if (!hasResult) {
        dfs(dominos, states[i], hasResult, j, outputType);    
      } else {
        break;       
      }  
    } 
    
    if (hasResult) {
      break;    
    }
  }
}

int toInt(char* input) {
  int result = 0;
  
  if (input[0] != '-') {
    for (int i = 0; i < strlen(input); i++) {
      result = result * 10 + (input[i] - '0');  
    }    
  } else {
    for (int i = 1; i < strlen(input); i++) {
      result = result * 10 + (input[i] - '0');  
    }
    result *= -1;         
  }

  return result;
}

int main(int argc, char *argv[])
{ 
  if (argc == 1) {
    cout << "Missing input arguments: ";
    cout << "Input file, (Depth limit), (Output flag)." << endl;    
    exit(0);
  }
  
  if (argc > 4) {
    cout << "Input error: too many arguments." << endl;       
    exit(0);
  }
  
  fstream fs;
  fs.open(argv[1]);
  //fs.open("C://Users/acer/Desktop/input6.txt");
    
  if (!fs.is_open()) {
    cout << "File open error." << endl;
    exit(0);  
  }
    
  vector<domino> dominos;
  vector<state*> states;
  set<string> t_tailing;   //record the top tailing string
  set<string> b_tailing;   //record the bottom tailnig string
  int queue_size, state_size, tmp_i;
  string tmp_t, tmp_b;
  bool outputType = true, hasResult = false;
  fs >> queue_size >> state_size;
  cout << "Queue size limit: " << queue_size << endl;
  cout << "State size limit: " << state_size << endl;
    
  //by default the DFS probe depth is 5
  int depth_limit = 5;
  //if the user set the DFS limit, then we change the depth limit
  if (argc == 3) {
    if (toInt(argv[2]) == -1) { //set the output type flag
      cout << "Output flag is set." << endl;
      outputType = true;
    } else {             //set the depth limit 
      cout << "Iterative depth limit is set as: " << argv[2] << endl;
      depth_limit = toInt(argv[2]);      
    }  
  } else if (argc == 4) {  //set both the depth limit and the output type
    cout << "Output flag is set." << endl;
    cout << "Iterative depth limit is set as: " << argv[2] << endl; 
    depth_limit = toInt(argv[2]);
    outputType = true;   
  } 
    
  while (fs) {
    fs >> tmp_i >> tmp_t >> tmp_b;
    dominos.push_back(domino(tmp_i, tmp_t, tmp_b));  
  }
  dominos.erase(dominos.end());
    
  //first stage
  state start_state, result_state;
  start_state.pos = true;
  start_state.prevState = NULL;
  start_state.addition = -1;
  states.push_back(&start_state);
    
  //THE FIRST STAGE: limited by queue size and state size
  int state_num = 0;
  while (!states.empty() && 
         states.size() < queue_size && 
         state_num < state_size &&
         !hasResult) {
    state* tmp_state = states[0];
    states.erase(states.begin());
    for (int i = 0; i < dominos.size(); i++) {
      if (tmp_state->pos) {
        //get a result
        if (dominos[i].top + tmp_state->tailing == dominos[i].bottom) {
          result_state.tailing = "";
          result_state.addition = i;
          result_state.prevState = tmp_state;
          hasResult = true;
          printResult(result_state, dominos, outputType);
          break;
        } else {
          bool isTop;               
          string tmp_tailing = getTailing(dominos[i].top + tmp_state->tailing, 
                                          dominos[i].bottom, isTop);
                                          
          if (tmp_tailing != "") {
            //test if the state has already existed 
            if (isTop) {
              if (t_tailing.find(tmp_tailing) == t_tailing.end()) {
                //cout << "Top tailing: " << tmp_tailing << endl;
                t_tailing.insert(tmp_tailing);    
              } else {
                //cout << "Top duplicate state: " << tmp_tailing << endl;
                continue;     
              }   
            } else { 
              if (b_tailing.find(tmp_tailing) == b_tailing.end()) {
                //cout << "Bottom tailing: " << tmp_tailing << endl;
                b_tailing.insert(tmp_tailing);    
              } else {
                //cout << "Bottom duplicate state: " << tmp_tailing << endl;
                continue;     
              }     
            }                          
                          
            state* add_state = new state;  
            add_state->addition = i;
            add_state->tailing = tmp_tailing;   
            add_state->pos = isTop;
            add_state->prevState = tmp_state;
            
            states.push_back(add_state);
            state_num ++;
            
            //reach the limit, the first stage ends
            if (states.size() == queue_size || state_num == state_size) {
              cout << "The BFS ends." << endl;
              break;   
            }
          }
        }  
      } else {
        //get a result
        if (dominos[i].bottom + tmp_state->tailing == dominos[i].top) {
          result_state.tailing = "";
          result_state.addition = i;
          result_state.prevState = tmp_state;
          hasResult = true;
          printResult(result_state, dominos, outputType);
          break;                      
        } else {
          bool isTop;
          string tmp_tailing = getTailing(dominos[i].top, 
                                          dominos[i].bottom + tmp_state->tailing, 
                                          isTop);
                          
          if (tmp_tailing != "") {
            //test if the state has already existed 
            if (isTop) {
              if (t_tailing.find(tmp_tailing) == t_tailing.end()) {
                //cout << "Top tailing: " << tmp_tailing << endl;
                t_tailing.insert(tmp_tailing);    
              } else {
                //cout << "Top duplicate state: " << tmp_tailing << endl;
                continue;     
              }   
            } else { 
              if (b_tailing.find(tmp_tailing) == b_tailing.end()) {
                //cout << "Bottom tailing: " << tmp_tailing << endl;
                b_tailing.insert(tmp_tailing);    
              } else {
                //cout << "Bottom duplicate state: " << tmp_tailing << endl;
                continue;     
              }     
            }
                          
            state* add_state = new state;  
            add_state->addition = i;
            add_state->tailing = tmp_tailing;   
            add_state->pos = isTop;
            add_state->prevState = tmp_state;
            
            states.push_back(add_state);
            state_num ++;
            
            //reach the limit, the first stage ends
            if (states.size() == queue_size || state_num == state_size) {
              cout << "\nThe BFS ends." << endl;
              break;   
            }
          }
        }//end of second else   
      }//end of first else
    }//end of for 
  }
  
  //THE SECOND STAGE: iterative deepening algorithm DFS
  if (!hasResult && states.size() != 0) {
    cout << "The iterative DFS starts.\n" << endl;
    iterative_dfs(depth_limit, dominos, states, hasResult, outputType);
  } 
  
  //after iterative depth search
  if (!hasResult) {
    cout << "There is no solution within the limit of search." << endl;    
  }
  
  system("PAUSE");
  return EXIT_SUCCESS;
}
