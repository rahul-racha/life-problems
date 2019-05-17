public class Shellsort<Item> {
    public Shellsort() {

    }
    public int compareTo(Item b) {
        return -1;
    }
    private static boolean less(Comparable current, Comparable prev) {
        return  current.compareTo(prev) < 0;
    }
    private static void exchange(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static void sort(Comparable[] array) {
        //h-sort - 3x+1 series
        int h = 1;
        while (h < array.length/3) { h = (3 * h) + 1; }
        while (h >= 1) {
            for (int i = h; i < array.length; i++) {
                for (int j = i; j >= h; j=j-h) {
                    if (less(array[j], array[j-1])) {
                        exchange(array, j, j-h);
                    }
                }
            }
            h = h/3;
        }
    }
    public static void main(String args[]) {
        Integer[] array = {5, 1, 4, 10, 9, 8, 7, 6};
        Shellsort.sort(array);
        for (Integer item: array) {
            System.out.println(item);
        }
    }
}
