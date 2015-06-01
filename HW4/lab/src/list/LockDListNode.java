package list;

public class LockDListNode extends DListNode {
	protected boolean locked;
	
	public LockDListNode(Object i,LockDListNode p, LockDListNode n){
		super(i,p,n);
		locked = false;
	}
}
