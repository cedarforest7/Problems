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

    //lintcode 605
    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        //if there is only one TS of directed graph and whether that TS is org
        if (seqs == null) {
            return false;
        }
        int len = seqs.length;
        if (len == 0 || seqs[0].length == 0) {
            if (org.length == 0) {
                return true;
            }
            return false;
        }

        //adj list
        Map<Integer, Set<Integer>> adj = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int[] arr : seqs) {
            for (int i = 0; i < arr.length; i++) {
                adj.putIfAbsent(arr[i], new HashSet<Integer>());
                inDegree.putIfAbsent(arr[i], 0);
                if (i < arr.length - 1) {
                    adj.putIfAbsent(arr[i + 1], new HashSet<Integer>());
                    inDegree.putIfAbsent(arr[i + 1], 0);
                    Set<Integer> tempSet = adj.get(arr[i]);
                    if (!tempSet.contains(arr[i + 1])) {
                        tempSet.add(arr[i + 1]);
                        inDegree.put(arr[i + 1], inDegree.get(arr[i + 1]) + 1);
                    }
                }

            }
        }

        Queue<Integer> fringe = new LinkedList<>();
        //Set<Integer> visited = new HashSet<>();
        for (int x : adj.keySet()) {
            //in degree is 0
            if (inDegree.get(x) == 0) {
                fringe.offer(x);
                //visited.add(x);

            }
        }
        int tsLen = 0;
        int[] res = new int[org.length];
        while (!fringe.isEmpty()) {
            if (fringe.size() > 1) {
                return false;
            }
            int cur = fringe.poll();
            if (tsLen >= org.length) {
                return false;
            }
            res[tsLen] = cur;
            //System.out.println(cur);
            tsLen++;
            for (int x : adj.get(cur)) {
                int ind = inDegree.get(x);
                inDegree.put(x, ind - 1);
                if (ind - 1 == 0) {
                    fringe.offer(x);
                    //visited.add(x);
                }
            }
        }

        if (Arrays.equals(res, org)) {
            return true;
        }
        return false;
    }

    class DirectedGraphNode {
        int label;
        ArrayList<DirectedGraphNode> neighbors;
        DirectedGraphNode(int x) { label = x; neighbors = new ArrayList<DirectedGraphNode>(); }
    }
    //lintcode 127
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        //TS
        if (graph == null) {
            return null;
        }
        ArrayList<DirectedGraphNode> res = new ArrayList<>();
        Queue<DirectedGraphNode> fringe = new LinkedList<>();
        Set<DirectedGraphNode> visited = new HashSet<>();
        Map<DirectedGraphNode, Integer> inDegree = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            if (!visited.contains(node)) {
                inDegree.putIfAbsent(node, 0);
                visited.add(node);
                for (DirectedGraphNode neib : node.neighbors) {
                    inDegree.putIfAbsent(neib, 0);
                    inDegree.put(neib, inDegree.get(neib) + 1);
                }
            }
        }
        visited.clear();
        //add all inDegree 0 nodes
        for (DirectedGraphNode node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                fringe.offer(node);
                visited.add(node);
            }
        }

        //TS
        while (!fringe.isEmpty()) {
            DirectedGraphNode cur = fringe.poll();
            res.add(cur);
            //System.out.println(cur.label);
            for (DirectedGraphNode neib : cur.neighbors) {
                int temp = inDegree.get(neib);
                inDegree.put(neib, temp - 1);
                if (temp - 1 == 0 && !visited.contains(neib)) {
                    fringe.offer(neib);
                    visited.add(neib);
                }
            }
        }
        if (res.size() == inDegree.size()) {
            return res;
        }
        //System.out.println("no TS");
        return null;
    }
}
