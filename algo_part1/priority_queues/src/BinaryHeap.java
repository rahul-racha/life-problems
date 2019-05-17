import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public class BinaryHeap<Key extends Comparable<Key>> {
    private Key[] priorityQ;
    private int N = 0;
    private int heapSize;

    public BinaryHeap(int capacity) {
        priorityQ = (Key[]) new Comparable[capacity+1];
        heapSize = capacity + 1;
    }

    public Boolean isEmpty() {
        return 0 == N;
    }

//    public void sort(Key[] list) {
//        // Build max heap from bottom up
//        for (int i = list.length/2; i >= 1; i--) {
//            sink(i);
//        }
//        //
//        while (N > 1) {
//            exch(1, N--);
//            sink(1);
//        }
//    }

    public void insert(Key key) {
        if (N >= heapSize - 1) {
            throw new BufferOverflowException();
        }

        priorityQ[++N] = key;
        if (1 == N) {
            return;
        }
        swim(N);
    }

    public Key delMax() {
        if (N <= 0) {
            throw new BufferUnderflowException();
        }
        Key max = priorityQ[1];
        exch(1, N--);
        priorityQ[N + 1] = null;
        sink(1);
        return max;
    }

    public Key[] getPQ() {
        return (Key[]) priorityQ;
    }

    private void swim(int k) {
        while (k/2 >= 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N) {
            if (2*k < N && less(2 * k, 2 * k + 1) && less(k, 2 * k + 1)) {
                exch(k, 2 * k + 1);
                k = 2*k + 1;
            } else if (2*k < N && less(2 * k + 1, 2 * k) && less(k, 2 * k)) {
                exch(k, 2 * k);
                k = 2*k;
            } else if (2*k <= N && less(k, 2 * k) ) {
                exch(k, 2 * k);
                k = 2*k;
            } else {
                return;
            }
        }
    }

    private Boolean less(int i, int j) {
        return priorityQ[i].compareTo(priorityQ[j]) < 0;
    }

    private void exch(int i, int j) {
        Key temp = priorityQ[i];
        priorityQ[i] = priorityQ[j];
        priorityQ[j] = temp;
    }

    public static void main(String args[]) {
        int[] list = { 1, 2, 3, 4, 5, 0 };
        BinaryHeap<Integer> heap = new BinaryHeap<>(list.length);
        for (int item: list) {
            heap.insert(item);
        }
        Comparable[] sortedList = heap.getPQ();
        for (Comparable item: sortedList) {
            System.out.println(item);
        }
        int i = 0;
        while (i++ < 4) { heap.delMax(); } ; heap.insert(9);
        System.out.println("after deletion");
        sortedList = heap.getPQ();
        for (Comparable item: sortedList) {
            System.out.println(item);
        }
        heap.insert(9);
    }
}
