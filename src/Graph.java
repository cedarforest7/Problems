import javax.naming.ldap.UnsolicitedNotification;
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

    //lintcode 121
    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        List<List<String>> res = new ArrayList<>();
        if (start == null || end == null || dict == null) {
            return res;
        }

        dict.add(start);
        dict.add(end);
        Map<String, Integer> distance = new HashMap<>();
        Map<String, List<String>> next = new HashMap<>();
        bfs121(start, end, dict, distance, next);
        dfs121(start, end, res, new ArrayList<String>(), distance, next);
        return res;
    }

    private void dfs121(String current, String end, List<List<String>> res, List<String> pre, Map<String, Integer> distance, Map<String, List<String>> next) {
        if (current.equals(end)) {
            pre.add(current);
            res.add(new ArrayList<>(pre));
            pre.remove(pre.size() - 1);
            return;
        }
        pre.add(current);
        for (String s : next.get(current)) {
            dfs121(s, end, res, pre, distance, next);
        }
        pre.remove(pre.size() - 1);
    }

    private void bfs121(String start, String end, Set<String> dict, Map<String, Integer> distance, Map<String, List<String>> next) {
        Queue<String> fringe = new LinkedList<>();
        //Set<String> visited = new HashSet<>();
        fringe.offer(start);
        distance.put(start, 0);
        int endDist = Integer.MAX_VALUE;

        for (String s : dict) {
            next.put(s, new ArrayList<>());
        }
        while(!fringe.isEmpty()) {
            String temp = fringe.poll();
            //System.out.println(temp + " " + distance.get(temp));
            if (distance.get(temp) >= endDist) {
                return;
            }

            List<String> neighbors = findNeighbors(temp, dict);

            for (String s : neighbors) {

                if (distance.containsKey(s)) {
                    int dist = distance.get(s);
                    if (dist == distance.get(temp) + 1) {
                        next.get(temp).add(s);
                    }
                } else {
                    fringe.offer(s);
                    next.get(temp).add(s);
                    distance.put(s, distance.get(temp) + 1);
                    if (s.equals(end)) {
                        endDist = distance.get(temp) + 1;

                    }
                }

            }
        }


    }

    private List<String> findNeighbors(String s, Set<String> dict) {
        List<String> next = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == current) {
                    continue;
                }
                StringBuilder temp = new StringBuilder(s);
                temp.setCharAt(i, c);
                String s1 = temp.toString();
                if (dict.contains(s1)) {
                    next.add(s1);
                }
            }
        }
        return next;
    }

    //133
    class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;
        UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
    }

    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        //build up the mapping and then clone graph
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        Queue<UndirectedGraphNode> fringe = new LinkedList<>();
        fringe.offer(node);
        map.put(node, new UndirectedGraphNode(node.label));
        while (!fringe.isEmpty()) {
            UndirectedGraphNode temp = fringe.poll();
            UndirectedGraphNode temp2 = map.get(temp);
            for (UndirectedGraphNode neib : temp.neighbors) {
                if (!map.containsKey(neib)) {
                    fringe.offer(neib);
                    map.put(neib, new UndirectedGraphNode(neib.label));
                }
                temp2.neighbors.add(map.get(neib));
            }
        }
        return map.get(node);
    }

    //127
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (beginWord.equals(endWord) || !dict.contains(endWord)) {
            return 0;
        }
        //build adjacency list, O(mn)
        wordList.add(beginWord);
        Map<String, Set<String>> adj = new HashMap<>();
        for (String word : wordList) {
            adj.put(word, new HashSet<>());
            for (int i = 0; i < word.length(); i++) {
                StringBuilder sb = new StringBuilder(word);
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == word.charAt(i)) {
                        continue;
                    }
                    sb.setCharAt(i, c);
                    String neib = sb.toString();
                    if (dict.contains(neib)) {
                        adj.get(word).add(neib);
                    }
                }
            }
        }

        //BFS
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(beginWord);
        visited.add(beginWord);
        int res = 0;
        while (!queue.isEmpty()) {
            res++;
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                String temp = queue.poll();
                if (temp.equals(endWord)) {
                    return res;
                }
                for (String neib : adj.get(temp)) {
                    if (!visited.contains(neib)) {
                        queue.offer(neib);
                        visited.add(neib);
                    }
                }
            }
        }
        return 0;
    }

    //269
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        if (words.length == 1) {
            return words[0];
        }
        int[] degree = new int[26];
        Map<Character, Set<Character>> adj = new HashMap<>();   //adjacency list

        //build a graph
        for (int i = 0; i < words.length - 1; i++) {
            //compare two adjacent words only
            int len1 = words[i].length(), len2 = words[i + 1].length();
            int j = 0;
            for (; j < Math.min(len1, len2); j++) {
                char c1 = words[i].charAt(j), c2 = words[i + 1].charAt(j);
                adj.putIfAbsent(c1, new HashSet<>());
                adj.putIfAbsent(c2, new HashSet<>());
                if (c1 != c2) {
                    if (!adj.get(c1).contains(c2)) {
                        degree[c2 - 'a']++;
                        adj.get(c1).add(c2);
                    }
                    break;
                }
            }
            for (int k = j; k < len1; k++) {
                adj.putIfAbsent(words[i].charAt(k), new HashSet<>());
            }
            for (; j < len2; j++) {
                adj.putIfAbsent(words[i + 1].charAt(j), new HashSet<>());
            }
        }

        //topological sorting
        Queue<Character> queue = new LinkedList<>();

        for (char c : adj.keySet()) {
            if (degree[c - 'a'] == 0) {
                //in-degree is 0
                queue.offer(c);
            }
        }

        List<Character> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            char cur = queue.poll();
            res.add(cur);
            for(char c : adj.get(cur)) {
                degree[c - 'a']--;
                if (degree[c - 'a'] == 0) {
                    queue.offer(c);
                }
            }
        }
        if (res.size() != adj.size()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : res) {
            sb.append(c);
        }
        return  sb.toString();
    }



    public static void main(String[] args) {
        Graph g = new Graph();
        /*Set<String> dict = new HashSet<>();
        dict.add("hot");
        dict.add("dot");
        dict.add("dog");
        dict.add("lot");
        dict.add("log");
        String start = "hit";
        String end = "cog";
        List<List<String>> lis = g.findLadders(start, end, dict);
        Helper.printListofList(lis);*/
        //String[] words = { "wrt", "wrf", "er", "ett", "rftt"};
        String[] words = {"vlxpwiqbsg","cpwqwqcd"};
        System.out.println(g.alienOrder(words));
    }
}
