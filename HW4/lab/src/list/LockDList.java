package list;

public class LockDList extends DList{

	protected LockDListNode newNode(Object item, DListNode prev, DListNode next){
		LockDListNode lprev = (LockDListNode) prev;
		LockDListNode lnext = (LockDListNode) next;
		return new LockDListNode(item, lprev, lnext);
	}

	public LockDListNode front(){
		LockDListNode f = (LockDListNode) super.front();	
		return f;
	}

	public LockDListNode back(){
		return ((LockDListNode) super.back());	
	}
	
	public LockDListNode next(DListNode node){
		return ((LockDListNode) super.next(node));	
	}

	public LockDListNode prev(DListNode node){
		return ((LockDListNode) super.prev(node));
	}

	public void remove(DListNode node){
		try{
			LockDListNode lnode = (LockDListNode) node;
			if(lnode.locked == false){
				super.remove(lnode);
			}
		}catch(ClassCastException e){
			System.out.println("not a LockDListNode");
			e.printStackTrace();
			return;
		}
	}

	public void lockNode(DListNode node){
		try{
			LockDListNode lnode = (LockDListNode)	node;
			lnode.locked = true;
		}catch(ClassCastException e){
			System.out.println("not a LockDListNode");
			e.printStackTrace();
			return;
		}
	}

}
