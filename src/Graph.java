import java.util.*;

public class Graph {

    //lintcode 615
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // BFS
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0].length == 0) {
            return true;
        }
        int n = numCourses;
        //build an adjacency list for topological sorting
        Map<Integer, Set<Integer>> adj = new HashMap<>();
        int[] inDegree = new int[n];
        for (int[] arr : prerequisites) {
            int pre = arr[0];
            int post = arr[1];
            adj.putIfAbsent(pre, new HashSet<Integer>());
            adj.putIfAbsent(post, new HashSet<Integer>());
            Set<Integer> tempSet = adj.get(pre);
            if (!tempSet.contains(post)) {
                //avoid duplicate edges being added
                tempSet.add(post);
                inDegree[post]++;
            }
        }

        Queue<Integer> fringe = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        //add all courses with in-degree 0
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                fringe.offer(i);
                set.add(i);
            }
        }
        int ts = 0;
        //if cycle,return false. Else true.
        while (!fringe.isEmpty()) {
            int temp = fringe.poll();
            ts++;
            if (adj.keySet().contains(temp)) {
                //if adj list does not contain the course
                for (int course : adj.get(temp)) {
                    //inDegree decrease by 1
                    inDegree[course]--;
                    if (inDegree[course] == 0) {
                        set.add(course);
                        fringe.offer(course);
                    }
                }
            }

        }
        return ts == n;
    }

    //lintcode 611
    class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }
    }
    int rows = 0;
    int cols = 0;
    public int shortestPath(boolean[][] grid, Point source, Point destination) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        rows = grid.length;
        cols = grid[0].length;
        if (!inGrid(source, grid) || !inGrid(destination, grid)) {
            return -1;
        }
        if (isDest(source.x, source.y, destination)) {
            return 0;
        }
        Queue<Point> fringe = new LinkedList<>();
        //Set<Integer> visited = new HashSet<>();
        int step = 0;
        int[][] move = {{1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}};
        int codeSource = code(source);
        fringe.offer(source);
        //visited.add(codeSource);
        while (!fringe.isEmpty()) {
            int size = fringe.size();
            step++;
            for (int k = 0; k < size; k++) {
                Point p = fringe.poll();
                for (int i = 0; i < 8; i++) {
                    int x = p.x + move[i][0];
                    int y = p.y + move[i][1];
                    if (isDest(x, y, destination)) {
                        return step;
                    }
                    if (inGrid(x, y, grid)) {
                        Point temp = new Point(x, y);
                        fringe.offer(temp);
                        grid[x][y] = true;
                    }
                }
            }
        }
        return -1;
    }

    private int code(Point p) {
        //code for coord (i, j) is i*cols + j
        return code(p.x, p.y);
    }

    private int code(int x, int y) {
        return x * cols + y;
    }

    private boolean inGrid(Point p, boolean[][] grid) {
        return inGrid(p.x, p.y, grid);
    }

    private boolean inGrid(int x, int y, boolean[][] grid) {
        return x >= 0 && x < rows && y >= 0 && y < cols && !grid[x][y];
    }

    private boolean isDest(int x, int y, Point dest) {
        return x == dest.x && y == dest.y;
    }
}
