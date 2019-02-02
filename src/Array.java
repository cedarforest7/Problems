import java.util.*;

public class Array {
    //Given a non-empty array of integers, every element appears twice except for one. Find that single one.
    public static int singleNumber(int[] nums) {
        HashMap<Integer, Boolean> m = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (m.containsKey(nums[i])) {
                m.remove(nums[i]);
            } else {
                m.put(nums[i], true);
            }
        }
        if (m.size() == 0) {
            return 0;
        }
        for (Integer x : m.keySet()) {
            return x;
        }
        return -1;
    }
    //Given an array of numbers nums, in which exactly two elements appear
    // only once and all the other elements appear exactly twice. Find the two elements that appear only once.
    public int[] singleNumber3(int[] nums) {
        HashMap<Integer, Boolean> m = new HashMap<>();
        int[] single = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (m.containsKey(nums[i])) {
                m.remove(nums[i]);
            } else {
                m.put(nums[i], true);
            }
        }
        if (m.size() == 0) {
            return null;
        }
        int i = 0;
        for (Integer x : m.keySet()) {
            single[i] = x;
            i++;
        }
        return single;
    }

    //Given an array nums, write a function to move all 0's to the end of it
    // while maintaining the relative order of the non-zero elements.
    public static void moveZeroes(int[] nums) {
        int temp;
        for (int i = 0, j = -1; i < nums.length; i++) {
            if (nums[i] != 0) {
                temp = nums[j + 1];
                nums[j + 1] = nums[i];
                nums[i] = temp;
                j++;
            }
        }
    }
    //Given an array of integers that is already sorted in ascending order,
    // find two numbers such that they add up to a specific target number.
    //Your returned answers (both index1 and index2) are not zero-based.
    //You may assume that each input would have exactly one solution and you may not use the same element twice.
    public int[] twoSum(int[] numbers, int target) {
        int len = numbers.length;
        int low = 0, high = len - 1;
        while (numbers[low] + numbers[high] != target) {
            if (numbers[low] + numbers[high] > target) {
                high--;
            } else {
                low++;
            }
        }
        int[] sol = new int[] {low + 1, high + 1};
        return sol;
    }
    //Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
    public int missingNumber1(int[] nums) {
        HashSet<Integer> s = new HashSet<>();
        for (int i = 0; i < nums.length + 1;i++ ) {
            s.add(i);
        }
        for (int x : nums) {
            s.remove(x);
        }
        LinkedList<Integer> l = new LinkedList<>();
        l.addAll(s);
        return l.peek();
    }

    public int missingNumber(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int x : nums) {
            sum = sum + x;
        }
        return n*(n + 1)/2 - sum;
    }

    //Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
    // prove that at least one duplicate number must exist.
    // Assume that there is only one duplicate number, find the duplicate one.
    public int findDuplicate(int[] nums) {
        return 0;       //to be modified
    }

    //Say you have an array for which the ith element is the price of a given stock on day i.
    //
    //If you were only permitted to complete at most one transaction
    // (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.
    public int maxProfit1(int[] prices) {
        int N = prices.length;
        int[] profit = new int[N];
        int maxProfit = 0;
        for (int i = 0; i < N; i++) {
            profit[i] = 0;
            for (int j = i + 1; j < N; j++) {
                if (prices[j] - prices[i] > profit[i]) {
                    profit[i] = prices[j] - prices[i];
                }
            }
            if (profit[i] > maxProfit) {
                maxProfit = profit[i];
            }
        }
        return maxProfit;
    }

    public int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int minPrice = prices[0];
        int maxPrice = prices[0];
        int maxProfit = 0;
        for (int x : prices) {
            if (x < minPrice) {
                minPrice = x;
                maxPrice = x;
            } else if (x > maxPrice) {
                maxPrice = x;
            }
            if (maxPrice - minPrice > maxProfit) {
                maxProfit = maxPrice - minPrice;
            }
        }
        return maxProfit;
    }

    //No.137
    public int singleNumber2(int[] nums) {
        int[] bitDup = new int[32];
        String num = "";
        for (int k = 0; k < 32; k++) {
            bitDup[k] = 0;
            for (int x : nums) {
                bitDup[k] += getBit(x, k);
            }
           bitDup[k] = bitDup[k]%3;
            num = bitDup[k] + num;
        }
        long l = Long.parseLong(num, 2);
        int singleNum = (int)l;
        return singleNum;
    }
    //return kth bit in n;
    private int getBit (int n, int k) {
        return (n >> k) & 1;
    }

    //No.215
    public int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(nums.length, Comparator.reverseOrder());
        for (int x : nums) {
                pq.add(x);
        }
        for (int i = 0; i < k - 1; i++) {
            pq.poll();
        }
        return pq.poll();
    }

    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    //No.27
    public int removeElement1(int[] nums, int val) {
        int end = nums.length - 1;
        for (int i = 0; i < nums.length; i++ ) {
            while (nums[end] == val) {
                if (end == 0) {
                    return 0;
                }
                end--;
            }
            if (i >= end) {
                return end + 1;
            }
            if (nums[i] == val) {
                int temp = nums[i];
                nums[i] = nums[end];
                nums[end] = temp;
                end--;
            }
        }
        return 0;
    }

    public int removeElement(int[] nums, int val) {
        int end = nums.length - 1;
        int i = 0;
        while (i <= end){
            if (nums[i] == val) {
                int temp = nums[i];
                nums[i] = nums[end];
                nums[end] = temp;
                end--;
            } else {
                i++;
            }
        }
        return end + 1;

    }

    private void swap (int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

    //No.153
    public int findMin(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return nums[i + 1];
            }
        }
        return nums[0];
    }

    //No.162
    public int findPeakElement(int[] nums) {
        if (nums.length == 1 || nums[0] > nums[1]) {
            return 0;
        }
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] > nums[i - 1] && nums[i] > nums[i + 1]) {
                return i;
            }
        }
        return nums.length - 1;
    }

    //No.198
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] rob = new int[n + 1];
        rob[0] = 0;
        rob[1] = nums[0];
        for (int i = 2; i < n + 1; i++) {
            rob[i] = Math.max(rob[i - 2] + nums[i - 1], rob[i - 1]);
        }
        return Math.max(rob[n - 1], rob[n]);
    }

    //No.35 binary search
    public int searchInsert(int[] nums, int target) {
        int n = nums.length;
        if (n == 0 || target <= nums[0]) {
            return 0;
        }
        if (target > nums[n - 1]) {
            return n;
        }
        int min = 0, max = n - 1, mid = (n - 1)/2;
        while (mid != min && mid != max) {
            if (target < nums[mid]) {
                max = mid;
                mid = (max + min)/2;
            } else if (target == nums[mid]) {
                return mid;
            } else {
                min = mid;
                mid = (max + min)/2;
            }
        }
        return mid + 1;
    }

    //No.300
    public int lengthOfLIS(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int n = nums.length;
        int[] Q = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            Q[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    Q[i] = Math.max(Q[i], Q[j] + 1);
                }
            }
            max = Math.max(max, Q[i]);
        }
        return max;
    }

    //No.128
    public int longestConsecutive1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int min = nums[0], max = nums[0];
        for (int x : nums) {
            min = Math.min(min, x);
            max = Math.max(max, x);
        }
        int n = max - min + 1;
        int[] arr = new int[n];
        for (int i = 1; i < n - 1; i++ ) {
            arr[i] = min - 2;
        }
        for (int x : nums) {
            arr[x - min] = x;
        }
        int lc = 1, p = 0, q = 0;
        for (int i = 1; i < n - 1; i++) {
            if (arr[i] != min - 2) {
                if (arr[i - 1] == min - 2) {
                    p = i;
                }
                if (arr[i + 1] == min - 2) {
                    q = i;
                }
                lc = Math.max(lc, q - p + 1);
            }
        }
        if (arr[n - 2] != min - 2) {
            q = n - 1;
            lc = Math.max(lc, q - p + 1);
        }
        return lc;
    }

    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int lc = 1, temp = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1] + 2) {
                if (nums[i] == nums[i - 1] + 1) {
                    temp++;
                }
                lc = Math.max(lc, temp);
            } else {
                temp = 1;
            }
        }
        return lc;
    }

    //No.1
    public int[] twoSum1(int[] nums, int target) {
        int[] sol = {-1, -1};
//        Map<Integer, Integer> map = new HashMap<>();        //Map value to index
//        for (int i = 0; i < nums.length; i++) {
//            map.put(i, nums[i]);
//        }
        int n = nums.length;
        int[] copy = new int[n];
        for (int i = 0; i < n; i++) {
            copy[i] = nums[i];
        }
        Arrays.sort(nums);
        int i = 0, j = n - 1;
        while (nums[i] + nums[j] != target) {
            if (nums[i] + nums[j] < target) {
                i++;
            } else {
                j--;
            }
        }
        for (int k = 0; k < n; k++) {
            if (copy[k] == nums[i]) {
                if (sol[0] == -1) {
                    sol[0] = k;
                } else {
                    sol[1] = k;
                }
;            } else if (copy[k] == nums[j]) {
                if (sol[0] == -1) {
                    sol[0] = k;
                } else {
                    sol[1] = k;
                }
            }
        }
        return sol;
    }
    //use a hashMap,directly find target - nums[i] is faster


    //No.42
    public static int trap1(int[] height) {
        int trap = 0;       //total water get trapped
        LinkedList<Integer> localMax = new LinkedList<>();
        //int max = 0;        //global max
        List<Integer> rains = new LinkedList<>();
        int high = 0;
        if (height.length > 1 && height[1] < height[0]) {
            localMax.add(0);
        }
        for (int i = 1; i < height.length; i++) {
            if (height[i] >= height[i - 1] && (i == height.length - 1 || height[i] >= height[i + 1])) {      //local max
                if (height[i] < height[high]) {
                    //rain -= (height[high] - height[i]) * (i - high - 1);
                    List<Integer> temp = new LinkedList<>();
                    for (int x : rains) {
                        if (height[high] - x < height[i]) {
                            temp.add(x - height[high] + height[i]);
                        }
                    }
                    rains = temp;
                }
                for (int x : rains) {
                    System.out.println(x);
                    trap += x;
                }
                rains.clear();
                if (height[i] > height[high]) {
                    int q = high;
                    for (int j = localMax.size() - 1; j >= 0; j--) {
                        int p = localMax.get(j);
                        if (height[p] > height[q] && height[q] < height[i]) {
                            trap += (Math.min(height[i], height[p]) - height[q])*(i - p - 1);
                            q = p;
                        }
                    }
                }
                high = i;
                localMax.add(high);
            }
            if (height[i] < height[high]) {
                rains.add(height[high] - height[i]);
            }
        }
        return trap;
    }
    //above has a bug
    private LinkedList<Integer> localMax(int[] arr) {
        LinkedList<Integer> localMax = new LinkedList<>();
        if (arr.length > 1 && arr[1] < arr[0]) {
            localMax.add(0);
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] >= arr[i - 1] && (i == arr.length - 1 || arr[i] >= arr[i + 1])) {      //local max
                localMax.add(i);
            }
        }
        return localMax;
    }

    private int[] waterLevel(List<Integer> localMax, int[] height) {
        int[] level = new int[localMax.size() - 1];
        for (int i = 1; i < localMax.size(); i++) {
            int a = localMax.get(i);
            int b = localMax.get(i - 1);
            level[i - 1] = Math.min(height[a], height[b]);
            if (height[a] > height[b]) {
                int q = b;
                for (int j = i - 1; j >= 0; j--) {
                    int p = localMax.get(j);
                    if (height[p] > height[q] && height[q] < height[a]) {
                        for (int k = j; k < i; k++) {
                            level[k] = Math.min(height[p], height[a]);
                        }
                        q = p;
                    }
                }
            }
        }
        return level;
    }

    public int trap(int[] height) {
        if (height.length < 3) {
            return 0;
        }
        int trap = 0;       //total water get trapped
        LinkedList<Integer> localMax = localMax(height);
        int[] level = waterLevel(localMax, height);
        for (int i = 0; i < level.length; i++) {
            for (int j = localMax.get(i); j < localMax.get(i + 1); j++) {
                if (height[j] < level[i]) {
                    trap += level[i] - height[j];
                }
            }
        }
        return trap;
    }

    LinkedList<Integer> max = new LinkedList<>();
    public int trap2(int[] height) {
        if (height.length < 3) {
            return 0;
        }
        int trap = 0;       //total water get trapped
        Map<Integer, Integer> rain = waterLevel2(height);
        for (int i = 0; i < max.size() - 1; i++) {
            for (int j = max.get(i); j < max.get(i + 1); j++) {
                if (height[j] < rain.get(max.get(i))) {
                    trap += rain.get(max.get(i)) - height[j];
                }
            }
        }
        max.clear();
        return trap;
    }

    private Map<Integer, Integer> waterLevel2 (int[] arr) {
        if (arr.length > 1 && arr[1] < arr[0]) {
            max.add(0);
            //System.out.println(0);      //for test
        }
        Map<Integer, Integer> rain = new HashMap<>();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] >= arr[i - 1] && (i == arr.length - 1 || arr[i] >= arr[i + 1])) {      //local max
                int a = i;
                if (max.peekLast() == null) {
                    max.add(i);
                    continue;
                }
                int b = max.peekLast();
                rain.put(b, Math.min(arr[a], arr[b]));
                if (arr[a] > arr[b]) {
                    int q = b;
                    for (int j = max.size() - 1; j >= 0; j--) {
                        int p = max.get(j);
                        if (arr[p] > arr[q] && arr[q] < arr[a]) {
                            for (int k = j; k < max.size(); k++) {
                                rain.put(max.get(k), Math.min(arr[p], arr[a]));
                            }
                            q = p;
                        }
                    }
                }
                max.add(i);
            }
        }
        //max.clear();        //to be deleted
        return rain;
    }

    //use a stack will be helpful in this case

    public static int trap3(int[] height) {
        if (height.length < 3) {
            return 0;
        }
        int n = height.length;
        int leftMax = height[0];
        int rightMax = height[n - 1];
        int trap = 0;
        int left = 1, right = n - 2;
        int cur;
        while (left <= right) {
            if (leftMax < rightMax) {
                cur = left;
                if (leftMax > height[cur]) {
                    trap += leftMax - height[cur];
                } else {
                    leftMax = height[cur];
                }
                left++;
            } else {
                cur = right;
                if (rightMax > height[cur]) {
                    trap += rightMax - height[cur];
                } else {
                    rightMax = height[cur];
                }
                right--;
            }
        }
        return trap;
    }

    public int findMin2(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return nums[i + 1];
            }
        }
        return nums[0];
    }

    //No.289
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int m = board.length;
        int n = board[0].length;
        int[][] bigBoard = new int[m + 2][n + 2];
        for (int x = 1; x < m + 1; x++) {
            for (int y = 1; y < n + 1; y++) {
                bigBoard[x][y] = board[x - 1][y - 1];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = bigBoard[i][j] + bigBoard[i][j + 1] + bigBoard[i][j + 2] + bigBoard[i + 1][j] + bigBoard[i + 1][j + 2] + bigBoard[i + 2][j] + bigBoard[i + 2][j + 1] + bigBoard[i + 2][j + 2];
                if (board[i][j] == 1) {
                    if (lives < 2 || lives > 3) {
                        board[i][j] = 0;
                    }
                } else if (lives == 3){
                    board[i][j] = 1;
                }
            }
        }
    }

    //No.80
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return n;
        }
        int len = 1;
        for (int i = 2; i < n; i++) {
            if (nums[i] != nums[len] || nums[i] != nums[len - 1]) {
                len++;
                nums[len] = nums[i];
            }
        }
        return len + 1;
    }

    //No.26
    public int removeDuplicates1(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int len = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[len]) {
                len++;
                nums[len] = nums[i];
            }
        }
        return len + 1;
    }

    //No.11
    public int maxArea1(int[] height) {
        int max = 0;
        for(int i = 1; i < height.length; i++) {
            for (int j = 0; j < i; j++) {
                max = Math.max(max, Math.min(height[i], height[j]) * (i - j));
            }
        }
        return max;
    }
    //two pointers-the crucial point is to decide which point to move inward
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0, right = n - 1;
        int max = 0;
        while (left < right) {
            max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }

    //No.40
    //All numbers (including target) will be positive integers.
    /**
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        int i = candidates.length - 1;
        for (; i >=0 && candidates[i] > target; i--);
        return combHelper(candidates, i + 1, target);
    }

    private List<List<Integer>> combHelper(int[] candidates, int n, int target) {
        List<List<Integer>> res = new LinkedList<>();
        if (candidates == null || candidates.length == 0 || n <= 0) {
            return res;
        }
        int last = candidates[n - 1];
        int i = n - 2;
        for (; i >= 0 && candidates[i] == last; i--);
        int dup = n  - i - 1;
        int time = target/last;
        if (target%last == 0 && time <= dup) {
            List<Integer> list = new LinkedList<>();
            for (int j = 0; j < time; j++) {
                list.add(last);
            }
            res.add(list);
        }
        for (int k = 0; k <= dup; k++) {
            List<List<Integer>> temp = combHelper(candidates, i + 1, target - last*k);
            for (List lis : temp) {
                if (!lis.isEmpty()) {
                    for (int j = 0; j < k; j++) {
                        lis.add(last);
                    }
                    res.add(lis);
                }
            }
        }
        return res;
    }
  **/
    //Depth First Search
    List<List<Integer>> res = new LinkedList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        LinkedList<Integer> lis = new LinkedList<>(); //"edgeTo", record the visited nodes in the current path
        Arrays.sort(candidates);
        dfs40(candidates, target, 0, lis);
        return res;
    }
    private void dfs40 (int[] candidates, int target, int crt, LinkedList<Integer> lis) {
        if (crt < 0 || crt >= candidates.length) {
            return;
        }
        int node = candidates[crt];
        if (node > target) {
            return;
        }
        if (node == target) {
            lis.add(node);
            LinkedList<Integer> temp = new LinkedList<>(lis);
            res.add(temp);
            lis.removeLast();
            return;
        }
        //candidates[crt] < target
        lis.add(node);       //mark the node(vertex)
        dfs40(candidates, target - node, crt + 1, lis);      //results that contains current node
        lis.removeLast();       //unmark the node
        int i = crt + 1;
        for (; i < candidates.length && candidates[i] == node; i++); //skip the duplicates
        dfs40(candidates, target, i, lis);        //results without current node
    }

    public List<List<Integer>> DFStest(int[] candidates, int target) {
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(0, target, candidates, path, paths);
        return paths;
    }

    private void dfs(int startIndex, int target, int[] nums, List<Integer> path, List<List<Integer>> paths) {
        if (target == 0) {
            paths.add(new ArrayList<>(path));
            return;
        }

        for (int i = startIndex; i < nums.length; i++) {
            if (i != startIndex && nums[i] == nums[i - 1]) continue;
            if (nums[i] > target) break;
            path.add(nums[i]);
            dfs(i + 1, target - nums[i], nums, path, paths);
            path.remove(path.size() - 1);
        }
    }


    //lintcode 547
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return null;
        }
        if (nums1.length > nums2.length) {
            //switch
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        //nums1 is the one with smaller size
        Set<Integer> set = new HashSet<>();
        for (int x : nums1) {
            set.add(x);
        }
        Set<Integer> res = new HashSet<>();
        for (int x : nums2) {
            if (set.contains(x)) {
                res.add(x);
            }
        }
        int[] intersect = new int[res.size()];
        int i = 0;
        for (int x : res) {
            intersect[i] = x;
            i++;
        }

        return intersect;
    }

    //lintcode 41
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        int minPrefix = 0;
        //int p = 1, q = 1;
        int maxSum = nums[0];
        for (int i = 1; i < nums.length + 1; i++) {

            if (prefixSum[i] - minPrefix > maxSum) {
                maxSum = prefixSum[i] - minPrefix;
                //p = minPrefixIndex;
                //q = i;
            }
            if (prefixSum[i] < minPrefix) {
                minPrefix = prefixSum[i];

            }
        }
        return maxSum;
    }

    //lintcode 486
    public int[] mergekSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return null;
        }
        return mergeHelper(arrays, 0, arrays.length - 1);
    }

    private int[] mergeHelper(int[][] arrays, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start == end) {
            return arrays[start];
        }
        if (end - start == 1) {
            return mergeTwoArrays(arrays[start], arrays[end]);
        }
        int mid = start + (end - start) / 2;
        return mergeTwoArrays(mergeHelper(arrays, start, mid), mergeHelper(arrays, mid + 1, end));
    }

    private int[] mergeTwoArrays(int[] a1, int[] a2) {
        if (a1 == null || a1.length == 0) {
            return a2;
        }
        if (a2 == null || a2.length == 0) {
            return a1;
        }
        int p = 0, q = 0, i = 0;
        int len1 = a1.length, len2 = a2.length;
        int[] res = new int[len1 + len2];
        while (p < len1 && q < len2) {
            if (a1[p] < a2[q]) {
                res[i] = a1[p];
                p++;
            } else {
                res[i] = a2[q];
                q++;
            }
            i++;
        }

        while (p < len1) {
            res[i] = a1[p];
            p++;
            i++;
        }

        while(q < len2) {
            res[i] = a2[q];
            q++;
            i++;
        }
        return res;
    }

    //lintcode 931
    public double findMedian1(int[][] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int count = getTotalNum(nums);
        if (count == 0) {
            return 0;
        }
        int[] pointers = new int[nums.length];
        //List<ListNode> lis = numsToListNodes(nums);
        //pq stores the index of the array in nums
        PriorityQueue<Integer> pq = new PriorityQueue<>(nums.length, new Comparator<Integer>(){
            //compare the current pointer nums[i] and nums[j]
            @Override
            public int compare(Integer i, Integer j) {
                return nums[i][pointers[i]] - nums[j][pointers[j]];
            }
        });
        for (int i = 0; i < nums.length; i++) {
            if (nums[i].length != 0) {
                pq.offer(i);
            }
        }

        int temp = 0;
        int med1 = 0;
        for (int i = 0; i < count / 2; i++) {
            temp = pq.poll();
            med1 = nums[temp][pointers[temp]];
            if (pointers[temp] < nums[temp].length - 1) {
                pointers[temp]++;
                pq.offer(temp);
            }

        }

        int temp2 = pq.poll();
        if (count % 2 == 0) {
            //even number of elements
            return ((double)med1)/2 + ((double)(nums[temp2][pointers[temp2]])) / 2;
        } else {
            return (double)nums[temp2][pointers[temp2]];
        }
    }


    private int getTotalNum(int[][] nums) {
        int count = 0;
        for (int[] ar : nums) {
            count += ar.length;
        }
        return count;
    }

    public double findMedian(int[][] nums) {
        //The elements of the given arrays are all positive number.
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int count = getTotalNum(nums);
        if (count == 0) {
            return 0;
        }

        if (count % 2 == 0) {
            //even number
            return ((double) findKth(nums, count / 2)) / 2 + ((double) findKth(nums, 1 + count / 2)) / 2;
        } else {
            //odd number
            return (double)findKth(nums, (count + 1) / 2);
        }
    }

    private int findKth(int[][] nums, int k) {
        int start = 1, end = Integer.MAX_VALUE;
        //find the smallest number than k numbers in nums <= the number
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (smallerThanEqual(nums, mid) >= k) {
                end = mid;
            } else {
                start = mid;
            }

        }

        if (smallerThanEqual(nums, start) == k) {
            return start;
        }
        return end;
    }

    //returns how many numbers in nums are <= x
    private int smallerThanEqual(int[][] nums, int x) {
        int count = 0;
        for (int[] ar : nums) {
            count += smallerThanEqual(ar, x);
        }
        return count;
    }

    //returns how many numbers in nums are <= x
    private int smallerThanEqual(int[] nums, int x) {
        if (nums == null || nums.length == 0 || nums[0] > x) {
            return 0;
        }
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] <= x) {
                start = mid;
            } else {
                end = mid;
            }
        }
        //nums[start] <= x is always true
        if (nums[end] <= x) {
            return end + 1;
        }
        return start + 1;
    }

    //lintcode 603
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> subsets = new ArrayList<>();
        int maxInd = 0;
        for (int i = 0; i < nums.length; i++) {
            List<Integer> current = new ArrayList<>();
            int maxDivIndex = -1;
            for (int j = 0; j < i; j++) {
                //find the largest num that current num is divisible by
                if (nums[i] % nums[j] == 0) {
                    maxDivIndex = j;

                }
            }
            if (maxDivIndex != -1) {

                current.addAll(subsets.get(maxDivIndex));
            }
            current.add(nums[i]);
            subsets.add(current);
            if (current.size() > subsets.get(maxInd).size()) {
                maxInd = i;
            }
        }

        return subsets.get(maxInd);
    }

    //735
    public int[] asteroidCollision(int[] asteroids) {
        //Each asteroid is a non-zero integer
        if (asteroids == null || asteroids.length < 2) {
            return asteroids;
        }
        Stack<Integer> st = new Stack<>();      //store index of positive ints
        int zeroCount = 0;
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i] > 0) {
                st.push(i);
            } else {
                while (!st.isEmpty()) {
                    int j = st.pop();
                    if (asteroids[j] + asteroids[i] < 0) {
                        asteroids[j] = 0;
                        zeroCount++;
                    } else if (asteroids[j] + asteroids[i] == 0) {
                        asteroids[j] = 0;
                        asteroids[i] = 0;
                        zeroCount += 2;
                        break;
                    } else {
                        asteroids[i] = 0;
                        zeroCount++;
                        st.push(j);
                        break;
                    }
                }
            }
        }
        int[] res = new int[asteroids.length - zeroCount];
        int i = 0;
        for (int x : asteroids) {
            if (x != 0) {
                res[i] = x;
                i++;
            }
        }
        return res;
    }

    public int[] asteroidCollision1(int[] asteroids) {
        //Each asteroid is a non-zero integer
        if (asteroids == null || asteroids.length < 2) {
            return asteroids;
        }
        List<Integer> lis = new ArrayList<>();
        for (int x : asteroids) {
            lis.add(x);
        }
        int lastSize = 0;

        while (lis.size() != lastSize) {
            lastSize = lis.size();
            int i = 1;
            while(i > 0 && i < lis.size()) {
                int left = lis.get(i - 1), right = lis.get(i);
                if (left * right >= 0 || (left < 0 && right > 0)) {
                    i++;
                    continue;
                }

                if(left + right < 0) {
                    lis.remove(i - 1);
                } else if (left + right == 0) {
                    lis.remove(i);
                    lis.remove(i - 1);
                    i--;
                } else {
                    lis.remove(i);
                }
            }
        }
        int[] res = new int[lis.size()];
        for (int i = 0; i < lis.size(); i++) {
            res[i] = lis.get(i);
        }
        return res;

    }

    public static void main(String[] args) {
        Array ar = new Array();
        int[] a = {1,1,1,2,2,3};
        //{6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3}
        int[] b = {2,3,4,5};
        //int[] level = ar.waterLevel(ar.localMax(b), b);
        //System.out.println(Arrays.toString(level));

        /*int[] c = {10,1,2,7,6,1,5};
        List<List<Integer>> l = ar.DFStest(b, 8);
        for (List lx : l) {
            for (Object x : lx) {
                System.out.print(x + " ");
            }
            System.out.println("end\n");
        }*/
        //int[][] nums = {{10}, {2,4}, {2,5}};
        int[] temp = ar.asteroidCollision(new int[]{1,-1,-2,-2});
        for (int x : temp) {
            System.out.print(x + " ");
        }

    }

}

