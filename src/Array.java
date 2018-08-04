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


    public static void main(String[] args) {
        Array ar = new Array();
        int[] a = {1,1,1,2,2,3};
        //{6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3}
        int[] b = {6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3};
        //int[] level = ar.waterLevel(ar.localMax(b), b);
        //System.out.println(Arrays.toString(level));
        int len = ar.removeDuplicates(a);
        for (int i = 0; i < len; i++) {
            System.out.println(a[i]);
        }

    }

}

