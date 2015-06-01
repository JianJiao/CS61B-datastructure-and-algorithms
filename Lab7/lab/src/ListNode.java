/* ListNode.java */

/**
 *  ListNode is a very simple headless list class, akin to cons cells in
 *  Scheme.  Each ListNode contains an item and a reference to the next node.
 **/
class ListNode {

  public Object item;
  public ListNode next;

  /**
   *  Constructs a ListNode with item i and next node n.
   *  @param i the item to store in the ListNode.
   *  @param n the next ListNode following this ListNode.
   **/
  ListNode(Object i, ListNode n) {
    item = i;
    next = n;
  }

	public String toString(){
		StringBuilder sb = new StringBuilder();
		ListNode node = this;
		while(node!=null){
			sb.append(node.item.toString()).append(" ");
			node = node.next;
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	public static void main(String[] args){
		ListNode testn = new ListNode(5.0, new ListNode(4.0, new ListNode(3.0, null)));
		System.out.println(testn.toString());
	}

}
