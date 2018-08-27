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
        System.out.println(o.multiply("12", "34"));
    }



}