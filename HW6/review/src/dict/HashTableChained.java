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
  protected int size;  // number of entries in the dictionary
  protected int bucketSize;  // number of buckets
  protected List buckets[];

  private class NumberTooSmallException extends Exception{
    public NumberTooSmallException(){
      super("the input number is too small");
    }
  }

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    int n = 2 * sizeEstimate;
    try{
      bucketSize = computePrimeSlightlySmallerThan(n);
      buckets = new SList[bucketSize];
    }catch(NumberTooSmallException e){
      bucketSize = 0;
      System.out.println(e.getMessage());
    }
  }


  /* get the largest prime smaller than the given param */
  protected int computePrimeSlightlySmallerThan(int n) throws NumberTooSmallException{
    boolean prime[] = new boolean[n];
    int result = 2;

    // check the input parameter
    if(n <= 2){
      throw new NumberTooSmallException();
    }else{
      // assume all to be prime unless proved false 
      for(int i=2;i<n;i++){
        prime[i] = true;
      }
      // find out all numbers not prime
      for(int i=2; i*i<n; i++){
        for(int j=i; i*j<n; j++){
          prime[i*j] = false;
        }
      }
 
      // find the largest one less than n
      for(int i = n-1; i>1; i--){
        if(prime[i] == true){
          result = i;
          break;
        }
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
    this(109);
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
   int compCode = code % bucketSize; 
   if(compCode < 0){
    compCode += bucketSize;
   }
   return compCode;
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
    if(size >0){
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
    Entry newEntry = new Entry();
    newEntry.key = key;
    newEntry.value = value;

    int compCode = compFunction(key.hashCode());

    if(buckets[compCode] == null){
      buckets[compCode]= new SList();
    }
    buckets[compCode].insertBack(newEntry);
    //System.out.println(chain.length());
    this.size++;
    return newEntry;
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


  protected ListNode detect(Object key){
    // Replace the following line with your solution.
    ListNode result = null;
    int compCode = compFunction(key.hashCode());
    if(buckets[compCode]!= null){
      ListNode curNode = buckets[compCode].front();
      Entry curEntry;
      while(curNode.isValidNode()){
        try{
          curEntry = (Entry) curNode.item();
          if(curEntry.key.equals(key)){
            result = curNode;
            break;
          }else{
            curNode = curNode.next();
          }
        }catch(InvalidNodeException e){}
      }
    }
    return result;
  }

  public Entry find(Object key) {
    // Replace the following line with your solution.
    ListNode nodeResult = detect(key);
    Entry entryResult = null;
    if(nodeResult !=null ){
      try{
        entryResult = (Entry) nodeResult.item();
      }catch(InvalidNodeException e){}
    }
    return entryResult;
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
    ListNode resultNode = detect(key);
    Entry resultEntry = null;
    if(resultNode != null ){
      try{
        resultEntry = (Entry) resultNode.item();
        resultNode.remove();
      }catch(InvalidNodeException e){}
    }
    this.size--;
    return resultEntry;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    this.buckets = new SList[bucketSize];
    this.size = 0;
  }

  public int computeCollisions(){
    int collisions = 0;
    //System.out.println(buckets.length);
    for(int i=0; i<bucketSize; i++){
      if(buckets[i] !=null ){
        collisions += buckets[i].length()-1;
      }else{
        //System.out.println("no");
      }
    }
    return collisions;
  }

  public static void main(String args[]) throws NumberTooSmallException{
    // test some helper method in this class;
    HashTableChained table = new HashTableChained(4);

    int result = table.computePrimeSlightlySmallerThan(110);
    System.out.println(result);
  }

}