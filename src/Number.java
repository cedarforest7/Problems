import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Number {
    //lintcode 513
    public int numSquares(int n) {
        int[] sq = new int[n + 1];
        //build the array from 1
        for (int i = 1; i * i <= n; i++) {
            sq[i * i] = 1;
        }
        if (sq[n] != 0) {
            return sq[n];
        }
        for (int i = 1; i <= n; i++) {
            if (sq[i] == 1) {
                continue;
            }
            sq[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j < i; j++) {
                sq[i] = Math.min(sq[i], sq[j * j] + sq[ i - j * j]);
            }

        }
        return sq[n];
    }

    //lintcode 116
    public boolean canJump(int[] A) {
        if (A == null || A.length == 0) {
            return false;
        }
        int max = A[0];
        int i = 0;
        for (; i <= max; i++) {
            if (i + A[i] >= A.length - 1) {
                return true;
            }
            //System.out.println(i);
            max = Math.max(max, i + A[i]);
            if (max >= A.length - 1 ) {
                return true;
            }
        }

        return i >= A.length;
    }

    //lintcode 76
    public int longestIncreasingSubsequence(int[] nums) {
        //DP
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] lis = new int[n];
        lis[0] = nums[0];
        int len = 1;
        for (int i = 1; i < n; i++) {
            //compare with the tail of current LIS
            if (lis[len - 1] < nums[i]) {
                lis[len] = nums[i];
                len++;
            } else {
                //tail >= nums[i]
                //find the first value in LIS that is larger than or equals nums[i], and replace it
                int index = firstGTE(lis, len, nums[i]);
                lis[index] = nums[i];
            }
        }
        return len;
    }

    private int firstGTE(int[] lis, int len, int num) {
        //binary search
        int start = 0, end = len - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (lis[mid] >= num) {
                //move to left
                end = mid;
            } else {
                start = mid;
            }
        }
        if (lis[start] >= num) {
            return start;
        }
        return end;
    }

    //No.904, 3 pointers
    public int totalFruit(int[] tree) {
        if (tree == null) {
            return 0;
        }

        int n = tree.length;
        if (n < 3) {
            return n;
        }
        int p1 = 0, p2 = 0;
        if (tree[1] != tree[0]) {
            p2 = 1;
        }
        int[] nums = {tree[0], tree[1]};

        int maxLen = 2;
        for (int i = 2; i < n; i++) {
            if (tree[i] == nums[0] || tree[i] == nums[1]) {
                if (tree[i] != tree[i - 1]) {
                    p2 = i;
                    nums[0] = nums[1];
                    nums[1] = tree[i];
                }

            } else {
                p1 = p2;
                p2 = i;
                nums[0] = nums[1];
                nums[1] = tree[i];
            }
            maxLen = Math.max(maxLen, i - p1 + 1);
            //System.out.println(p1 + ", " + p2 + "[" + nums[0] + "," + nums[1] + "]");
        }
        return maxLen;
    }

    //No.322
    public int coinChange(int[] coins, int amount) {

        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        //Arrays.sort(coins);
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int val : coins) {
            for (int i = val; i <= amount; i++) {
                if (dp[i - val] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - val]);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        Number nb = new Number();
        int[] coins = {1, 9, 4};
        System.out.println(nb.coinChange(coins, 12));
    }
}
