/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    LinkedQueue qq = new LinkedQueue();
    LinkedQueue sq;
    int size = q.size();
    for(int i=0; i<size ; i++){
      sq = new LinkedQueue();
      try{
        Object item = q.dequeue();
        sq.enqueue(item);
        qq.enqueue(sq);
      }catch(QueueEmptyException e){
        System.out.println(e.getMessage());
      }
    }
    return qq;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    LinkedQueue resultQueue = new LinkedQueue();
    if(q1.size() != 0 && q2.size() == 0 ){
      resultQueue.append(q1);
    }else if(q1.size()==0 && q2.size()!=0 ){
      resultQueue.append(q2);
    }else if(q1.size()!=0 && q2.size()!=0){
      int i =1, j =1;
      Comparable q1p, q2p;
      try{
        while(q1.size()>0 && q2.size()>0){
          q1p = (Comparable)q1.front();
          q2p = (Comparable)q2.front();
          if( q1p.compareTo(q2p) > 0){
            Object item = q2.dequeue();
            resultQueue.enqueue(item);
          }else if(q1p.compareTo(q2p) < 0){
            Object item = q1.dequeue();
            resultQueue.enqueue(item);
          }else{
            Object item1 = q1.dequeue();
            Object item2 = q2.dequeue();
            resultQueue.enqueue(item1);
            resultQueue.enqueue(item2);
          }
        }
        if( q1.size()==0 ){
          resultQueue.append(q2);
        }else{
          resultQueue.append(q1);
        }
      }catch(QueueEmptyException e){
        System.out.println(e.getMessage());
      }
    }
    return resultQueue;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    if(qIn!=null && pivot!=null && qSmall!=null && qEquals!=null && qLarge!=null && qIn.size()!=0){
      while(qIn.size()!=0){
        try{
          Comparable item = (Comparable)qIn.dequeue();
          if(item.compareTo(pivot)>0){
            qLarge.enqueue(item);
          }else if(item.compareTo(pivot) == 0){
            qEquals.enqueue(item);
          }else{
            qSmall.enqueue(item);
          }
        }catch(QueueEmptyException e){
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    if(q.size() > 1){
      LinkedQueue newQ = makeQueueOfQueues(q);
      q.append(newQ);
      LinkedQueue q1, q2;
      try{
        while(q.size() > 1){
          q1 = (LinkedQueue)q.dequeue();
          q2 = (LinkedQueue)q.dequeue();
          LinkedQueue result = mergeSortedQueues(q1, q2);
          q.enqueue(result);
        }
        q.append((LinkedQueue)(q.dequeue()));
      }catch(QueueEmptyException e){
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    if(q!=null && q.size()>1 ){
      int randomInt = (int)(Math.random()*q.size());
      Comparable pivot = (Comparable) q.nth(randomInt);
      LinkedQueue smaller = new LinkedQueue();
      LinkedQueue equal = new LinkedQueue();
      LinkedQueue larger = new LinkedQueue();
      partition(q, pivot, smaller, equal, larger);
      quickSort(smaller);
      quickSort(larger);
      equal.append(larger);
      smaller.append(equal);
      q.append(smaller);
    }
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }


  public static void testRunningTime(int SORTSIZE){
    LinkedQueue q;
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());


    System.out.println();
    System.out.println("* testing makeQueueOfQueues:");
    LinkedQueue q1 = makeRandom(10);
    System.out.println("q1: " + q1.toString());
    LinkedQueue q1q = makeQueueOfQueues(q1);
    System.out.println("q1q: " + q1q.toString());
    System.out.println("q1: " + q1.toString());

    System.out.println();
    System.out.println("* testing mergeSortedQueues: ");
    LinkedQueue qa = new LinkedQueue();
    LinkedQueue qb = new LinkedQueue();
    qa.enqueue(1);
    qa.enqueue(4);
    qa.enqueue(7);
    qb.enqueue(3);
    qb.enqueue(8);
    qb.enqueue(10);
    qb.enqueue(12);
    System.out.println("qa is: " + qa);
    System.out.println("qb is: " + qb);
    LinkedQueue result1 = mergeSortedQueues(qa, qb);
    System.out.println("result is: " + result1);
    System.out.println("qa is: " + qa);
    System.out.println("qb is: " + qb +"\n");

    LinkedQueue qc = new LinkedQueue();
    qc.enqueue(3);
    qc.enqueue(5);
    System.out.println("qc is: " + qc);
    System.out.println("qa is: " + qa);
    LinkedQueue result2 = mergeSortedQueues(qa, qc);
    System.out.println("result is: " + result2);
    System.out.println("qc is: " + qc);
    System.out.println("qa is: " + qa);


    System.out.println("\n*testing mergeSort on zero and one: ");
    q = new LinkedQueue();
    System.out.println(q);
    mergeSort(q);
    System.out.println(q);

    q.enqueue(5);
    System.out.println("\n" + q);
    mergeSort(q);
    System.out.println(q);

    System.out.println("\n*testing partition:");
    q = makeRandom(10);
    System.out.println(q);
    Comparable pivot = 4;
    LinkedQueue qs = new LinkedQueue();
    LinkedQueue qe = new LinkedQueue();
    LinkedQueue ql = new LinkedQueue();
    partition(q, pivot, qs, qe, ql);
    System.out.println("after");
    System.out.println(q + "\n" + qs +"\n" + qe + "\n" + ql);

    System.out.println("\n*testing quicksort on zero and one");
    q = new LinkedQueue();
    System.out.println(q);
    quickSort(q);
    System.out.println(q);

    q.enqueue(4);
    System.out.println("\n" + q);
    quickSort(q);
    System.out.println(q);

    System.out.println("\n*comparing running time");
    for(int i=100; i<=1000000; i*=10){
      testRunningTime(i);
    }


    // Timer stopWatch = new Timer();
    // q = makeRandom(SORTSIZE);
    // stopWatch.start();
    // mergeSort(q);
    // stopWatch.stop();
    // System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
    //                    stopWatch.elapsed() + " msec.");

    // stopWatch.reset();
    // q = makeRandom(SORTSIZE);
    // stopWatch.start();
    // quickSort(q);
    // stopWatch.stop();
    // System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
    //                    stopWatch.elapsed() + " msec.");
  }

}