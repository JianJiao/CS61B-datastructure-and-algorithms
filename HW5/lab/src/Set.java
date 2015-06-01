/* Set.java */

import list.*;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  Duplicate elements are not permitted in a Set.
 **/
public class Set {
  /* Fill in the data fields here. */
	protected List list;

  /**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    // Your solution here.
		list = new DList(); 
  }

  /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
		return list.length();
  }

  /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) throws InvalidNodeException {
    // Your solution here.
		ListNode node = list.front();
		insertFrom(c, node);
  }

	private ListNode insertFrom(Comparable c, ListNode node) throws InvalidNodeException {
		while(node.isValidNode()){
			Comparable item = (Comparable) node.item();
			int rslt = c.compareTo(item);
			if(rslt>0){
				node = node.next();
				continue;
			}else if(rslt == 0){
				return node.next();	
			}else if(rslt < 0){
				node.insertBefore(c);
				return node;
			}
		}
		list.insertBack(c);
		return node;
	}

  /**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set s) throws InvalidNodeException {
    // Your solution here.
		ListNode node1, node2;
		node1 = this.list.front();
		node2 = s.list.front();
		do{
			node1 = this.insertFrom((Comparable) node2.item(), node1);
			node2 = node2.next();
		}while(node2.isValidNode());
  }

  /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) throws InvalidNodeException {
    // Your solution here.
		ListNode node1 = this.list.front(); 
		ListNode node2 = s.list.front(); 
		compareTwo(node1, node2);
  }

	private void compareTwo(ListNode node1, ListNode node2) throws InvalidNodeException {
		if(!node1.isValidNode()){
			return;	
		}
		if(!node2.isValidNode()){
			while(node1.next().isValidNode()){
				node1 = node1.next();
				node1.prev().remove();
			}
			node1.remove();
			return;
		}
		Comparable item1 = (Comparable) node1.item();
		Comparable item2 = (Comparable) node2.item();
		int rslt = item1.compareTo(item2);
		if(rslt > 0){
			node2 = node2.next();	
		}else if(rslt == 0){
			node1 = node1.next();
			node2 = node2.next();
		}else if(rslt < 0){
			node1 = node1.next();
			node1.prev().remove();
		}
		compareTwo(node1, node2);
	}


  /**
   *  toString() returns a String representation of this Set.  The String must
   *  have the following format:
   *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
   *            between them.
   *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
   *            "{" or after "}"; two spaces before and after each element.
   *            Elements are printed with their own toString method, whatever
   *            that may be.  The elements must appear in sorted order, from
   *            lowest to highest according to the compareTo() method.
   *
   *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
   *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
   *            DEVIATIONS WILL LOSE POINTS.
   **/
  public String toString() {
    // Replace the following line with your solution.
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("{ ");
			ListNode node = list.front();
			while(node.isValidNode()){
				sb.append(" ").append(node.item().toString()).append(" ");
				node = node.next();
			}
			sb.append(" }");
			return sb.toString();
		}catch(InvalidNodeException e){
			e.printStackTrace();
			return e.getMessage();
		}
  }

  public static void main(String[] argv) throws InvalidNodeException {
    Set s = new Set();
    s.insert(new Integer(3));
    s.insert(new Integer(4));
    s.insert(new Integer(3));
    System.out.println("Set s = " + s);

    Set s2 = new Set();
    s2.insert(new Integer(4));
    s2.insert(new Integer(5));
    s2.insert(new Integer(5));
    System.out.println("Set s2 = " + s2);

    Set s3 = new Set();
    s3.insert(new Integer(5));
    s3.insert(new Integer(3));
    s3.insert(new Integer(8));
    System.out.println("Set s3 = " + s3);

    s.union(s2);
    System.out.println("After s.union(s2), s = " + s);

    s.intersect(s3);
    System.out.println("After s.intersect(s3), s = " + s);

    System.out.println("s.cardinality() = " + s.cardinality());
    // You may want to add more (ungraded) test code here.
  }
}
