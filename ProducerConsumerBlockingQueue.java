//code from http://javarevisited.blogspot.sg/2012/02/producer-consumer-design-pattern-with.html
//BlockingQueue block API: put(e) / take()
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

class Producer implements Runnable {
  private final BlockingQueue sharedQueue;
  
  public Producer(BlockingQueue sharedQueue) {
  this.sharedQueue = sharedQueue;
  }
  
  @Override
  public void run() {
  for (int i = 0; i < 10; i++) {
    try {
    System.out.println("Produced:" + i);
    sharedQueue.put(i);
    } catch (InterruptedException ex) {
    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  }
}

class Consumer implements Runnable {
  private final BlockingQueue sharedQueue;
  
  public Consumer (BlockingQueue sharedQueue) {
  this.sharedQueue = sharedQueue;
  }
  
  @Override
  public void run() {
  while (true) {
    try {
    System.out.println("Consumed" + sharedQueue.take());
    } catch (InterruptedException ex) {
    Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  }
}

//Without BlockingQueue
//code from http://java67.blogspot.com/2012/12/producer-consumer-problem-with-wait-and-notify-example.html
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java program to solve Producer Consumer problem using wait and notify
 * method in Java. Producer Consumer is also a popular concurrency design pattern.
 *
 * @author Javin Paul
 */
public class ProducerConsumerSolution {

  public static void main(String args[]) {
    Vector sharedQueue = new Vector();
    int size = 4;
    Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer");
    Thread consThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
    prodThread.start();
    consThread.start();
  }
}

class Producer implements Runnable {

  private final Vector sharedQueue;
  private final int SIZE;

  public Producer(Vector sharedQueue, int size) {
    this.sharedQueue = sharedQueue;
    this.SIZE = size;
  }

  @Override
  public void run() {
    for (int i = 0; i < 7; i++) {
      System.out.println("Produced: " + i);
      try {
        produce(i);
      } catch (InterruptedException ex) {
        Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
  }

  private void produce(int i) throws InterruptedException {

    //wait if queue is full
    while (sharedQueue.size() == SIZE) {
      synchronized (sharedQueue) {
        System.out.println("Queue is full " + Thread.currentThread().getName()
                  + " is waiting , size: " + sharedQueue.size());

        sharedQueue.wait();
      }
    }

    //producing element and notify consumers
    synchronized (sharedQueue) {
      sharedQueue.add(i);
      sharedQueue.notifyAll();
    }
  }
}

class Consumer implements Runnable {

  private final Vector sharedQueue;
  private final int SIZE;

  public Consumer(Vector sharedQueue, int size) {
    this.sharedQueue = sharedQueue;
    this.SIZE = size;
  }

  @Override
  public void run() {
    while (true) {
      try {
        System.out.println("Consumed: " + consume());
        Thread.sleep(50);
      } catch (InterruptedException ex) {
        Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
  }

  private int consume() throws InterruptedException {
    //wait if queue is empty
    while (sharedQueue.isEmpty()) {
      synchronized (sharedQueue) {
        System.out.println("Queue is empty " + Thread.currentThread().getName()
                  + " is waiting , size: " + sharedQueue.size());

        sharedQueue.wait();
      }
    }

    //Otherwise consume element and notify waiting producer
    synchronized (sharedQueue) {
      sharedQueue.notifyAll();
      return (Integer) sharedQueue.remove(0);
    }
  }
}
