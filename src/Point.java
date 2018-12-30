import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Point {
    int x;
    int y;
    Point() { x = 0; y = 0; }
    Point(int a, int b) { x = a; y = b; }

    //lintcode 612

    /*return these points sorted by distance,
    if they are same in distance, sorted by the x-axis, and if they are same in the x-axis, sorted by y-axis. */
    public Point[] kClosest(Point[] points, Point origin, int k) {
        if (points == null || points.length == 0) {
            return points;
        }


        PriorityQueue<Point> pq = new PriorityQueue<>(points.length, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int d1 = distance(p1, origin);
                int d2 = distance(p2, origin);
                if (d1 != d2) {
                    return d1 - d2;
                }
                if (p1.x != p2.x) {
                    return p1.x - p2.x;
                }
                return p1.y - p2.y;

            }
        });

        for (Point p : points) {
            pq.add(p);
        }
        int i = 0;
        Point[] res = new Point[k];
        while (!pq.isEmpty() && i < k) {
            res[i] = pq.poll();
            i++;
        }
        return res;
    }

    private int distance(Point p1, Point p2) {
        int difX = p1.x - p2.x;
        int difY = p1.y - p2.y;
        return difX * difX + difY * difY;
    }

    //lintcode 611
    public int shortestPath(boolean[][] grid, Point source, Point destination) {
        // write your code here
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        if (!inBoard(grid, source) || !inBoard(grid, destination) || grid[source.x][source.y] || grid[destination.x][destination.y] ) {
            return -1;
        }
        Queue<Point> queue = new LinkedList<>();
        queue.offer(source);
        grid[source.x][source.y] = true;        //mark as visited
        int step = -1;
        int[][] moves = {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
        while (!queue.isEmpty()) {
            int size = queue.size();
            step++;
            for (int i = 0; i < size; i++) {
                Point p = queue.poll();

                if (isDest(p, destination)) {
                    return step;
                }

                //add neighbors
                for (int[] move : moves) {
                    Point next = new Point(p.x + move[0], p.y + move[1]);
                    if (inBoard(grid, next) && !visited(grid, next)) {
                        queue.offer(next);
                        grid[next.x][next.y] = true;        //mark as visited
                        //System.out.println(step + ": " + next.x + " " + next.y);
                    }
                }
            }

        }

        return -1;
    }

    private boolean inBoard(boolean[][] grid, Point p) {
        return p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length;
    }

    private boolean isDest(Point p, Point destination) {
        return p.x == destination.x && p.y == destination.y;
    }

    private boolean visited(boolean[][] grid, Point p) {
        return grid[p.x][p.y];
    }
    public static void main(String[] args) {
        boolean[][] grid = new boolean[3][3];
        grid[0][1] = true;
        Point a = new Point(2, 0);
        Point b = new Point(2, 2);
        System.out.println(a.shortestPath(grid, a, b));
    }
}
