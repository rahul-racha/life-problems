/*
    To search a number in bitonic array in the order of 2logn
*/

public class Bitonic {
    private int[] array;
    private int key;

    public Bitonic(int[] array, int key) {
        this.array = array;
        this.key = key;
    }

    private int searchAscending(int low, int high) {
        if (low <= high && low >= 0 && high <= this.array.length) {
            int mid = (low + high)/2;
            if (this.key == this.array[mid]) {
                return mid;
            }
            if (this.key > this.array[mid]) {
                return searchAscending(mid+1, high);
            } else {
                return searchAscending(low, mid-1);
            }
        }
        return -1;
    }

    private int searchDescending(int low, int high) {
        if (low <= high && low >= 0 && high <= this.array.length) {
            int mid = (low + high)/2;
            if (this.key == this.array[mid]) {
                return mid;
            }
            if (this.key > this.array[mid]) {
                return searchDescending(low, mid-1);
            } else {
                return searchDescending(mid+1, high);
            }
        }
        return -1;
    }

    public int searchBitonic(int low, int high) {
        if (low <= high && low >= 0 && high <= this.array.length) {
            int mid = (low + high)/2;
            if (this.key == this.array[mid]) {
                return mid;
            }
            if (this.key > this.array[mid]) {
                if (this.array[mid+1] >= this.array[mid]) {
                    return searchBitonic(mid+1, high);
                } else {
                    return searchBitonic(low, mid-1);
                }
            } else {
                int index = this.searchAscending(low,mid-1);
                if (-1 == index) {
                    return this.searchDescending(mid+1, high);
                }
                return index;
            }
        }
        return -1;
    }

    public static void main(String args[]) {
        int[] array = { 2, 4, 6, 8, 10, 14, 11, 9, 7, -5, -4, -9 };
        // Test Case 1
        Bitonic bitonic = new Bitonic(array, -4);
        System.out.println(display(array, bitonic.searchBitonic(0, array.length-1)));
        bitonic = null;
        // Test Case 2
        bitonic = new Bitonic(array, -8);
        System.out.println(display(array, bitonic.searchBitonic(0, array.length-1)));
        bitonic = null;
    }

    private static String display(int[] array, int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException();
        }
        return "Number Found = " + array[index];
    }
}

