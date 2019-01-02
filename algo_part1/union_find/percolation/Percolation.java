/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;

import java.util.HashMap;

public class Percolation {
    int n;
    Site[][] site;
    int[][] size;
    int noOfOpenSites = 0;
    HashMap<Integer, Location> countToLocMap;
    // API
    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        try {
            if (n <= 0) {
                throw new IllegalArgumentException("n must not be <= 0");
            }
            this.n = n;
            this.site = new Site[n][n];
            this.size = new int[n][n];
            this.countToLocMap = new HashMap<Integer, Location>();
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    this.site[i][j] = new Site();
                    this.site[i][j].value = count;
                    this.site[i][j].type = SiteType.blocked;
                    this.size[i][j] = 1;
                    this.site[i][j].rootLoc = new Location();
                    this.site[i][j].rootLoc.row = i;
                    this.site[i][j].rootLoc.col = j;
                    this.countToLocMap.put(count, this.site[i][j].rootLoc);
                    count++;
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception);
        }
    }
    private Site getRootFor(Site site) {
        while (countToLocMap.get(site.value) != site.rootLoc) {
            Location location = this.site[site.rootLoc.row][site.rootLoc.col].rootLoc; // parent
            location = this.site[location.row][location.col].rootLoc; // grandparent
            location = this.site[location.row][location.col].rootLoc; // ancester
            site = this.site[location.row][location.col];
        }
        return site;
    }
    private boolean areLocationsEqual(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) {
            return false;
        }
        if (loc1.row == loc2.row && loc1.col == loc2.col) {
            return true;
        } else {
            return false;
        }
    }
    private Location getMaxLocation(Location firstLoc, Location secondLoc) {
        if (firstLoc == null && secondLoc == null) {
            return null;
        } else if (firstLoc != null && secondLoc == null) {
            return firstLoc;
        } else if (firstLoc == null && secondLoc != null) {
            return secondLoc;
        } else {
            if (this.size[firstLoc.row][firstLoc.col] >=
                    this.size[secondLoc.row][secondLoc.col]) {
                return firstLoc;
            } else {
                return secondLoc;
            }
        }
    }
    private Location getLocWithMaxSize(Location topSiteLoc, Location leftSiteLoc, Location rightSiteLoc,
                                           Location bottomSiteLoc) {
        Location firstSetMaxSizeLoc = getMaxLocation(topSiteLoc, leftSiteLoc);
        Location secondSetMaxSizeLoc = getMaxLocation(rightSiteLoc, bottomSiteLoc);
        return getMaxLocation(firstSetMaxSizeLoc, secondSetMaxSizeLoc);
    }
    private void updateRootLocForSites(Site maxSizeSite, Site site1, Site site2, Site site3) {
        if (maxSizeSite == null) {
            return;
        }
        int childrenLinked = 0;
        if (site1 != null) {
            site1.rootLoc = maxSizeSite.rootLoc;
            childrenLinked += 1;
        }
        if (site2 != null) {
            site2.rootLoc = maxSizeSite.rootLoc;
            childrenLinked += 1;
        }
        if (site3 != null) {
            site3.rootLoc = maxSizeSite.rootLoc;
            childrenLinked += 1;
        }
        this.size[maxSizeSite.rootLoc.row][maxSizeSite.rootLoc.col] += childrenLinked;
    }
    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (this.site[row][col].type == SiteType.blocked) {
            this.site[row][col].type = (0 == row) ? SiteType.full : SiteType.open;
            this.noOfOpenSites += 1;
        } else {
            return;
        }
        //check adjacent sites
        Site topSite = (row >= 1) ? this.site[row-1][col] : null;
        Site leftSite = (col >= 1) ? this.site[row][col-1] : null;
        Site rightSite = (col <= n-2) ? this.site[row][col+1] : null;
        Site bottomSite = (row <= n-2) ? this.site[row+1][col] : null;
        Site[] adjacentSites = {topSite, leftSite, rightSite, bottomSite};
        int track = 0;
        for (Site adjacentSite: adjacentSites) {
            if (adjacentSite != null && adjacentSite.type != SiteType.blocked) {
                track += 1;
            }
        }
        if (0 == track) { return; }

        Site topSiteRoot = (topSite != null && topSite.type != SiteType.blocked) ?
                           getRootFor(topSite) : null;
        Site leftSiteRoot = (leftSite != null && leftSite.type != SiteType.blocked) ?
                            getRootFor(leftSite) : null;
        Site rightSiteRoot = (rightSite != null && rightSite.type != SiteType.blocked) ?
                             getRootFor(rightSite) : null;
        Site bottomSiteRoot = (bottomSite != null && bottomSite.type != SiteType.blocked) ?
                              getRootFor(bottomSite) : null;


        Location maxSizeSiteLoc = this.getLocWithMaxSize((topSiteRoot != null) ? topSiteRoot.rootLoc: null,
                                                         (leftSiteRoot != null) ? leftSiteRoot.rootLoc: null,
                                                         (rightSiteRoot != null) ? rightSiteRoot.rootLoc: null,
                                                         (bottomSiteRoot != null) ? bottomSiteRoot.rootLoc: null);
        if (maxSizeSiteLoc == null) {
            return;
        }

        this.site[row][col].rootLoc = maxSizeSiteLoc;
        if (topSiteRoot != null && this.areLocationsEqual(maxSizeSiteLoc, topSiteRoot.rootLoc)) {
            this.updateRootLocForSites(topSiteRoot, leftSiteRoot, rightSiteRoot,
                                       bottomSiteRoot);
        }
        else if (leftSiteRoot != null && this.areLocationsEqual(maxSizeSiteLoc, leftSiteRoot.rootLoc)) {
            this.updateRootLocForSites(leftSiteRoot, topSiteRoot, rightSiteRoot,
                                       bottomSiteRoot);
        }
        else if (rightSiteRoot != null && this.areLocationsEqual(maxSizeSiteLoc, rightSiteRoot.rootLoc)) {
            this.updateRootLocForSites(rightSiteRoot, topSiteRoot, leftSiteRoot,
                                       bottomSiteRoot);
        }
        else {
            this.updateRootLocForSites(bottomSiteRoot, topSiteRoot, leftSiteRoot,
                                       rightSiteRoot);
        }

        track = 0;
        for (Site adjacentSite: adjacentSites) {
            if (adjacentSite != null && adjacentSite.type == SiteType.full) {
                track += 1;
            }
        }

        // current site is full or atleast one of the adjacent sites are full
        if (track > 0 || this.site[row][col].type == SiteType.full) {
            this.site[maxSizeSiteLoc.row][maxSizeSiteLoc.col].type = SiteType.full;
        } else {
            // current site is open & all adjacent site are either blocked or open but not full
            return;
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        // SiteType currentSiteType = this.getRootFor(this.site[row][col]).type;
        // if (SiteType.full == currentSiteType || SiteType.open == currentSiteType) {
        //     return true;
        // }
        if (SiteType.blocked != this.site[row][col].type) {
            return true;
        }
        return false;
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (SiteType.full == this.getRootFor(this.site[row][col]).type) {
            return true;
        }
        return false;
    }
    public int numberOfOpenSites()       // number of open sites
    {
        return this.noOfOpenSites;
    }
    public boolean percolates()              // does the system percolate?
    {
        for (int i = 0; i < this.n; i++) {
            // if (SiteType.full == this.site[this.n-1][i].type) {
            //     return true;
            // }
            if (SiteType.full == this.getRootFor(this.site[this.n-1][i]).type) {
                return true;
            }
        }
        return false;
    }
    public Site[][] getGrid() {
        return this.site.clone();
    }
    public double getThreshold() {
        return (double)(this.noOfOpenSites)/(this.n*this.n);
    }
    public static void main(String[] args) {
        int number = 5;
        Percolation grid = new Percolation(number);
        // Site site[][] = grid.getGrid();
        // System.out.println("Size: "+ site.length);
        int row = 0, col = 0;
        while(true) {
            row = StdRandom.uniform(0, number);
            col = StdRandom.uniform(0, number);
            System.out.println("("+row+","+col+")");
            grid.open(row, col);
            if (grid.percolates()) {
                System.out.println("The system percolates!");
                System.out.println("The threshold value is:" + grid.getThreshold());
                break;
            }
        }
    }
}

final class Location {
    int row;
    int col;
}

enum SiteType {
    blocked,
    open,
    full
}

final class Site {
    SiteType type;
    int value;
    Location rootLoc;
}