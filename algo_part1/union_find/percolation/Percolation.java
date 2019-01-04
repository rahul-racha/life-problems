/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private int noOfOpenSites = 0;
    private final class Location {
        int row;
        int col;
    }
    private enum SiteType {
        blocked,
        open,
        full
    }
    private final class Site {
        SiteType type;
        Location rootLoc;
        Location loc;
    }
    private int n;
    private Site[][] site;
    private int[][] size;
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
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    this.site[i][j] = new Site();
                    this.site[i][j].type = SiteType.blocked;
                    Location location = new Location();
                    location.row = i;
                    location.col = j;
                    this.site[i][j].rootLoc = location;
                    this.site[i][j].loc = location;
                    this.size[i][j] = 1;
                }
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception);
        }
    }
    private Site getRootFor(Site localSite) {
        while (localSite.loc != localSite.rootLoc) {
            Location location = this.site[localSite.rootLoc.row][localSite.rootLoc.col].rootLoc; // parent
            location = this.site[location.row][location.col].rootLoc; // grandparent
            location = this.site[location.row][location.col].rootLoc; // ancester
            localSite = this.site[location.row][location.col];
        }
        return localSite;
    }
    private boolean areLocationsEqual(Location loc1, Location loc2) {
        if (loc1 != null && loc2 != null && loc1.row == loc2.row && loc1.col == loc2.col) {
            return true;
        }
        return false;
    }
    private Location getMaxLocation(Location firstLoc, Location secondLoc) {
        if (firstLoc != null && secondLoc != null) {
            if (this.size[firstLoc.row][firstLoc.col] >=
                    this.size[secondLoc.row][secondLoc.col]) {
                return firstLoc;
            }
            else {
                return secondLoc;
            }
        } else if (firstLoc != null && secondLoc == null) {
            return firstLoc;
        } else if (firstLoc == null && secondLoc != null) {
            return secondLoc;
        } else {
            return null;
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
    private void applyUnionOnRoots(Location maxSizeSiteLoc, Site topSiteRoot, Site leftSiteRoot, Site rightSiteRoot,
                            Site bottomSiteRoot) {

        if (maxSizeSiteLoc == null) {
            return;
        }
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
        else if (bottomSiteRoot != null && this.areLocationsEqual(maxSizeSiteLoc, bottomSiteRoot.rootLoc)) {
            this.updateRootLocForSites(bottomSiteRoot, topSiteRoot, leftSiteRoot,
                                       rightSiteRoot);
        } else {
            return;
        }
    }
    public void open(int x, int y)    // open site (row, col) if it is not open already
    {
        try {
            if (x < 1 || x > this.n || y < 1 || y > this.n) {
                throw new IllegalArgumentException("invalid arguments");
            }
            int row = x - 1;
            int col = y - 1;
            if (this.site[row][col].type == SiteType.blocked) {
                this.site[row][col].type = (0 == row) ? SiteType.full : SiteType.open;
                this.noOfOpenSites += 1;
            }
            else {
                return;
            }
            // check adjacent sites
            Site topSite = (row >= 1) ? this.site[row - 1][col] : null;
            Site leftSite = (col >= 1) ? this.site[row][col - 1] : null;
            Site rightSite = (col <= n - 2) ? this.site[row][col + 1] : null;
            Site bottomSite = (row <= n - 2) ? this.site[row + 1][col] : null;
            Site[] adjacentSites = { topSite, leftSite, rightSite, bottomSite };
            int track = 0;
            for (Site adjacentSite : adjacentSites) {
                if (adjacentSite != null && adjacentSite.type != SiteType.blocked) {
                    track += 1;
                }
            }
            if (0 == track) {
                return;
            }

            Site topSiteRoot = (topSite != null && topSite.type != SiteType.blocked) ?
                               getRootFor(topSite) : null;
            Site leftSiteRoot = (leftSite != null && leftSite.type != SiteType.blocked) ?
                                getRootFor(leftSite) : null;
            Site rightSiteRoot = (rightSite != null && rightSite.type != SiteType.blocked) ?
                                 getRootFor(rightSite) : null;
            Site bottomSiteRoot = (bottomSite != null && bottomSite.type != SiteType.blocked) ?
                                  getRootFor(bottomSite) : null;


            Location maxSizeSiteLoc = this
                    .getLocWithMaxSize((topSiteRoot != null) ? topSiteRoot.rootLoc : null,
                                       (leftSiteRoot != null) ? leftSiteRoot.rootLoc : null,
                                       (rightSiteRoot != null) ? rightSiteRoot.rootLoc : null,
                                       (bottomSiteRoot != null) ? bottomSiteRoot.rootLoc : null);
            if (maxSizeSiteLoc == null) {
                return;
            }

            this.site[row][col].rootLoc = maxSizeSiteLoc;
            this.applyUnionOnRoots(maxSizeSiteLoc, topSiteRoot, leftSiteRoot, rightSiteRoot,
                                   bottomSiteRoot);

            track = 0;
            for (Site adjacentSite : adjacentSites) {
                if (adjacentSite != null && adjacentSite.type == SiteType.full) {
                    track += 1;
                }
            }

            // current site is full or atleast one of the adjacent sites are full
            if (track > 0 || this.site[row][col].type == SiteType.full) {
                this.site[maxSizeSiteLoc.row][maxSizeSiteLoc.col].type = SiteType.full;
            }
            else {
                // current site is open & all adjacent site are either blocked or open but not full
                return;
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception);
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        try {
            if (row < 1 || row > this.n || col < 1 || col > this.n) {
                throw new IllegalArgumentException("invalid arguments");
            }
            if (SiteType.blocked != this.site[row-1][col-1].type) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception);
            return false;
        }
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        try {
            if (row < 1 || row > this.n || col < 1 || col > this.n) {
                throw new IllegalArgumentException("invalid arguments");
            }
            if (SiteType.full == this.getRootFor(this.site[row-1][col-1]).type) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception);
            return false;
        }
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
    public static void main(String[] args) {
        int number = 5;
        Percolation grid = new Percolation(number);
        int row = 0, col = 0;
        while (true) {
            row = StdRandom.uniform(1, number+1);
            col = StdRandom.uniform(1, number+1);
            System.out.println("("+row+","+col+")");
            grid.open(row, col);
            // if (grid.isOpen(row,col)) { System.out.println("The point is open"); }
            // if (grid.isFull(row, col)) { System.out.println("The point is full"); }
            if (grid.percolates()) {
                System.out.println("The system percolates!");
                System.out.println("The threshold value is:" + (double) (grid.numberOfOpenSites())/(number*number));
                break;
            }
        }
    }
}