import java.util.Comparator;
import java.util.PriorityQueue;

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
}
