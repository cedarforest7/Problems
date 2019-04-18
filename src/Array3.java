import java.util.*;

public class Array3 {
    //239
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null) {
            return null;
        }
        if (nums.length == 0 || k < 1) {
            return new int[0];
        }
        int len = k > nums.length ? 1 : nums.length - k + 1;
        int[] res = new int[len];
        LinkedList<Integer> lis = new LinkedList<>();   //monotonically decreasing stack
        for (int i = 0; i < nums.length; i++) {
            pushMonoStack(lis, nums[i]);
            if (i >= k - 1) {
                int max = lis.peekFirst();
                res[i - k + 1] = max;
                if (max == nums[i - k + 1]) {
                    lis.pollFirst();
                }
            }
        }
        return res;
    }

    private void pushMonoStack(LinkedList<Integer> lis, int x) {
        while (!lis.isEmpty() && lis.peekLast() < x) {
            lis.pollLast();
        }
        lis.offerLast(x);
    }

    //939
    public int minAreaRect(int[][] points) {
        if (points == null || points.length < 4) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        Map<Integer, Set<Integer>> map = new HashMap<>();   //key: x, val: set of y
        for (int i = 0; i < points.length; i++) {
            int x = points[i][0];
            int y = points[i][1];
            map.putIfAbsent(x, new HashSet<>());
            map.get(x).add(y);
        }
        for (int i = 0; i < points.length; i++) {
            int x1 = points[i][0];
            int y1 = points[i][1];
            for (int j = i + 1; j < points.length; j++) {

                int x2 = points[j][0];
                int y2 = points[j][1];
                if (x1 == x2 || y1 == y2) {
                    continue;
                }
                //i, j as diagonal
                if (map.get(x1).contains(y2) && map.get(x2).contains(y1)) {
                    min = Math.min(min, Math.abs(x1 - x2) * Math.abs(y1 - y2));
                }
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public int minAreaRect1(int[][] points) {
        if (points == null || points.length < 4) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        boolean flag = false;
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] a1, int[] a2) {
                return a1[0] - a2[0];
            }
        });
        Map<Integer, Map<Integer, List<Integer>>> edges = new HashMap<>();   //key: x1, val: map (key: x, val: list of y)
        //each triple entry represents an edge parallel to x axis
        for (int i = 0; i < points.length; i++) {
            int x = points[i][0];
            int y = points[i][1];
            edges.putIfAbsent(x, new HashMap<>());
            Map<Integer, List<Integer>> map = edges.get(x);
            for (int j = i + 1; j < points.length; j++) {
                if (points[j][1] != y) {
                    continue;
                }
                int x2 = points[j][0];
                map.putIfAbsent(x2, new ArrayList<>());
                map.get(x2).add(y);
            }
        }

        for (int x1 : edges.keySet()) {
            Map<Integer, List<Integer>> map = edges.get(x1);
            for (int x2 : map.keySet()) {
                int len = x2 - x1;
                List<Integer> lis = map.get(x2);
                Collections.sort(lis);
                for (int i = 1; i < lis.size(); i++) {
                    int diff = lis.get(i) - lis.get(i - 1);
                    min = Math.min(len * diff, min);
                    flag = true;
                }
            }
        }
        return flag ? min : 0;
    }

    public static void main(String[] args) {
        Array3 ar = new Array3();
        int[][] points = {{1,1},{1,3},{3,1},{3,3},{2,2}};
        System.out.println(ar.minAreaRect(points));
    }
}
