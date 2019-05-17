import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;

public class MedianHeap {
    private MaxPQ<Integer> maxPQ;
    private MinPQ<Integer> minPQ;
    private int maxQIndex;
    private int minQIndex;

    public MedianHeap() {
        maxPQ = new MaxPQ<>();
        minPQ = new MinPQ<>();
    }

    public int removeMedian() {
        if (minPQ.size() > maxPQ.size()) {
            return minPQ.delMin();
        }
        return maxPQ.delMax();
    }

    public void insert(int item) {
        int median = getMedian();
        if (item >= median) {
            minPQ.insert(item);
            if (minPQ.size() - maxPQ.size() > 1) {
                maxPQ.insert(minPQ.delMin());
            }
        } else {
            maxPQ.insert(item);
            if (maxPQ.size() - minPQ.size() > 1) {
                minPQ.insert(maxPQ.delMax());
            }
        }
    }

    private int getMedian() {
        if (maxPQ.size() == minPQ.size()) {
            return (maxPQ.max() + minPQ.min())/2;
        }
        if (maxPQ.size() > minPQ.size()) {
            return maxPQ.max();
        }
        return minPQ.min();
    }
}
