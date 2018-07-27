import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class Others2 {
    //burst balloons
    //0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
    public int maxCoins(int[] nums) {
        return 0;
    }
    //No.62
    public int uniquePaths(int m, int n) {
        return combination(Math.min(m - 1, n - 1), m + n - 2).intValue();
    }
    private int factorial (int n) {
        if (n < 0) {
            throw new ArithmeticException();
        }
        if (n < 2) {
            return 1;
        }
        return n*factorial(n - 1);
    }
    private static BigInteger combination (int x, int y) {
        if (x < 0 || y < 0 || x > y) {
            throw new ArithmeticException();
        }
        if (y == 0) {
            return new BigInteger("1");
        }
        BigInteger up = new BigInteger("1");
        for (int i = y; i > y - x; i--) {
            up = up.multiply(BigInteger.valueOf(i));
        }
        BigInteger comb = up;
        for (int j = 1; j < x + 1; j++) {
            comb = comb.divide(BigInteger.valueOf(j));
        }
        return comb;
    }
    //No.89
    public List<Integer> grayCode1(int n) {     //wrong
        List<Integer> l = new LinkedList<>();
        if (n == 0) {
            l.add(0);
            return l;
        }
        if (n == 1) {
            l.add(0);
            l.add(1);
            return l;
        }
        List<Integer> prev = grayCode1(n - 1);
        for (int x : prev) {
            l.add(x*2);
            l.add(x*2 + 1);
        }
        return l;
    }

    public List<Integer> grayCode(int n) {
        LinkedList<Integer> l = new LinkedList<>();
        if (n == 0) {
            l.add(0);
            return l;
        }
        l = (LinkedList) grayCode(n - 1);
        int size = l.size();
        int baseNum = (int)Math.pow(2, n - 1);
        for (int i = size - 1; i >= 0; i--) {
            l.add(baseNum + l.get(i));
        }
        return l;
    }


    //No.96
    public int numTrees1(int n) {
        if (n == 0) {
            return 1;
        }
        if (n < 3) {
            return n;
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += numTrees1(i) * numTrees1(n - 1 - i);
        }
        return sum;
    }

    public int numTrees(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[] num = new int[n + 1];
        num[0] = num[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            num[i] = 0;
            for (int j = 0; j < i; j++) {
                num[i] += num[j] * num[i - 1 - j];
            }
        }
        return num[n];
    }

    public int findDuplicate1(int[] nums) {
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        int n = nums.length - 1;
        return sum - n*(n + 1)/2;
    }

    //No.39
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<List<Integer>> comb = new ArrayList<>();
        if (len == 0) {
            return comb;
        }
        List<Integer> l = new ArrayList<>();
        if (len == 1) {
            if (target%candidates[0] == 0) {
                for (int i = 0; i < target/candidates[0]; i++) {
                    l.add(candidates[0]);
                }
                comb.add(l);
                return comb;
            } else {
                return comb;
            }
        }
        for (int i = 0; i < target/candidates[len - 1] + 1; i++) {
            for (List list : combinationSum(Arrays.copyOfRange(candidates, 0, len - 1), target - i*candidates[len - 1])){
                for(int j = 0; j < i; j++) {
                    list.add(candidates[len - 1]);
                }
                comb.add(list);
            }
        }
        return comb;
    }

    //No.77
    public List<List<Integer>> combine(int n, int k) {
        List<Integer> l = new LinkedList<>();
        List<List<Integer>> comb = new LinkedList<>();
        if (k == 0 || k > n) {
            comb.add(l);
            return comb;
        }
        if (n == k) {
            for (int i = 1; i < n + 1; i++) {
                l.add(i);
            }
            comb.add(l);
            return comb;
        }
        for (List list : combine(n - 1, k - 1)) {
            list.add(n);
            comb.add(list);
        }
        comb.addAll(combine(n - 1, k));
        return comb;
    }

    //No.202
    public boolean isHappy(int n) {
        int num = n;
        Set<Integer> s = new HashSet<>();
        while (true) {
            int sum = 0;
            while (num/10 > 0) {
                sum += (num%10)*(num%10);
                num = num/10;
            }
            sum += (num%10)*(num%10);
            if (sum == 1) {
                return true;
            }
            if (s.contains(sum)) {
                return false;
            }
            s.add(sum);
            num = sum;
        }
    }

    //No.70 actually a fibbonaci sequence
    public int climbStairs(int n) {
        if (n < 4) {
            return n;
        }
        int[] fib = new int[n];
        fib[0] = 1;
        fib[1] = 2;
        for (int i = 2; i < n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n - 1];
    }

    //No.118
    public List<List<Integer>> generate(int numRows) {
        List<Integer> l0 = new LinkedList<>();
        List<List<Integer>> list = new LinkedList<>();
        if (numRows == 0) {
            return list;
        }
        l0.add(1);
        list.add(l0);
        for (int i = 1; i < numRows; i++) {
            List<Integer> l = new LinkedList<>();
            l.add(1);
            List<Integer> pre = list.get(i - 1);
            for (int j = 1; j < i; j++) {
                l.add(pre.get(j - 1) + pre.get(j));
            }
            l.add(1);
            list.add(l);
        }
        return list;
    }

    //No.231
    public boolean isPowerOfTwo1(int n) {
        if (n < 1) {
            return false;
        }
        String binary = Integer.toBinaryString(n);
        char[] digits = binary.toCharArray();
        int i = 0;
        for (char x : digits) {
            if (x == '1') {
                i++;
            }
        }
        return i == 1;
    }

    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        return  (n & (n - 1)) == 0;
    }
    //49
    public List<List<String>> groupAnagrams1(String[] strs) {
        Map<char[], List<String>> map = new HashMap<>();
        List<List<String>> l = new LinkedList<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            boolean b = false;
            for (char[] c : map.keySet()) {
                if (Arrays.equals(c, chars)) {
                    map.get(c).add(s);
                    b = true;
                    continue;
                }
            }
            if (!b) {
                map.put(chars, new LinkedList<>());
                map.get(chars).add(s);
            }

        }
        l.addAll(map.values());
        return l;
    }       //too slow

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        List<List<String>> l = new LinkedList<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.putIfAbsent(key, new LinkedList<>());
            map.get(key).add(s);
        }
        l.addAll(map.values());
        return l;
    }

    //No.53
    public int maxSubArray(int[] nums) {
        int[] maxSum = new int[nums.length];
        maxSum[0] = nums[0];
        int max = maxSum[0];
        for (int i = 1; i < nums.length; i++) {
            maxSum[i] = Math.max(maxSum[i - 1] + nums[i], nums[i]);
            max = Math.max(max, maxSum[i]);
        }
        return max;
    }

    //No.66
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        int plus = 1;
        for (int i = n - 1; i >= 0; i--) {
            int temp = digits[i] + plus;
            digits[i] = temp % 10;
            plus = temp/10;
        }
        if (digits[0] == 0 && plus == 1) {
            int[] plusOne = new int[n + 1];
            plusOne[0] = 1;
            System.arraycopy(digits, 0, plusOne, 1, n);
            return plusOne;
        }
        return digits;
    }





        public static void main(String[] args) {
        //System.out.println(combination(2, 8));
        int[] candidates = {2, 3, 7, 1};
        List<List<Integer>> l1 = combinationSum(candidates, 7);
        for (List l : l1) {
            for (Object x : l) {
                System.out.print(x + " ");
            }
            System.out.println("\n");
        }
    }
}
