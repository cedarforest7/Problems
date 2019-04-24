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

    //18
    /*public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        *//*Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            threeSum(nums, target, i,  res);
        }*//*
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
        }
        List<Integer> numbers = new ArrayList<>(count.keySet());
        Map<Integer, Map<Integer, Integer>> twoSum = new HashMap<>();  //key: sum, val: (key, val are a pair of ints, key <= val)
        //get all tuples
        for (int i = 0; i < numbers.size(); i++) {
            int cur = numbers.get(i);
            if (count.get(cur) > 1) {
                twoSum.putIfAbsent(cur * 2, new HashMap<>());
                twoSum.get(cur * 2).put(cur, cur);
            }
            for (int j = i + 1; j < numbers.size(); j++) {
                int next = numbers.get(j);
                int sum = cur + next;
                twoSum.putIfAbsent(sum, new HashMap<>());
                if (cur <= next) {
                    twoSum.get(sum).put(cur, next);
                } else {
                    twoSum.get(sum).put(next, cur);
                }
            }
        }

        //traverse all tuples
        List<Integer> sums = new ArrayList<>(twoSum.keySet());
        Collections.sort(sums);
        for (int i = 0; i < sums.size(); i++) {
            int sum = sums.get(i);
            Map<Integer, Integer> pairs = twoSum.get(sum);
            for (int j = sums.size() - 1; j >= i; j--) {
                Map<Integer, Integer> pairs2 = twoSum.get(target - sum);
                List<Integer> lis = new ArrayList<>();
                for (int k1 : pairs.keySet()) {
                    int m = pairs.get(k1);
                    for (int k2 : pairs2.keySet()) {
                        int n = pairs.get(k2);
                    }
                }
            }
        }
    }*/
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            threeSum(nums, target, i,  res);
        }
        return res;
    }

    private void threeSum(int[] nums, int target, int index, List<List<Integer>> res) {
        int sum = target - nums[index];

        for (int i = index + 1; i < nums.length; i++) {
            if (i > index + 1 &&  nums[i] == nums[i - 1]) {
                continue;
            }
            List<Integer> lis = new ArrayList<>();
            lis.add(nums[index]);
            lis.add(nums[i]);
            twoSum(nums, sum - nums[i], i + 1, lis, res);
        }
    }

    private void twoSum(int[] nums, int target, int start, List<Integer> lis, List<List<Integer>> res) {
        int i = start, j = nums.length - 1;
        while (j > i) {
            if (nums[i] + nums[j] > target) {
                j--;
            } else if (nums[i] + nums[j] < target) {
                i++;
            } else {
                List<Integer> temp = new ArrayList<>(lis);
                temp.add(nums[i]);
                temp.add(nums[j]);
                res.add(temp);
                i++;
                j--;
                while (i < nums.length && nums[i] == nums[i - 1]) {
                    i++;
                }
                while (j >= start && nums[j] == nums[j + 1]) {
                    j--;
                }

            }
        }
    }

    public static void main(String[] args) {
        Array3 ar = new Array3();
//        int[][] points = {{1,1},{1,3},{3,1},{3,3},{2,2}};
//        System.out.println(ar.minAreaRect(points));
        int[] nums = {1, 0, 0, 0, -1, 0, -2, 2};
        Helper.printListofListInt(ar.fourSum(nums, 0));
    }
}
