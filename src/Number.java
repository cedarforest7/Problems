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

}
