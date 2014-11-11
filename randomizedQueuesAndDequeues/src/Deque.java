import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;
    
    private class Node {
        private Item item;
        private Node next;
        private Node pre;
    }
    
    public Deque() {
        N = 0;
        first = null;
        last = null;
    }                          // construct an empty deque
    public boolean isEmpty() {
       return N == 0; 
    }                // is the deque empty?
    public int size() {
        return N;
    }                       // return the number of items on the deque
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Item is null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.pre = null;
        first.next = oldFirst;
        N++;
        if (oldFirst == null) last = first;
        else oldFirst.pre = first;
    }         // insert the item at the front
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Item is null");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.pre = oldLast;
        N++;
        if (oldLast == null) first = last;
        else oldLast.next = last;
    }          // insert the item at the end
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        if (N == 1) {            
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.pre = null;
        }
        N--;
        return item;
    }               // delete and return the item at the front
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        
        if (N == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.pre;
            last.next = null; 
        }
        
        N--;
        return item;
    }
    // delete and return the item at the end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        // return an iterator over items in order from front to end
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    } 
    public static void main(String[] args) {
/*        Deque<String> s = new Deque<String>();
//        while (!StdIn.isEmpty()) {
//            String item = StdIn.readString();
//            if (!item.equals("-")) s.addFirst(item);
//            else if (!s.isEmpty()) StdOut.print(s.removeFirst() + " ");
//            StdOut.println(s.isEmpty());
//        }
//        StdOut.println("(" + s.size() + " left on stack)");
        s.addFirst("a");
        s.addFirst("b");
        s.addFirst("c");
        s.addFirst("d");
        s.addLast("e");
        s.addLast("f");
        s.addLast("g");
        s.addLast("h");
//        assert(s.removeLast().equals("h"));
//        assert(s.removeLast().equals("g"));
//        assert(s.removeLast().equals("f"));
//        assert(s.removeLast().equals("e"));
//        assert(s.removeFirst().equals("d"));
//        assert(s.removeFirst().equals("c"));
//        assert(s.removeFirst().equals("b"));
//        assert(s.removeFirst().equals("a"));
        Iterator<String> it=s.iterator();
        while(true){
            StdOut.print(it.next());
        }*/
    }  // unit testing
 }
