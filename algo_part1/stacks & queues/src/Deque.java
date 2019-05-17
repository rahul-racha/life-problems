import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int count = 0;
    private final class Node {
        Node prev;
        Item item;
        Node next;
    }
    private Node first;
    private Node last;

    public Deque()                           // construct an empty
    {
        first = null;
        last = null;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return 0 == count;
    }

    public int size()                        // return the number of items on the deque
    {
        return count;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if (null == item) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.prev = null;
        if (null == first) {
            node.next = null;
            first = node;
            if (null == last) {
                last = node;
            }
            node = null;
            ++count;
            return;
        }
        node.next = first;
        first.prev = node;
        first = node;
        ++count;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if (null == item) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.next = null;
        if (null == last) {
            node.prev = null;
            last = node;
            if (null == first) {
                first = node;
            }
            node = null;
            ++count;
            return;
        }
        node.prev = last;
        last.next = node;
        last = node;
        ++count;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if (0 == count) {
            throw new NoSuchElementException();
        }
        Node temp = first;
        if (1 < count) {
            first = first.next;
            first.prev = null;
        }
        Item tempItem = temp.item;
        temp = null;
        --count;
        return tempItem;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if (0 == count) {
            throw new NoSuchElementException();
        }
       Node temp = last;
       if (1 < count) {
           last = last.prev;
           last.next = null;
       }
       Item tempItem = temp.item;
       temp = null;
       --count;
       return tempItem;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator<Item>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        Node current;
        public DequeIterator(Node node) {
            current = node;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node temp = current;
            current = current.next;
            return (Item) temp.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(3);
        deque.addLast(10);
        deque.addLast(11);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addLast(12);
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        for (int item: deque) {
            System.out.println(item);
        }
    }
}
