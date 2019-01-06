import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bitwise {
    //No.191
    public static int hammingWeight(int n) {
        String binary = Integer.toBinaryString(n);
        int weight = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                weight++;
            }
        }
        return weight;
    }

    //lintcode 236
    public int swapOddEvenBits(int x) {
        int num = 1;
        int odd = 0, even = 0;
        for (int i = 0; i < 32; i++) {

            if (i % 2 == 0) {
                even += x & num;
            } else {
                odd += x & num;
            }
            num = num << 1;
        }

        return (odd >>> 1) + (even << 1);
    }

    //lintcode 1
    public int aplusb(int a, int b) {
        return (a ^ b) + ((a & b) << 1);
    }

    //lintcode 179
    public int updateBits(int n, int m, int i, int j) {
        int num = ~1;
        for (int k = 0; k < i; k++) {
            num = (num << 1) + 1;
        }
        //turn the bits between i, j to 0
        for (int k = i; k <= j; k++) {
            n = n & num;
            num = (num << 1) + 1;
        }

        return n | (m << i);
    }

    //lintcode 365
    public int countOnes(int num) {
        int x = 1, count = 0;
        for (int i = 0; i < 32; i++) {
            count += (x & num) != 0 ? 1 : 0;
            x = x << 1;
        }
        return count;
    }

    public int bitSwapRequired(int a, int b) {
        return countOnes1(a ^ b);
    }
    private int countOnes1(int num) {
        int count = 0;
        while (num != 0) {
            num = num & (num - 1);
            count++;
        }
        return count;
    }

    //lintcode 142
    public boolean checkPowerOf2(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    //lintcode 17
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null) {
            return res;
        }
        int n = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    temp.add(nums[j]);
                }
            }
            res.add(temp);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(hammingWeight(11));
    }
}
