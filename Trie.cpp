#include<iostream>
#include<string>
#include<unordered_map>
using namespace std;

class Trie {

public:
    bool leaf;
    unordered_map<char, Trie*> children;     
    Trie() {
        leaf = false;
    }

    void insert(string word) {
        if(children[word[0]] == NULL) {
            children[word[0]] = new Trie();
        }
    
        if(word.size() == 1) {
            children[word[0]]->leaf = true;
        }else {
            children[word[0]]->insert(word.substr(1));
        }
    }

    bool find(string word) {
        if(children[word[0]] == NULL) {
            return false;
        }

        if(word.size() == 1) {
            return children[word[0]]->leaf;
        }else {
            return children[word[0]]->find(word.substr(1));
        }
    }

    void remove(string word) {
        if(children[word[0]] == NULL) {
            return;
        }

        if(word.size() == 1) {
            children[word[0]]->leaf = false;
            if(children[word[0]]->children.size() == 0) {
                delete children[word[0]];
            }
        }else{
            children[word[0]]->remove(word.substr(1));
        }
    }
};

int main() {
    Trie* test = new Trie();
    test->insert("whese");
    test->insert("who");
    test->insert("whose");
    test->insert("find");
    test->insert("fin");
    test->remove("fin");
    test->insert("aaa");

    cout << "whese" << test->find("whese") << endl;
    cout << "who" << test->find("who") << endl;
    cout << "whose" << test->find("whose") << endl;
    cout << "what" << test->find("what") << endl;
    cout << "aa" << test->find("aa") << endl;
    cout << "fin" << test->find("fin") << endl;
    cout << "find" << test->find("find") << endl;
}
