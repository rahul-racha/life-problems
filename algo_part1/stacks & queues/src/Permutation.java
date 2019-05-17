import java.util.Scanner;

public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQue = new RandomizedQueue<String>();
        Scanner stdin = new Scanner(System.in);
        while (stdin.hasNext()) {
            randomQue.enqueue(stdin.next());
        }
        while (k-- > 0) {
            System.out.println(randomQue.dequeue());
        }
    }
}
