/* HashTableChained.java */

package dict;

import list.*;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
	 protected int size;
	 protected int N;
	 protected List[] buckets;


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

	public int numBuckets(){
		return N;	
	}

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
		N = largestP(2*sizeEstimate);
		buckets = new List[N];
		size = 0;
  }

	//find the largest prime less than N
	public int largestP(int N){
		int result = -1;
		boolean[] prime = new boolean[N];
		for(int i=2;i<N;i++){
			prime[i]=true;
		}

		for(int i=2;i*i<N;i++){
			if(prime[i]==true){
				for(int j=i;j*i<N;j++){
					prime[j*i]=false;
				}
			}
		}

		for(int i=N-1;i>1;i--){
			if(prime[i]==true){
				return i;	
			}
		}
		return result;
	}


  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
		N = 109;
		buckets = new List[N];
		size = 0;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
		int recode = code%N;
		if(recode<0){
			recode = recode + N;
		}
		return recode;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
		boolean result = true;
		if(size>0){
			result = false;	
		}
		return result;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
		Entry entry = new Entry();
		entry.key = key;
		entry.value = value;
		int code = key.hashCode();
		int recode = compFunction(code);
		if(buckets[recode]==null){
			buckets[recode] = new SList();	
		}
		buckets[recode].insertBack(entry);
		size++;
		return entry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
		Entry entry = null;
		Object value;
		int code = key.hashCode();
		int recode = compFunction(code);
		if(buckets[recode]!=null){
			ListNode node = buckets[recode].front();
			while(node.isValidNode()){
				try{
					Entry entry2 = (Entry) node.item();
					if(entry2.key.equals(key)){
						entry = entry2;
						break;
					}
					node = node.next();	
				}catch(InvalidNodeException e){
					System.out.println("Exception "+e.getMessage());
				}
			}
		}
		return entry;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
		Entry entry = null;
		Object value;
		int code = key.hashCode();
		int recode = compFunction(code);
		if(buckets[recode]!=null){
			ListNode node = buckets[recode].front();
			while(node.isValidNode()){
				try{
					Entry entry2 = (Entry) node.item();
					if(entry2.key.equals(key)){
						entry = entry2;
						node.remove();
						size--;
						break;
					}
					node = node.next();	
				}catch(InvalidNodeException e){
					System.out.println("Exception " + e.getMessage());
				}
			}
		}
		return entry;
  }

	public int collisions(){
		int result = 0;
		for(int i=0;i<N;i++){
			if(buckets[i]!=null){
				result += buckets[i].length()-1;
			}
		}
		return result;
	}

	public void printEach(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<N;i++){
			if(buckets[i]!=null){
				sb.append(buckets[i].length()).append(" , ");
			}else{
				sb.append("0 , ");
			}
		}
		sb.setLength(sb.length()-3);
		System.out.println(sb.toString());
	}

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
		for(int i=0;i<N;i++){
			buckets[i]=null;
		}
  }

}
