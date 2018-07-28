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

    public static void main(String[] args) {
        int[] a = {2, 2, 1, 1};
        System.out.println(singleNumber(a));
    }

}

