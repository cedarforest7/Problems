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
}
