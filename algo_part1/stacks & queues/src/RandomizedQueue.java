import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count = 0;
    private Item[] Bag;

    public RandomizedQueue() // construct an empty randomized queue
    {
        Bag = (Item[]) new Object[2];
    }
    private void expandBag() {
        if (count >= Bag.length) {
            Item[] temp = (Item[]) new Object[Bag.length*2];
            for (int i = 0; i < Bag.length; i++) {
                temp[i] = Bag[i];
            }
            Bag = temp;
        }
    }
    private void shrinkBag() {
        if (count <= (1/4)*Bag.length) {
            Item[] temp = (Item[]) new Object[(int) (Bag.length*0.5)];
            for (int i = 0; i < count; i++) {
                temp[i] = Bag[i];
            }
            Bag = temp;
        }
    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException();
        }
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return 0 == count;
    }
    public int size()                        // return the number of items on the randomized queue
    {
        return count;
    }
    public void enqueue(Item item)           // add the item
    {
        if (null == item) {
            throw new IllegalArgumentException();
        }
        expandBag();
        Bag[count++] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        shrinkBag();
        int randomIndex = getRandomNumberInRange(0, count-1);
        Item item = Bag[randomIndex];
        Bag[randomIndex] = Bag[count-1];
        Bag[count-1] = null;
        --count;
        return item;
    }
    public Item sample()                     // return a random item (but do not remove it)
    {
        int randomIndex = getRandomNumberInRange(0, count-1);
        return Bag[randomIndex];
    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new BagIterator<Item>();
    }
    private class BagIterator<Item> implements Iterator<Item> {
        int index = 0;
        Item[] shuffledBag;
        public BagIterator() {
            shuffledBag = (Item[]) Bag.clone();
            int randomNumber = 0;
            for (int i = 0; i < count; i++) {
                randomNumber = getRandomNumberInRange(0, count - 1);
                swap(i, randomNumber);
            }
        }
        private void swap(int a, int b) {
            Item temp = shuffledBag[a];
            shuffledBag[a] = shuffledBag[b];
            shuffledBag[b] = temp;
        }
        public boolean hasNext() {
            return index < count;
        }
        public Item next() {
            if (index > count) {
                throw new NoSuchElementException();
            }
            return (Item) shuffledBag[index++];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<Integer> que = new RandomizedQueue<>();
        que.enqueue(3);
        que.enqueue(4);
        que.enqueue(5);
        que.enqueue(6);
        System.out.println(que.sample());
        System.out.println(que.dequeue());
        System.out.println(que.sample());
        System.out.println(que.dequeue());
        System.out.println(que.sample());
        System.out.println(que.sample());

        System.out.println("*************************");
        for (int item: que) {
            System.out.println(item);
        }
        for (int item: que) {
            System.out.println(item);
        }
    }
}
