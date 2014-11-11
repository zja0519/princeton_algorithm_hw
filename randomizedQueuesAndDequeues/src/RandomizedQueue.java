import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N;
    
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        N = 0;
    }                // construct an empty randomized queue
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }
    public boolean isEmpty() {
        return N == 0;
    }                // is the queue empty?
    public int size() {
        return N;
    }                       // return the number of items on the queue
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Item is null");
        if (N == q.length) resize(2*q.length);
        q[N++] = item;
    }          // add the item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randomI = StdRandom.uniform(N);
        Item item = q[randomI];
        q[randomI] = q[N-1];
        q[N-1] = null;
        N--;
        if (N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }                   // delete and return a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[ StdRandom.uniform(N) ];
    }                    // return (but do not delete) a random item
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }        // return an independent iterator over items in random order
    private class RandomQueueIterator implements Iterator<Item> {

        private int i = 0;
        private Item[] iq;
        
        private RandomQueueIterator() {
            int tempI = 0;
            Item temp;
            iq = (Item[]) new Object[N];
            for (int id = 0; id < N; id++) {
                iq[id] = q[id];
            }
            for (int id = 0; id < N; id++) {
                tempI = StdRandom.uniform(id+1);
                temp = iq[tempI];
                iq[tempI] = iq[id];
                iq[id] = temp;
            }
        }
        
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = iq[i];
            i++;
            return item;
        }
        
    }
    public static void main(String[] args) {
/*        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        r.enqueue(1);
        r.enqueue(2);
        r.enqueue(3);
        r.enqueue(4);
        r.enqueue(5);
        r.enqueue(6);
        r.enqueue(7);
        r.enqueue(8);
        r.enqueue(9);
        r.enqueue(10);
        for(int i=0;i<=9;i++){
//            Iterator<Integer> it = r.iterator();
//            while(it.hasNext()){
//                StdOut.print(it.next()+",");
//            }
//            StdOut.println();
            StdOut.print(r.dequeue()+",");
        }
        r.dequeue();*/
    }  // unit testing
}
