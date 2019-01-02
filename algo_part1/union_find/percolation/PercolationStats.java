/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    double[] trialsCount;
    double confidenceLevel = 1.96;
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        try {
            if (trials <= 0) {
                throw new IllegalArgumentException("trials must not be <= 0");
            }
            this.trialsCount = new double[trials];
            for (int i = 0; i < trials; i++) {
                Percolation grid = new Percolation(n);
                int row = 0, col = 0;
                while(true) {
                    row = StdRandom.uniform(0, n);
                    col = StdRandom.uniform(0, n);
                    // System.out.println("("+row+","+col+")");
                    grid.open(row, col);
                    if (grid.percolates()) {
                        // System.out.println("The system percolates!");
                        this.trialsCount[i] = grid.getThreshold();
                        System.out.println("The threshold value for "+ "case "+(i+1)+" is = " + this.trialsCount[i]);
                        grid = null;
                        break;
                    }
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception);
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(trialsCount);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(trialsCount);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double temp = confidenceLevel * stddev() / Math.sqrt(trialsCount.length);
        return mean() - temp;
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double temp = confidenceLevel * stddev() / Math.sqrt(trialsCount.length);
        return mean() + temp;
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println(args[0]);
            System.out.println(args[1]);
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            PercolationStats stats = new PercolationStats(n, trials);
            System.out.println("mean                    = " + stats.mean());
            System.out.println("stddev                  = " + stats.stddev());
            String confidenceInterval = "[" + stats.confidenceLo() + ", " + stats.confidenceHi() +
                    "]";
            System.out.println("95% confidence interval = " + confidenceInterval);
        }
    }
}
