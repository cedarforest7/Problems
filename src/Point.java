import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

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


    public int maxPoints(Point[] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        //deal with duplicates
        Map<Integer, Map<Integer, Integer>> coord = new HashMap<>();
        Set<Integer> dups = new HashSet<>();
        for (int i = 0; i < points.length; i++) {
            int x1 = points[i].x;
            int y1 = points[i].y;
            coord.putIfAbsent(x1, new HashMap<>());
            Map<Integer, Integer> temp = coord.get(x1);
            int count = temp.getOrDefault(y1, 0) + 1;
            if (count != 1) {
                dups.add(i);
            }
            temp.put(y1, count);
        }

        if (points.length < 3) {
            return points.length;
        }
        int max = 0;

        //line has the form of x = const
        Map<Integer, Integer> xCount = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            int x = points[i].x;
            int count = xCount.getOrDefault(x, 0) + 1;
            max = Math.max(max, count);
            xCount.put(x, count);
        }

        //line has the form of y = kx + b
        Map<Integer, Map<BigDecimal, Integer>> map = new HashMap<>();
        //in map: key is the index of Point in points, value is the slope of lines that the point is on and its corresponding # points
        //traverse all possible lines
        for (int i = 0; i < points.length; i++) {
            if (dups.contains(i)) {
                continue;
            }
            Map<BigDecimal, Integer> count = new HashMap<>();
            map.put(i, count);
            int x1 = points[i].x;
            int y1 = points[i].y;
            int dupCount = coord.get(x1).get(y1);
            for(int j = i + 1; j < points.length; j++) {
                if (dups.contains(j)) {
                    continue;
                }
                int x2 = points[j].x;
                int y2 = points[j].y;
                if (x1 == x2) {
                    continue;
                }
                BigDecimal k = new BigDecimal(y2 - y1).divide(new BigDecimal(x2 - x1), 16, RoundingMode.HALF_UP);
                int kCount = count.getOrDefault(k, dupCount) + coord.get(x2).get(y2);
                max = Math.max(max, kCount);
                count.put(k, kCount);
            }
        }

        return max;
    }

    public static void main(String[] args) {
        /*boolean[][] grid = new boolean[3][3];
        grid[0][1] = true;
        Point a = new Point(2, 0);
        Point b = new Point(2, 2);
        System.out.println(a.shortestPath(grid, a, b));*/
        System.out.println(new BigDecimal(1234, MathContext.DECIMAL64).divide(new BigDecimal(5678, MathContext.DECIMAL64)));
    }
}
