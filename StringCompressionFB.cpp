#include <cstdlib>
#include <iostream>

using namespace std;

string strCompress(string input) {
    if (input.length() == 0) {
        return "";
    }

    int count = 1;
    string result = "";
    char temp = input[0];
    
    for (int i = 1; i < input.size(); i++) {
        if (input[i] == input[i - 1]) {
            count ++;
        } else {
            if (count != 1) {
                result += temp;
                result += (char)('0' + count);  
            } else {
                result += temp;       
            }
                  
            temp = input[i];
            count = 1;
        }
    }
    
    if (count != 1) {
        result += temp;
        result += (char)('0' + count);  
    } else {
        result += temp;       
    }
    
    return result;
}

int main(int argc, char *argv[])
{   
    string input[5] = {"", "a", "aaabb", "abbbcc", "abcd"};
    for (int i = 0; i < 5; i++) {
        cout << strCompress(input[i]) << endl;
    }
    system("PAUSE");
    return EXIT_SUCCESS;
}
