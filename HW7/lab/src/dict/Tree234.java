/* Tree234.java */

package dict;
/**
 *  A Tree234 implements an ordered integer dictionary ADT using a 2-3-4 tree.
 *  Only int keys are stored; no object is associated with each key.  Duplicate
 *  keys are not stored in the tree.
 *
 *  @author Jonathan Shewchuk
 **/
public class Tree234 extends IntDictionary {

  /**
   *  You may add fields if you wish, but don't change anything that
   *  would prevent toString() or find() from working correctly.
   *
   *  (inherited)  size is the number of keys in the dictionary.
   *  root is the root of the 2-3-4 tree.
   **/
  Tree234Node root;

  /**
   *  Tree234() constructs an empty 2-3-4 tree.
   *
   *  You may change this constructor, but you may not change the fact that
   *  an empty Tree234 contains no nodes.
   */
  public Tree234() {
    root = null;
    size = 0;
  }

  /**
   *  toString() prints this Tree234 as a String.  Each node is printed
   *  in the form such as (for a 3-key node)
   *
   *      (child1)key1(child2)key2(child3)key3(child4)
   *
   *  where each child is a recursive call to toString, and null children
   *  are printed as a space with no parentheses.  Here's an example.
   *      ((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))
   *
   *  DO NOT CHANGE THIS METHOD.  The test code depends on it.
   *
   *  @return a String representation of the 2-3-4 tree.
   **/
  public String toString() {
    if (root == null) {
      return "";
    } else {
      /* Most of the work is done by Tree234Node.toString(). */
      return root.toString();
    }
  }

  /**
   *  printTree() prints this Tree234 as a tree, albeit sideways.
   *
   *  You're welcome to change this method if you like.  It won't be tested.
   **/
  public void printTree() {
    if (root != null) {
      /* Most of the work is done by Tree234Node.printSubtree(). */
      root.printSubtree(0);
    }
  }

  /**
   *  find() prints true if "key" is in this 2-3-4 tree; false otherwise.
   *
   *  @param key is the key sought.
   *  @return true if "key" is in the tree; false otherwise.
   **/
  public boolean find(int key) {
    Tree234Node node = root;
    while (node != null) {
      if (key < node.key1) {
        node = node.child1;
      } else if (key == node.key1) {
        return true;
      } else if ((node.keys == 1) || (key < node.key2)) {
        node = node.child2;
      } else if (key == node.key2) {
        return true;
      } else if ((node.keys == 2) || (key < node.key3)) {
        node = node.child3;
      } else if (key == node.key3) {
        return true;
      } else {
        node = node.child4;
      }
    }
    return false;
  }

  /**
   *  insert() inserts the key "key" into this 2-3-4 tree.  If "key" is
   *  already present, a duplicate copy is NOT inserted.
   *
   *  @param key is the key sought.
   **/
  public void insert(int key) {

    // Fill in your solution here.
    if(this.size == 0){
      Tree234Node newRoot = new Tree234Node(null, key);
      this.root = newRoot;
      this.size++;
    }else{
      int position = 0;
      Tree234Node child = this.root;
      Tree234Node curNode = null;
      while(child != null){
        curNode = child;
        if(curNode.keys == 3){
          if(key == 99){
            System.out.println(curNode);
            System.out.println(curNode.parent);
          }
          reconstruct(curNode, position);
          curNode = curNode.parent;
        }
        try{
          ChildPosition cp = selectChild(key, curNode);
          position =  cp.position;
          child = cp.child;
          // if(key == 49){
          //   System.out.println(position);
          //   System.out.println("that is " + child);
          // }
        }catch(KeyEqualException e){
          return;
        }
      }
      insert(key, curNode, position);
    }
  }


  protected void reconstruct(Tree234Node curNodeCP, int position){
    if(curNodeCP == this.root){
      int newRootKey = curNodeCP.key2;
      int newRightKey= curNodeCP.key3;
      curNodeCP.keys = 1;
      Tree234Node newRoot = new Tree234Node(null, newRootKey);
      Tree234Node newRight = new Tree234Node(newRoot, newRightKey);
      this.root = newRoot;
      newRoot.child1 = curNodeCP;
      newRoot.child2 = newRight;
      newRight.child1 = curNodeCP.child3;
      if(newRight.child1 !=null)
      newRight.child1.parent =  newRight;
      newRight.child2 = curNodeCP.child4;
      if(newRight.child2 !=null )
      newRight.child2.parent = newRight;
      curNodeCP.child3 = null;
      curNodeCP.child4 = null;
      curNodeCP.parent = newRoot;
    }else{
      Tree234Node curParent = curNodeCP.parent;
      int newParentKey = curNodeCP.key2;
      int newRightKey = curNodeCP.key3;

      Tree234Node newRight = new Tree234Node(curParent, newRightKey);
      if(position == 1){
        curParent.key3 = curParent.key2;
        curParent.key2 = curParent.key1;
        curParent.key1 = newParentKey;
        curParent.child4 = curParent.child3;
        curParent.child3 = curParent.child2;
        curParent.child2 = newRight;
        curParent.child1 = curNodeCP;
      }else if(position == 2){
        curParent.key3 = curParent.key2;
        curParent.key2 = newParentKey;
        curParent.child4 = curParent.child3;
        curParent.child3 = newRight;
        curParent.child2 = curNodeCP;
      }else{
        curParent.key3 = newParentKey;
        curParent.child4 = newRight;
        curParent.child3 = curNodeCP;
      }
      curParent.keys = curParent.keys + 1;

      newRight.child1 = curNodeCP.child3;
      if(newRight.child1 != null)
      newRight.child1.parent = newRight;
      newRight.child2 = curNodeCP.child4;
      if(newRight.child2 !=null )
      newRight.child2.parent = newRight;
      curNodeCP.child3 = null;
      curNodeCP.child4 = null;
      curNodeCP.keys = 1; 
    }
  }

  protected ChildPosition selectChild(int key, Tree234Node curNode) throws KeyEqualException{
    if(key < curNode.key1){
      return new ChildPosition(1, curNode.child1);
    }else if(key == curNode.key1){
      throw new KeyEqualException();
    }else{
      if(curNode.keys == 1){
        return new ChildPosition(2, curNode.child2);
      }else if(key < curNode.key2){
        return new ChildPosition(2, curNode.child2);
      }else if(key == curNode.key2){
        throw new KeyEqualException();
      }else{
        if(curNode.keys == 2 || key < curNode.key3){
          return new ChildPosition(3, curNode.child3);
        }else if(key == curNode.key3){
          throw new KeyEqualException();
        }else{
          return new ChildPosition(4, curNode.child4);
        }
      }
    }
  }

  class ChildPosition{
    int position;
    Tree234Node child;
    ChildPosition(int position, Tree234Node child){
      this.position = position;
      this.child = child;
    }
  }

  protected void insert(int key, Tree234Node curNodeCP, int position){
    if(position == 1){
      curNodeCP.key3 = curNodeCP.key2;
      curNodeCP.key2 = curNodeCP.key1;
      curNodeCP.key1 = key;
    }else if(position == 2){
      curNodeCP.key3 = curNodeCP.key2;
      curNodeCP.key2 = key;
    }else if(position == 3){
      curNodeCP.key3 = key;
    }
    curNodeCP.keys++;
    this.size++;
  }

  class KeyEqualException extends Exception{
    public KeyEqualException(){
      super("the key to be inserted already exists in the tree");
    }
  }


  /**
   *  testHelper() prints the String representation of this tree, then
   *  compares it with the expected String, and prints an error message if
   *  the two are not equal.
   *
   *  @param correctString is what the tree should look like.
   **/
  public void testHelper(String correctString) {
    String treeString = toString();
    System.out.println(treeString);
    if (!treeString.equals(correctString)) {
      System.out.println("ERROR:  Should be " + correctString);
    }
  }

  /**
   *  main() is a bunch of test code.  Feel free to add test code of your own;
   *  this code won't be tested or graded.
   **/
  public static void main(String[] args) {
    Tree234 t = new Tree234();

    System.out.println("\nInserting 84.");
    t.insert(84);
    t.testHelper("84");

    System.out.println("\nInserting 7.");
    t.insert(7);
    t.testHelper("7 84");

    System.out.println("\nInserting 22.");
    t.insert(22);
    t.testHelper("7 22 84");

    System.out.println("\nInserting 95.");
    t.insert(95);
    t.testHelper("(7)22(84 95)");

    System.out.println("\nInserting 50.");
    t.insert(50);
    t.testHelper("(7)22(50 84 95)");

    System.out.println("\nInserting 11.");
    t.insert(11);
    t.testHelper("(7 11)22(50 84 95)");

    System.out.println("\nInserting 37.");
    t.insert(37);
    t.testHelper("(7 11)22(37 50)84(95)");

    System.out.println("\nInserting 60.");
    t.insert(60);
    t.testHelper("(7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 1.");
    t.insert(1);
    t.testHelper("(1 7 11)22(37 50 60)84(95)");

    System.out.println("\nInserting 23.");
    t.insert(23);
    t.testHelper("(1 7 11)22(23 37)50(60)84(95)");

    System.out.println("\nInserting 16.");
    t.insert(16);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95))");

    System.out.println("\nInserting 100.");
    t.insert(100);
    t.testHelper("((1)7(11 16)22(23 37))50((60)84(95 100))");

    System.out.println("\nInserting 28.");
    t.insert(28);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(95 100))");

    System.out.println("\nInserting 86.");
    t.insert(86);
    t.testHelper("((1)7(11 16)22(23 28 37))50((60)84(86 95 100))");

    System.out.println("\nInserting 49.");
    t.insert(49);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60)84(86 95 100))");

    System.out.println("\nInserting 81.");
    t.insert(81);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((60 81)84(86 95 100))");

    System.out.println("\nInserting 51.");
    t.insert(51);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86 95 100))");

    System.out.println("\nInserting 99.");
    t.insert(99);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51 60 81)84(86)95(99 100))");

    System.out.println("\nInserting 75.");
    t.insert(75);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(75 81)84(86)95" +
                 "(99 100))");

    System.out.println("\nInserting 66.");
    t.insert(66);
    t.testHelper("((1)7(11 16)22(23)28(37 49))50((51)60(66 75 81))84((86)95" +
                 "(99 100))");

    System.out.println("\nInserting 4.");
    t.insert(4);
    t.testHelper("((1 4)7(11 16))22((23)28(37 49))50((51)60(66 75 81))84" +
                 "((86)95(99 100))");

    System.out.println("\nInserting 80.");
    t.insert(80);
    t.testHelper("(((1 4)7(11 16))22((23)28(37 49)))50(((51)60(66)75" +
                 "(80 81))84((86)95(99 100)))");

    System.out.println("\nFinal tree:");
    t.printTree();
  }

}