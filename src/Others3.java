import javax.security.auth.callback.CallbackHandler;
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

    //No.482
    public String licenseKeyFormatting(String S, int K) {
        String s = S.replaceAll("[-]", "");
        int len = s.length();
        if (len < K) {
            return s.toUpperCase();
        }
        int first = len%K;
        int cut = len/K - 1;
        StringBuilder sb = new StringBuilder(s);
        if (first != 0) {
            sb.insert(first, '-');
            cut++;
            first++;
        }
        for (int i = first + K; i < len + cut; i += K + 1) {
            sb.insert(i, '-');
        }
        return sb.toString().toUpperCase();
    }

    //No.722
    //code has bug and not neat
//    public List<String> removeComments(String[] source) {
//        List<String> res = new ArrayList<>();
//        if (source == null || source.length == 0) {
//            return res;
//        }
//        boolean blockLast = false;      //if the block starts before this line begins
//        boolean blockEnd = true;
//        int st;
//        for (int i = 0; i < source.length; i++) {
//            if (blockLast && !blockEnd) {
//                st = 0;
//            } else {
//                st = source[i].indexOf("/*");
//            }
//            int slash = source[i].indexOf("//");
//            //find which one is invalid
//            if (!blockLast && slash >= 0 && (slash < st || st < 0)) {
//                //block is invalid, slash is valid
//                source[i] = source[i].substring(0, slash);
//
//            } else if (st >= 0){
//                //slash is invalid, block is valid
//                //source[i] = source[i].substring(0, st);
//                int end = source[i].indexOf("*/");
//                for (; end < source[i].length() && end > 0 && source[i].charAt(end - 1) == '/'; end = source[i].indexOf("*/", end + 1));
//                if (end < 0) {
//                    //no closing */
//                    source[i] = source[i].substring(0, st);
//                    blockLast = true;
//                    blockEnd = false;
//                    if (source[i].length() != 0) {
//                        res.add(source[i]);
//                    }
//                    continue;
//                }else if (end + 2 < source[i].length()) {
//                    source[i] = source[i].substring(0, st) + source[i].substring(end + 2);
//                    i--;
//                    blockEnd = true;
//                    continue;
//                } else {
//                    // */ is at the end of string
//                    source[i] = source[i].substring(0, st);
//                    blockEnd = true;
//                }
//
//            }
//            if (blockLast) {
//                //append the string to the last element of res
//                int last = res.size() - 1;
//                res.set(last, res.get(last) + source[i]);
//                blockLast = false;
//            } else {
//                if (source[i].length() != 0) {
//                    res.add(source[i]);
//                }
//            }
//        }
//        return res;
//    }
    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<>();
        if (source == null || source.length == 0) {
            return res;
        }
        //whether the block or slash mode has started
        boolean block = false;
        boolean slash = false;
        StringBuilder sb = new StringBuilder();
        for (String s : source) {
            for (int i = 0; i < s.length(); i++) {
                if (block) {
                    //ignore all chars until there is a */
                    if (i < s.length() - 1 && s.charAt(i) == '*' && s.charAt(i + 1) == '/') {
                        block = false;
                        i++;
                    }
                } else if (!slash) {
                    if (i < s.length() - 1 && s.charAt(i) == '/') {
                        if (s.charAt(i + 1) == '*') {
                            block = true;
                            i++;
                        } else if (s.charAt(i + 1) == '/') {
                            slash = true;
                            i++;
                        } else {
                            sb.append(s.charAt(i));
                        }
                    } else {
                        sb.append(s.charAt(i));
                    }
                }
            }
            //at the end of each line invalidate slash
            slash = false;
            if (!block && sb.length() != 0) {
                res.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        return res;
    }

    //722 transformation (Goldman Sachs)
    public String removeComments2(List<String> input) {
        if (input == null || input.size() == 0) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        //whether the block or slash mode has started
        boolean block = false;
        boolean slash = false;
        boolean newline = false;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        int newlines = 0;
        for (String s : input) {
            //to be modified below
            for (int i = 0; i < s.length(); i++) {
                if (block) {
                    //keep all chars in temp until there is a */
                    if (i < s.length() - 1 && s.charAt(i) == '*' && s.charAt(i + 1) == '/') {
                        block = false;
                        slash = false;
                        i++;
                        newline = (i == 0);
                    } else if (!slash){
                        if (i < s.length() - 1 && s.charAt(i) == '/') {
                            if (s.charAt(i + 1) == '/') {
                                slash = true;
                                i++;
                            } else {
                                sb2.append(s.charAt(i));
                            }
                        } else {
                            sb2.append(s.charAt(i));
                        }
                    }
                } else if (!slash) {
                    if (i < s.length() - 1 && s.charAt(i) == '/') {
                        if (s.charAt(i + 1) == '*') {
                            block = true;
                            i++;
                        } else if (s.charAt(i + 1) == '/') {
                            slash = true;
                            i++;
                        } else {
                            sb.append(s.charAt(i));
                        }
                    } else {
                        sb.append(s.charAt(i));
                    }
                }
            }
            //at the end of each line invalidate slash
            slash = false;
            if (block) {
                if(sb2.length() == 0) {
                    temp.append(" \n");
                    newlines++;
                } else {
                    temp.append(sb2);
                    temp.append("\n");
                    newlines++;
                }
                sb2 = new StringBuilder();
            }
            if (!block || (block && sb.length() != 0)) {
                if(sb.length() == 0) {
                    res.append(" ");
                } else {
                    res.append(sb);
                }
                sb = new StringBuilder();
            }
            res.append("\n");
        }
        //if the block is not closed at the end
        if (block) {
            if (!newline) {
                newlines--;
            }
            res.replace(res.length() - 1 - newlines, res.length(), "/*");
            return res.toString() + temp.toString();
        }
        return res.toString();
    }

    //Charitable Giving (Goldman Sachs)
    public String[] charityAllocation (int[] profits) {
        if (profits == null || profits.length == 0) {
            String[] x = {};
            return x;
        }
        int len = profits.length;
        String[] res = new String[len];
        Map<String, Integer> money = new HashMap<>();
        money.put("A", 0);
        money.put("B", 0);
        money.put("C", 0);
        PriorityQueue<String> pq = new PriorityQueue<>(3, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int dif = money.get(o1) - money.get(o2);
                if (dif == 0) {
                    return o1.charAt(0) - o2.charAt(0);
                } else {
                    return dif;
                }
            }
        });
        pq.add("A");
        pq.add("B");
        pq.add("C");
        for (int i = 0; i < len; i++) {
            String temp = pq.poll();
            res[i] = temp;
            money.put(temp, money.get(temp) + profits[i]);
            pq.add(temp);
        }
        return res;
    }

    //Whole minute dilemma (Goldman Sachs)--a bit similar to 2Sum
    public int playlist (int[] songs) {
//        if (songs == null || songs.length == 0) {
//            return 0;
//        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < songs.length; i++) {
            map.putIfAbsent(songs[i], 0);
            map.put(songs[i], map.get(songs[i]) + 1);
        }
        int comb = 0;
        int len = map.keySet().size();
        int[] songLis = new int[len];
        int n = 0;
        for (int x : map.keySet()) {
            songLis[n] = x;
            n++;
        }
        Arrays.sort(songLis);
        int max;
        if (len > 1) {
            max = (songLis[len - 1] + songLis[len - 2])/60;
        } else {
            max = (songLis[len - 1] + songLis[len - 1])/60;
        }
        for (int i = 1; i <= max; i++) {
            int time = 60*i;
            int p = 0, q = len - 1;
            while (p <= q) {
                int sum = songLis[p] + songLis[q];
                if (sum == time) {
                    if (p != q) {
                        comb += map.get(songLis[p]) * map.get(songLis[q]);
                        p++;
                        q--;
                    } else {
                        int num = map.get(songLis[p]);
                        comb += num*(num - 1)/2;
                        break;
                    }
                } else if (sum < time){
                    //p move to the right
                    p++;
                } else {
                    //q move to the left
                    q--;
                }
            }
        }
        return comb;
    }

    //latest student (Goldman Sachs)
    public String latestStudent(String[][] input) {
        if (input == null || input.length == 0) {
            return "";
        }
        //Map<String, Double> sum = new HashMap<>();
        Map<String, Integer> num = new HashMap<>();
        Map<String, Double> avg = new HashMap<>();
        for (String[] s : input) {
            avg.putIfAbsent(s[0], 0.0);
            num.putIfAbsent(s[0], 0);
            Double time = Double.parseDouble(s[3]);
            Double start = Double.parseDouble(s[2]);
            avg.put(s[0], avg.get(s[0]) + (time > start ? time : start));
            num.put(s[0], num.get(s[0]) + 1);
        }
        for (String st : avg.keySet()) {
            avg.put(st, avg.get(st)/num.get(st));
        }
        Map<String, Double> late = new HashMap<>();
        for (String[] s : input) {
            late.putIfAbsent(s[1], 0.0);
            Double lateTime = Double.parseDouble(s[3]) - avg.get(s[0]);
            late.put(s[1], late.get(s[1]) + (lateTime > 0 ? lateTime : 0.0));
        }
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                double dif = late.get(o2) - late.get(o1);
                if (dif == 0) {
                    return o1.compareTo(o2);
                } else if (dif > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        for (String st : late.keySet()) {
            pq.add(st);
        }
        return pq.poll();
    }

    //csv formatter (Goldman Sachs)
    public String formatter(List<String> form) {
        StringBuilder sb = new StringBuilder("");
        List<Integer> colLen = new ArrayList<>();       //max length of columns
        List<List<String>> csv = new ArrayList<>();

        for (String s : form) {
            List<String> row = new ArrayList<>(Arrays.asList(s.split(",")));
            //String last = row.get(row.size() - 1);

            for(int i = s.length() - 1; i >= 0 && s.charAt(i) == ','; i--) {
                row.add("");
            }
            csv.add(row);
            for (int i = 0; i < row.size(); i++) {
                if (i < colLen.size()) {
                    colLen.set(i, Math.max(colLen.get(i), row.get(i).length()));
                } else {
                    colLen.add(row.get(i).length());
                }
            }
        }

        for (List<String> row : csv) {
            for (int i = 0; i < row.size(); i++) {
                int len = colLen.get(i);
                if (i != 0) {
                    len++;
                }
                String temp = row.get(i);
                for (int j = 0; j < len - temp.length(); j++) {
                    sb.append(" ");
                }
                sb.append(temp);
            }
            sb.append("end\n");
        }
        return sb.toString();
    }

    //reverse algebraic expression (Goldman Sachs)
    public String reverseAlgebra(String s) {
        Set<Character> operator = new HashSet<>();
        operator.add('+');
        operator.add('-');
        operator.add('*');
        operator.add('/');
        StringBuilder sb = new StringBuilder();
        int numLen = 0;     //number length
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (!operator.contains(c) || (c == '-' && (i == 0 || operator.contains(s.charAt(i - 1))))) {
                sb.insert(sb.length() - numLen, c);
                numLen++;
            } else {
                 numLen = 0;
                 sb.append(c);
            }
        }
        return sb.toString();
    }

    static String winner(String erica, String bob) {
        int[] eLevel = winnerHelper(erica);  //0-E 1-M 2-H 3-total score
        int[] bLevel = winnerHelper(bob);

        for (int i = 3; i >= 0; i--) {
            if (eLevel[i] >  bLevel[i]) {
                return "Erica";
            } else if (eLevel[i] <  bLevel[i]) {
                return "Bob";
            } else if (i == 0){
                //tie
                return "Tie";
            }
        }
        return "Tie";
    }

    private static int[] winnerHelper (String s) {
        int[] win = new int[4];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case 'S': break;
                case 'E': win[3]++; win[0]++; break;
                case 'M': win[3] += 3; win[1]++; break;
                case 'H': win[3] += 5; win[2]++; break;
            }
        }
        return win;
    }

    static int maxDifferenceOddEven(List<Integer> a) {
        int minOdd = Integer.MAX_VALUE;
        int maxDif = -1;
        for (int x : a) {
            if (x%2 == 0) {
                //x even
                if(x > minOdd) {
                    maxDif = Math.max(maxDif, x - minOdd);
                }
            } else {
                //x odd
                minOdd = Math.min(minOdd, x);
            }
        }
        return maxDif;
    }


    //No.688
    double stay = 0;
    public double knightProbability1(int N, int K, int r, int c) {
        double total = Math.pow(8, K);
        dfs688(N, K, r, c);
        return stay/total;
    }

    private void dfs688 (int N, int toMove, int r, int c) {
        if (r < 0 || r > N - 1 || c < 0 || c > N - 1) {
            return;
        }
        if (toMove == 0) {
            //this is the last step of K moves
            stay++;
        } else {
            dfs688 (N, toMove - 1, r + 1, c + 2);
            dfs688 (N, toMove - 1, r + 1, c - 2);
            dfs688 (N, toMove - 1, r + 2, c - 1);
            dfs688 (N, toMove - 1, r + 2, c + 1);
            dfs688 (N, toMove - 1, r - 1, c + 2);
            dfs688 (N, toMove - 1, r - 1, c - 2);
            dfs688 (N, toMove - 1, r - 2, c + 1);
            dfs688 (N, toMove - 1, r - 2, c - 1);
        }
    }

    public double knightProbability(int N, int K, int r, int c) {
        if (K == 0) {
            if (inBoard(N, r, c)) {
                return 1;
            }
            return 0;
        }
        double[][][] prob = new double[K][N][N];
        int[][] move = {{1, 2}, {1, -2}, {2, 1}, {2, -1}, {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};
        //start from move == 0
        for (int j = 0; j < N; j++) {
            for (int k = 0; k < N; k++) {
                prob[0][j][k] = 1;
            }
        }
        for (int i = 1; i < K; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    for (int m = 0; m < 8; m++) {
                        int row = j + move[m][0];
                        int col = k + move[m][1];
                        if (inBoard(N, row, col)) {
                            prob[i][j][k] += prob[i - 1][row][col];
                        }
                    }
                    prob[i][j][k] /= 8;
                }
            }
        }
        double res = 0;
        for (int m = 0; m < 8; m++) {
            int row = r + move[m][0];
            int col = c + move[m][1];
            if (inBoard(N, row, col)) {
                res += prob[K - 1][row][col];
            }
        }
        res /= 8;
        return res;
    }

    private boolean inBoard (int N, int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    //No.387
    public int firstUniqChar1(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i);
            if (map.keySet().contains(temp)) {
                map.put(temp, 2);
            } else {
                map.put(temp, 1);
            }
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }
    public int firstUniqChar2(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i);
            if (set.contains(temp)) {
                continue;
            }
            set.add(temp);
            if (i == s.lastIndexOf(temp)) {
                return i;
            }
        }
        return -1;
    }

    //the string contain only lowercase letters.
    public int firstUniqChar(String s) {
        int min = Integer.MAX_VALUE;
        for (char c = 'a'; c <= 'z'; c++) {
            int ind = s.indexOf(c);
            if (ind >= 0 && ind == s.lastIndexOf(c) && ind < min) {
                min = ind;
            }
        }
        if (min != Integer.MAX_VALUE) {
            return min;
        }
        return -1;
    }

    //No.20
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        int len = s.length();
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        LinkedList<Character> open = new LinkedList<>();
        //list a little faster than a stack

        for (int i = 0; i < len; i++) {
            char temp = s.charAt(i);
            if (map.keySet().contains(temp)) {
                open.add(temp);
            } else if (open.size() == 0 || temp != map.get(open.pollLast())){
                return false;
            }
        }
        return open.isEmpty();
    }

    //lintcode 140
    public int fastPower(int a, int b, int n) {
        //(a ^ n) % b
        if (a == 0) {
            return 0;
        }
        if (n == 0) {
            return 1 % b;
        }
        if (n == 1) {
            return a % b;
        }
        long temp = fastPower(a, b, n / 2);
        long mod;
        if (n % 2 == 0) {
            mod = temp * temp % b;
        } else {
            mod = ((temp * temp) % b) * a % b;
        }
        return (int)mod;
    }

    //No.22
    public List<String> generateParenthesis(int n) {
        List<String> res = new LinkedList<>();
        if (n == 0) {
            return res;
        }
        int openPar = 1;
        StringBuilder sb = new StringBuilder("(");
        parHelper(res, sb, openPar, n);
        return res;
    }

    private void parHelper(List<String> res, StringBuilder sb, int openPar, int n) {
        if (openPar == n) {
            int len = sb.length();
            while(sb.length() < n * 2) {
                sb.append(")");
            }
            res.add(sb.toString());
            return;
        }

        StringBuilder temp = new StringBuilder(sb.toString() + "(");
        parHelper(res, temp, openPar + 1, n);

        if(openPar * 2 > sb.length()) {
            sb.append(")");
            parHelper(res, sb, openPar, n);
        }

    }

    //lintcode 152
    public List<List<Integer>> combine(int n, int k) {
        if (n == 0) {
            return null;
        }
        List<List<Integer>> res = new ArrayList<>();

        //get all combinations in the range start from 1
        combineHelper(n, k, res, new ArrayList<Integer>(), 1);

        return res;
    }

    private void combineHelper (int n, int k, List<List<Integer>> res, List<Integer> temp, int start) {
        if (k == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i <= n; i++) {
            temp.add(i);
            combineHelper(n, k - 1, res, temp, i + 1);
            temp.remove(temp.size() - 1);
        }
    }



    public static void main(String[] args) {
        Others3 o = new Others3();
        /*int[] a = {7, 2, 3, 10,1,4,8,1};
        List<Integer> l = new ArrayList<>();
        l.add(7);
        l.add(2);
        l.add(3);
        l.add(10);
        l.add(1);
        l.add(4);
        l.add(8);
        l.add(1);
        System.out.print(maxDifferenceOddEven(l));*/
        List<String> lis = o.generateParenthesis(3);
        for (String s : lis) {
            System.out.println(s);
        }

    }



}