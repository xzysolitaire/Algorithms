package Algorithms;

import java.util.ArrayList;
import java.util.List;

/*
* An iterator class for list of list
* 
* It will be calld like below:
* 
* ArrayListIterator iter = new ArrayListIterator(input);
* while (iter.hasNext()) {
*   int num = iter.next();
*   if (num % 2 == 0) {
*   iter.remove();  //it will remove the former element
*   }
* }
*/

class ArrayListIterator {
  List<List<Integer>> input = new ArrayList<List<Integer>>();
  int x, y;
  
  class EmptyArrayListException extends Exception {    
  }
  
  ArrayListIterator(List<List<Integer>> input) {
    this.input = input;
    this.x = 0;
    this.y = 0;
  }
  
  public boolean hasNext() {
    while (x < input.size()) {
      if (y < input.get(x).size()) {
        return true;
      } else {
        x ++;
        y = 0;
      }
    }
    
    return false;
  }
  
  public int next() throws EmptyArrayListException {
    if (hasNext()) {
      y ++;
      return input.get(x).get(y - 1);
    } else {
      throw new EmptyArrayListException();
    }
  }
  
  public void remove() throws EmptyArrayListException {
    int tmp_x = x, tmp_y = y - 1;
    while (tmp_x >= 0) {
      if (tmp_y >= 0) {
        input.get(tmp_x).remove(tmp_y);
        break;
      } else {
        tmp_x --;
        tmp_y = input.get(tmp_x).size() - 1;
      }
    }
    
    if (tmp_x == x) {
      y --;
    }
    
    if (tmp_x == -1) {
      throw new EmptyArrayListException();
    }
  }
  
  public static void main(String[] args) throws EmptyArrayListException {
    List<List<Integer>> input = new ArrayList<List<Integer>>();
    List<Integer> tmp1 = new ArrayList<Integer>();
    List<Integer> tmp2 = new ArrayList<Integer>();
    List<Integer> tmp3 = new ArrayList<Integer>();
    List<Integer> tmp4 = new ArrayList<Integer>();
    tmp1.add(1);
    tmp3.add(2);
    tmp3.add(3);
    tmp4.add(4);
    input.add(tmp1);
    input.add(tmp2);
    input.add(tmp3);
    input.add(tmp4);
    
    ArrayListIterator test = new ArrayListIterator(input);
    while (test.hasNext()) {
      int num = test.next();
      if (num % 2 == 0) {
        test.remove();
      }
    }
    
    ArrayListIterator test1 = new ArrayListIterator(input);
    while (test1.hasNext()) {
      System.out.println(test1.next());
    }
	
	//throw an excpetion in this case 
	test.next();
  }
}