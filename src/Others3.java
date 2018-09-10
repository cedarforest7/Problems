import java.math.BigInteger;
import java.util.*;

public class Others3 {
    //No.263
    public boolean isUgly(int num) {
        if (num <= 0) {
            return false;
        }
        while (num % 2 == 0) {
            num = num / 2;
        }
        while (num % 3 == 0) {
            num = num / 3;
        }
        while (num % 5 == 0) {
            num = num / 5;
        }
        return num == 1;
    }

    //No.119
    public List<Integer> getRow1(int rowIndex) {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        if (rowIndex == 0) {
            return list;
        }
        List<Integer> pre = getRow1(rowIndex - 1);
        for (int i = 1; i < rowIndex; i++) {
            list.add(pre.get(i - 1) + pre.get(i));
        }
        list.add(1);
        return list;
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        if (rowIndex == 0) {
            return list;
        }
        long temp = rowIndex;
        for (int i = 1; i < rowIndex; i++) {
            list.add(Math.toIntExact(temp));
            temp = temp * (rowIndex - i) / (i + 1);
        }
        list.add(1);
        return list;
    }

    //No.36
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            Set<Character> s = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                int x = board[i][j] - '0';
                if (x > 0 && x <= 9) {
                    if (s.contains(board[i][j])) {
                        return false;
                    }
                    s.add(board[i][j]);
                }
            }
        }
        for (int j = 0; j < 9; j++) {
            Set<Character> s = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                int x = board[i][j] - '0';
                if (x > 0 && x <= 9) {
                    if (s.contains(board[i][j])) {
                        return false;
                    }
                    s.add(board[i][j]);
                }
            }
        }
        for (int m = 0; m < 9; m += 3) {
            for (int n = 0; n < 9; n += 3) {
                Set<Character> s = new HashSet<>();
                for (int i = m; i < m + 3; i++) {
                    for (int j = n; j < n + 3; j++) {
                        int x = board[i][j] - '0';
                        if (x > 0 && x <= 9) {
                            if (s.contains(board[i][j])) {
                                return false;
                            }
                            s.add(board[i][j]);
                        }
                    }
                }
            }
        }
        return true;
    }

    //No.279
    public static int numSquares1(int n) {
        int sqrt = (int) Math.sqrt(n);
        if (n == sqrt*sqrt) {
            return 1;
        }

        int min = n;
        for (int i = sqrt; i > 0; i--) {
            min = Math.min(min, 1 + numSquares1(n - i*i));
            if (min == 2) {
                return 2;
            }
        }
        return min;
    }
    //above solution timeout

    static Map<Integer, Integer> sqMap = new HashMap<>();
    public int numSquares(int n) {
        int sqrt = (int) Math.sqrt(n);
        if (n == sqrt*sqrt) {
            sqMap.put(n, 1);
            return 1;
        }
        if (sqMap.get(n) != null) {
            return sqMap.get(n);
        }
        int min = n;
        for (int i = sqrt; i > 0; i--) {
            min = Math.min(min, 1 + numSquares(n - i*i));
            if (min == 2) {
                return 2;
            }
        }
        sqMap.putIfAbsent(n, min);
        return min;
    }

    //No.9
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String num = String.valueOf(x);
        int len = num.length();
        for (int i = 0; i < len/2; i++) {
            if (num.charAt(i) != num.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome1(int x) {
        if (x < 0) {
            return false;
        }
        int ori = x;
        int rev = 0;
        while (x != 0) {
            rev = rev*10 + x%10;
            x = x/10;
        }
        return ori == rev;
    }

    //No.38
    static Map<Integer, String> countSay = new HashMap<>();
    public String countAndSay1(int n) {
        if (n == 1) {
            return "1";
        }
        String pre;
        if (countSay.containsKey(n - 1)) {
            pre = countSay.get(n - 1);
        } else {
            pre = countAndSay(n - 1);
        }
        String res = "";
        char scan;
        for (int i = 0; i < pre.length();) {
            scan = pre.charAt(i);
            int count = 0;
            while (i < pre.length() && pre.charAt(i) == scan) {
                count++;
                i++;
            }
            res += String.valueOf(count) + String.valueOf(scan);
        }
        countSay.putIfAbsent(n, res);
        return res;
    }

    //Using StringBuilder is faster
    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }
        String pre;
        if (countSay.containsKey(n - 1)) {
            pre = countSay.get(n - 1);
        } else {
            pre = countAndSay(n - 1);
        }
        StringBuilder sb = new StringBuilder("");
        char scan;
        for (int i = 0; i < pre.length();) {
            scan = pre.charAt(i);
            int count = 0;
            while (i < pre.length() && pre.charAt(i) == scan) {
                count++;
                i++;
            }
            sb.append(count);
            sb.append(scan);
        }
        String res = sb.toString();
        countSay.putIfAbsent(n, res);
        return res;
    }

    //No.17  actually level order DFS(fastest)(not the solution below)
    static String[][] dial = {{" "}, {"*"}, {"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}, {"j", "k", "l"}, {"m", "n", "o"},
            {"p", "q", "r", "s"}, {"t", "u", "v"}, {"w", "x", "y", "z"}};
    //digits from 2-9 inclusive
    public List<String> letterCombinations(String digits) {
        return letterCombHelper(digits, digits.length() - 1);
    }

    private List<String> letterCombHelper (String digits, int n) {
        List<String> list = new LinkedList<>();
        if (n == -1) {
            return list;
        }
        List<String> pre = letterCombHelper(digits, n - 1);
        if (n == 0) {
            pre.add("");
        }
        int ind = digits.charAt(n) - '0';
        for (String s : pre) {
            for (int i = 0; i < dial[ind].length; i++) {
                //StringBuilder sb = new StringBuilder(s);
                //list.add(sb.append(dial[ind][i]).toString());
                list.add(s + dial[ind][i]);
            }
        }
        return list;
    }

    //No.172
    public int trailingZeroes(int n) {
        int count = 0;
        while (n/5 != 0) {
            n = n/5;
            count += n;
        }
        return count;
    }

    //No.131
    List<List<String>> part = new LinkedList<>();
    public List<List<String>> partition(String s) {
        LinkedList<String> lis = new LinkedList<>();
        dfs131(s, 1, lis);
        return part;
    }

    private void dfs131 (String s, int end, LinkedList<String> lis) {
        if (s.length() == 0 || end > s.length()) {
            List<String> l = new LinkedList<>(lis);
            part.add(l);
            return;
        }
        int n = s.length();

        for (int i = end; i <= n; i++) {
            String temp = s.substring(0, i);        //i is exclusive
            if(isPal(temp)) {
                lis.add(temp);
                dfs131(s.substring(i), 1, lis);
                lis.removeLast();
            }
        }
    }

    private boolean isPal (String str) {
        int len = str.length();
        for (int i = 0; i < len/2; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }
    //No.43
    //below is wrong; int overflow
    public String multiply1(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        int product = 0;
        for (int i = 0; i < len1; i++) {
            int a = num1.charAt(len1 - i - 1) - '0';
            for (int j = 0; j < num2.length(); j++) {
                int b = num2.charAt(len2 - j - 1) - '0';
                product += a*Math.pow(10, i)*b*Math.pow(10, j);
            }
        }
        return String.valueOf(product);
    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int len1 = num1.length();
        int len2 = num2.length();
        Map<Integer, Integer> prod = new HashMap<>();
        int pre = 0;
        for (int i = 0; i < len1; i++) {
            int a = num1.charAt(len1 - i - 1) - '0';
            for (int j = 0; j < len2; j++) {
                int b = num2.charAt(len2 - j - 1) - '0';
                if (prod.containsKey(i + j)) {
                    prod.put(i + j, prod.get(i + j) + a*b);
                } else {
                    prod.put(i + j, a*b);
                }
            }
        }
        StringBuilder res = new StringBuilder();
        int k = 0;
        for (; k < len1 + len2 - 1; k++) {
            int x = prod.get(k);
            if (x > 9) {
                prod.put(k, x%10);
                if (prod.containsKey(k + 1)) {
                    prod.put(k + 1, prod.get(k + 1) + x/10);
                } else {
                    prod.put(k + 1, x/10);
                }
                x = x%10;
            }
            res.insert(0, x);
        }
        if (prod.containsKey(k)) {
            res.insert(0, prod.get(k));
        }
//        //delete leading zeros
//        for (int i = 0; i < res.length() && res.charAt(i) == '0'; i++);
        return res.toString();
    }

    //No.120
    public int minimumTotal(List<List<Integer>> triangle) {
        for (int i = 1; i < triangle.size(); i++) {
            List<Integer> pre = triangle.get(i - 1);
            List<Integer> lis = triangle.get(i);
            lis.set(0, pre.get(0) + lis.get(0));
            for (int j = 1; j < i; j++) {
                lis.set(j, lis.get(j) + Math.min(pre.get(j - 1), pre.get(j)));
            }
            lis.set(i, pre.get(i - 1) + lis.get(i));
        }
        int res = Integer.MAX_VALUE;
        List<Integer> last = triangle.get(triangle.size() - 1);
        for (int i = 0; i < triangle.size(); i++) {
            res = Math.min(res, last.get(i));
        }
        return res;
    }

    //No.686
    //assume A is non-empty
    public int repeatedStringMatch1(String A, String B) {       //time limit exceeded
        if (B.equals("")) {
            return 0;
        }
        if (isSub(B, A)) {
            return 1;
        }
        if (isSub(B, A + A)) {
            return 2;
        }
        if (!isSub(A, B)) {
            return -1;
        }
        int i = 1;
        String A1 = A;
        for (; !isSub(B, A1); i++) {
            A1 += A;
        }
        return i;
    }

    public int repeatedStringMatch(String A, String B) {
        int lenA = A.length();
        int lenB = B.length();
        int n = lenB/lenA + 1;
        StringBuilder X = new StringBuilder();
        for (int i = 0; i < n - 1; i++) {
            X.append(A);
        }
        String XS = X.toString();
        if (!isSub(B, XS + A + A)) {
            return -1;
        }
        if (XS.equals(B)) {
            return n - 1;
        } else if (isSub(B, XS + A)) {
            return n;
        } else {
            return n + 1;
        }
    }


    private boolean isSub (String s1, String s2) {      //if s1 is substring of s2
        if (s1.equals("")) {
            return true;
        }
        char start = s1.charAt(0);
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            if (s2.charAt(i) != start) {
                continue;
            }
            if (s2.substring(i, i + s1.length()).equals(s1)) {
                return true;
            }
        }
        return false;
    }

    //No.3
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        return lengthOfLongestSubstring(s, s.length() - 1)[0];
    }

    private int[] lengthOfLongestSubstring(String s, int n) {
        int[] res = new int[2];
        if (n == 0) {
            res[0] = 1;      //max length
            res[1] = 0;     //starting position of the non-repeating longest substring ending with the nth char
            return res;
        }
        int[] pre = lengthOfLongestSubstring(s, n - 1);
        int i = n;
        for (; i > pre[1] && s.charAt(i - 1) != s.charAt(n); i--);
        res[1] = i;
        res[0] = Math.max(pre[0], n - i + 1);
        return res;
    }

    //No.7
    public int reverse(int x) {
        int res = 0;
        while(x != 0) {
            if(res > Integer.MAX_VALUE/10 || res < Integer.MIN_VALUE/10) {
                return 0;
            }
            res = res*10 + x%10;
            x /= 10;
        }
        return res;
    }
    //slow
    public int reverse1(int x) {
        boolean pos = true;
        if (x < 0) {
            pos = false;
            x = 0 - x;
        }
        StringBuilder sb = new StringBuilder().append(x);
        sb.reverse();
        int i = 0;
        for (; i < sb.length() && sb.charAt(i) == '0'; i++);
        int res = 0;
        for (; i < sb.length(); i++) {
            if(res > 214748364 || (res == 214748364 && sb.charAt(i) - '0' > 7)) {
                return 0;
            }
            res = res*10 + sb.charAt(i) - '0';
        }
        if (!pos) {
            res = 0 - res;
        }
        return res;
    }

    //No.5
    //below is too slow
    public String longestPalindrome1(String s) {
        String res = "";
        int maxL = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + maxL; j < s.length(); j++) {
                String temp = s.substring(i, j + 1);
                if (isPal(temp) && j - i + 1 > maxL) {
                    res = temp;
                    maxL = j - i + 1;
                }
            }
        }
        return res;
    }

    int startPal = 0;
    int maxL = 0;
    public String longestPalindrome(String s) {
        for (int i = 0; i < s.length() - maxL/2; i++) {
            longestPal(s, i);
        }
        return s.substring(startPal, startPal + maxL);
    }

    private void longestPal (String s, int i) {
        int l = i, r = i;
        for (;r < s.length() - 1 && s.charAt(r) == s.charAt(r + 1); r++);
        for (;l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r); l--, r++);
        if (r - l - 1 > maxL) {
            maxL = r - l - 1;
            startPal = l + 1;
            //longPal = s.substring(l + 1, r);
        }
    }

//    private boolean isPal (String str, int st, int end) {
//        int len = end - st + 1;
//        for (int i = st; i < len/2; i++) {
//            if (str.charAt(i) != str.charAt(len - i - 1)) {
//                return false;
//            }
//        }
//        return true;
//    }


    //No.14
    public String longestCommonPrefix1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String res = strs[0];
        for (int i = 1; i < strs.length; i++) {
            for (int j = 0; j <= strs[i].length(); j++) {
                if (j == strs[i].length() || j > res.length() - 1 || res.charAt(j) != strs[i].charAt(j)) {
                    res = res.substring(0, j);
                    break;
                }
            }
        }
        return res;
    }

    //faster sol
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String res = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(res) != 0) {
                res = res.substring(0, res.length() - 1);
                //remove the last char of res
            }
        }
        return res;
    }

    //No.564
    //n is positive
    public String nearestPalindromic(String n) {
        if (n == null || n.length() == 0) {
            return n;
        }
        if (n.length() == 1) {
            return String.valueOf(Integer.parseInt(n) - 1);
        }
        //char[] chars = n.toCharArray();
        int len = n.length();
        //char[] res = new char[len];
        //int num = Integer.parseInt(n);
        BigInteger num = new BigInteger(n);
        BigInteger near = num;
        for (int i = 0; i < len/2; i++) {
            //near /= 10;
            near = near.divide(BigInteger.TEN);
        }
        BigInteger low = createPal(near.subtract(BigInteger.ONE), len);
        //int lowdif = num - low;
        BigInteger lowdif = num.subtract(low);
        BigInteger mid = createPal(near, len);
        //int middif = Math.abs(mid - num);
        BigInteger middif = mid.subtract(num).abs();
        BigInteger high = createPal(near.add(BigInteger.ONE), len);
        //int highdif = high - num;
        BigInteger highdif = high.subtract(num);
        if (middif.compareTo(BigInteger.ZERO) == 0) {
            return lowdif.compareTo(highdif) <= 0? String.valueOf(low) : String.valueOf(high);
        }
        if (lowdif.compareTo(highdif) <= 0) {
            return middif.compareTo(lowdif) < 0 ? String.valueOf(mid) : String.valueOf(low);
        } else {
            return middif.compareTo(highdif) <= 0 ? String.valueOf(mid) : String.valueOf(high);
        }
        //int close = Integer.MAX_VALUE;
 //       int i = 0;
//        if (chars[len - 1] == '0') {
//            res[0] = chars[0];
//            res[len - 1] = chars[0];
//            i++;
//        }
//        for (; i <= len/2; i++) {
//            char temp;
//            if (chars[i] <= chars[len - 1 - i]) {
//                temp = chars[i];
//            } else {
//                temp = chars[len - 1 - i];
//            }
//            res[i] = temp;
//            res[len - 1 - i] = temp;
//        }
//        return String.valueOf(res);
    }

    private BigInteger createPal (BigInteger num, int len) {
        BigInteger res = num;
        if (len%2 != 0) {
            //num /= 10;
            num = num.divide(BigInteger.TEN);
        }
        for (int i = 0; i < len/2; i++) {
//            res = res*10 + num%10;
//            num /= 10;
            res = res.multiply(BigInteger.TEN).add(num.mod(BigInteger.TEN));
            num = num.divide(BigInteger.TEN);
        }
        //res += num%10;
        res = res.add(num.mod(BigInteger.TEN));
        if (res.mod(BigInteger.TEN).compareTo(BigInteger.ZERO) == 0) {
            //res += 9;
            res = res.add(BigInteger.TEN.subtract(BigInteger.ONE));
        }
        return res;
    }
    //No.599
    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> fav = new HashSet<>();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }
        for (int j = 0; j < list2.length; j++) {
            if (j > min) {
                break;
            }
            String temp = list2[j];
            if (map.containsKey(temp)) {
                int sum = map.get(temp) + j;
                if (sum == min) {
                    fav.add(temp);
                } else if (sum < min) {
                    fav.clear();
                    fav.add(temp);
                    min = sum;
                }
            }
        }
        return fav.toArray(new String[fav.size()]);
    }

    //No.681
    List<Integer> res681 = new ArrayList<>();
    boolean stop = false;
    public String nextClosestTime(String time) {
        int[] clock = new int[4];
        Set<Integer> digit = new HashSet<>();
        clock[0] = time.charAt(0) - '0';
        clock[1] = time.charAt(1) - '0';
        clock[2] = time.charAt(3) - '0';
        clock[3] = time.charAt(4) - '0';
        for (int x : clock) {
            digit.add(x);
        }
        int[] digits = new int[digit.size()];
        int i = 0;
        for (int x: digit) {
            digits[i] = x;
            i++;
        }
        Arrays.sort(digits);
        //int[] res = new int[4];
        List<Integer> res = new ArrayList<>();
        dfs681(clock, digits, true);
        String s = String.valueOf(res681.get(0)) + String.valueOf(res681.get(1)) + ":" + String.valueOf(res681.get(2)) + String.valueOf(res681.get(3));

        for (int x : res681) {
            System.out.print(x + " ");
        }
        return s;
    }

    private void dfs681(int[] clock, int[] digits, boolean same) {
        if (res681.size() == 4) {
            stop = true;
            return;
        }
        int cur = clock[res681.size()];
        if (same) {
            for (int i = 0; i < digits.length; i++) {
                int x = digits[i];
                switch(res681.size()) {
                    case 0: if (x > 2) {
                        dfs681(clock, digits, false);
                        return;
                    }
                            break;
                    case 1: if(res681.get(0) == 2 && x > 3) return;
                            break;
                    case 2: if (x > 5) return;
                            break;
                }
                if (x >= cur) {
                    if (x > cur) {
                        same = false;
                    } else if (res681.size() == 3) {
                        continue;
                    }
                    res681.add(x);
                    dfs681(clock, digits, same);
                    if (stop) {
                        return;
                    }
                    res681.remove(res681.size() - 1);
                }
            }
            if (res681.size() != 0) {
                return;
            }
        }
        while (res681.size() < 4) {
            res681.add(digits[0]);
        }
        stop = true;
    }

    public static void main(String[] args) {
        Others3 o = new Others3();
        //System.out.println(o.numSquares(118));
//        for (int i = 1; i < 20; i++) {
//            System.out.println(o.countAndSay(i));
//        }
//        List<String> l = o.letterCombinations("");
//        for (String s : l) {
//            System.out.println(s);
//        }
//        List<List<String>> l = o.partition("aab");
//        for (List lx : l) {
//            for (Object x : lx) {
//                System.out.print(x + " ");
//            }
//            System.out.println("end\n");
//        }
        //System.out.println(o.lengthOfLongestSubstring( "abbacaaa"));
//        String[] strs = {"flip", "flipped", "float"};
//        System.out.print(o.longestCommonPrefix(strs));
        //System.out.print(o.createPal(100, 4));
        //System.out.print(o.nearestPalindromic("12300"));

        //String[] list1 = {"a", "b", "c", "d"};
        //String[] list2 = {"b","a", "x", "y"};
        System.out.print(o.nextClosestTime("19:34"));
    }



}