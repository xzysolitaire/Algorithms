#include <cstdlib>
#include <iostream>
#include <vector>

using namespace std;

//assume k is larger than 0 
//use a vector as a double-end queue
int getMax(vector<int>& temp, int start, int k, int num[]) {
    if (temp.size() == 0) {
        temp.push_back(num[start]);
        return num[start];  
    }
    
    if (temp.size() == k) {
        temp.erase(temp.begin()); 
    }
    
    while (!temp.empty() && temp[0] < num[start]) {
        temp.erase(temp.begin());
    } 
    temp.push_back(num[start]);
    return temp[0];
}

int main(int argc, char *argv[])
{   
    int num[10] = {3, 1, -1, -3, 4, 5, 1, 4, -2, 0};
    int window_size = 3;
    vector<int> temp;
    for (int i = 0; i < 10; i++) {
        cout << getMax(temp, i, window_size, num) << endl;
    }
    
    system("PAUSE");
    return EXIT_SUCCESS;
}
