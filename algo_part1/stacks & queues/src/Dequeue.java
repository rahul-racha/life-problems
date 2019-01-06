import java.util.Iterator;

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
        if (0 == count) {
            return true;
        }
        return false;
    }

    public int size()                        // return the number of items on the deque
    {
        return count;
    }
    public void addFirst(Item item)          // add the item to the front
    {
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
            return;
        }
        node = first.prev;
        node.next = first;
        first = node;
        ++count;
    }
    public void addLast(Item item)           // add the item to the end
    {
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
        }
        node.prev = last;
        last.next = node;
        last = node;
        ++count;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        Node temp = first;
        first = first.next;
        first.prev = null;
        Item tempItem = temp.item;
        temp = null;
        return tempItem;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
       Node temp = last;
       last = last.prev;
       last.next = null;
       Item tempItem = temp.item;
       temp = null;
       return tempItem;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator<Item>(first);
    }

    class DequeIterator<Item> implements Iterator<Item> {
        Node current;
        public DequeIterator(Node node) {
            current = node;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Node temp = current.next;
            current = current.next;
            return (Item) temp.item;
        }
    }

    public static void main(String args[]) {

    }
}
