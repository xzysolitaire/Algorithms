void inorderMorrisTraversal(TreeNode *root) {
  TreeNode *cur = root, *prev = NULL;
  while (cur != NULL)  {
    if (cur->left == NULL) {
      printf("%d ", cur->val);
      cur = cur->right;
    } else {
      // find predecessor
      prev = cur->left;
      while (prev->right != NULL && prev->right != cur)
        prev = prev->right;

      if (prev->right == NULL) {  // 2.a)
        prev->right = cur;
        cur = cur->left;
      } else {          // 2.b)
        prev->right = NULL;
        printf("%d ", cur->val);
        cur = cur->right;
      }
    }
  }
}

void preorderMorrisTraversal(TreeNode *root) {
  TreeNode *cur = root, *prev = NULL;
  while (cur != NULL) {
    if (cur->left == NULL) {
      printf("%d ", cur->val);
      cur = cur->right;
    } else {
      prev = cur->left;
      while (prev->right != NULL && prev->right != cur)
        prev = prev->right;

      if (prev->right == NULL) {
        printf("%d ", cur->val);  // the only difference with inorder-traversal
        prev->right = cur;
        cur = cur->left;
      } else {
        prev->right = NULL;
        cur = cur->right;
      }
    }
  }
}

// reverse the tree nodes 'from' -> 'to'.
void reverse(TreeNode *from, TreeNode *to) 
{
  if (from == to)
    return;
  TreeNode *x = from, *y = from->right, *z;
  while (true) {
    z = y->right;
    y->right = x;
    x = y;
    y = z;
    if (x == to)
      break;
  }
}

// print the reversed tree nodes 'from' -> 'to'.
void printReverse(TreeNode* from, TreeNode *to) 
{
  reverse(from, to);
  
  TreeNode *p = to;
  while (true) {
    printf("%d ", p->val);
    if (p == from)
      break;
    p = p->right;
  }
  
  reverse(to, from);
}

void postorderMorrisTraversal(TreeNode *root) {
  TreeNode dump(0);
  dump.left = root;
  TreeNode *cur = &dump, *prev = NULL;
  while (cur) {
    if (cur->left == NULL) {
      cur = cur->right;
    } else {
      prev = cur->left;
      while (prev->right != NULL && prev->right != cur)
        prev = prev->right;

      if (prev->right == NULL) {
        prev->right = cur;
        cur = cur->left;
      } else {
        printReverse(cur->left, prev);  // call print
        prev->right = NULL;
        cur = cur->right;
      }
    }
  }
}
