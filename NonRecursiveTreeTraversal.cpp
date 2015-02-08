#include <cstdlib>
#include <iostream>
#include <stack>

using namespace std;

//Iterative inorder traversal
void InOrderTraversal(Node* root) {
  stack<Node*> s;
  Node* p = root;
  while (p != NULL || !s.empty()) {
    while (p != NULL) {
      s.push(p);
      p = p->lchild;
    }
    
    if (!s.empty()) {
      p = s.top();
      cout << p->data << endl;
      s.pop();
      p = p->rchild;
    }
  }
}

//Iterative postorder traversal
void PostOrderTraversal(Node* root) {
  stack<Node*> s;
  Node* p = root;
  Node* pre = NULL;
  Node* top = NULL;
  
  while (p != NULL || !s.empty()) {
    while (p != NULL) {
      s.push(p);
      p = p->lchild;
    }
    
    if (!s.empty()) {
      top = s.top();
      if (top->rchild != NULL && top->rchild != pre) {
        p = p->rchild;
      } else {
        cout << top->data << endl;
        pre = top;
        s.pop();
      }
    }
  }
}