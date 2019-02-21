import java.util.*;

public class Strings {
    //No.139
    //brute force too slow...what if with memorization?

    /*public boolean wordBreak1(String s, List<String> wordDict) {
        Set<String> dic = new HashSet<>(wordDict);
        int len = s.length();
        for (int k = 1; k <= len; k++) {
            for (int i = 0; i + k <= len; i++) {
                String temp = s.substring(i, i + k);
                if (dic.contains(temp)) {
                    words.add(temp);
                }
            }
        }
        return dfs139(s, len, 0);
    }*/
    //Set<String> words = new HashSet<>();
    Set<Integer> notWords = new HashSet<>();
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dic = new HashSet<>(wordDict);
        int len = s.length();
        return dfs139(s, len, 0, dic);
    }

    private boolean dfs139(String s, int len, int st, Set<String> dic) {
        if (st == len) {
            return true;
        }
        for (int i = 1; st + i <= len; i++) {
            String temp = s.substring(st, st + i);
            if (dic.contains(temp) && !notWords.contains(st + i) && dfs139(s, len, st + i, dic)) {
                return true;
            }
        }
        notWords.add(st);
        return false;
    }

    //No.771
    public int numJewelsInStones(String J, String S) {
        Set<Character> jewels = new HashSet<>();
        for (int i = 0; i < J.length(); i++) {
            jewels.add(J.charAt(i));
        }
        int res = 0;
        for (int i = 0; i < S.length(); i++) {
            if (jewels.contains(S.charAt(i))) {
                res++;
            }
        }
        return res;
    }

    //No.819
    public String mostCommonWord(String paragraph, String[] banned) {
        //String[] words =  paragraph.split("\\W+");
        String[] words =  paragraph.split("\\s+");
        Map<String, Integer> freq = new HashMap<>();
        Set<String> ban = new HashSet<>();
        int max = 0;
        String res = "";
        for (String s : banned) {
            ban.add(s);
        }
        for (String st : words) {
            String s = st.toLowerCase();
            char last = s.charAt(s.length() - 1);
            if (last < 97) {
                s = s.substring(0, s.length() - 1);
            }
            if (!ban.contains(s)) {
                freq.putIfAbsent(s, 0);
                int temp = freq.get(s) + 1;
                freq.put(s, temp);
                if (temp > max) {
                    max = temp;
                    res = s;
                }
            }
        }
        return res;
    }





    //lintcode 200

    public String longestPalindrome(String s) {
        // enumeration
        if (s == null || s.length() == 0) {
            return s;
        }
        int len = s.length();
        int st = 0;
        int max = 1;
        //if pal is even
        for (int i = 0; i < len - max/2 - 1; i++) {
            int l = i, r = i + 1;
            for (; l >= 0 && r < len && s.charAt(l) == s.charAt(r); l--, r++);
            l++;
            r--;
            if (max < r - l + 1) {
                max = r - l + 1;
                st = l;
            }
        }
        //if pal is odd
        for (int i = 1; i < len - max/2; i++) {
            int l = i - 1, r = i + 1;
            for (; l >= 0 && r < len && s.charAt(l) == s.charAt(r); l--, r++);
            l++;
            r--;
            if (max < r - l + 1) {
                max = r - l + 1;
                st = l;
            }
        }
        return s.substring(st, st + max);
    }
    //lintcode 667
    public int longestPalindromeSubseq(String s) {
        // write your code here
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        int[][] pal = new int[len][len];
        for (int i = 0; i < len; i++) {
            //initialize the shortest substring (length 1) with 1
            pal[i][i] = 1;
        }
        //k is the length of substring
        for (int k = 2; k <= len; k++) {
            for (int i = 0; i + k - 1 < len; i++) {
                int end = i + k - 1;
                if(s.charAt(i) == s.charAt(end)) {
                    pal[i][end] += 2 + pal[i + 1][end - 1];
                } else {
                    pal[i][end] = Math.max(pal[i + 1][end], pal[i][end - 1]);
                }
            }
        }
        return pal[0][len - 1];
    }

    /*
     * lintcode 594
     * @param source: A source string
     * @param target: A target string
     * @return: An integer as index
     */
    static final int  BASE = 10000000;
    public int strStr2(String source, String target) {
        if (source == null || target == null || source.length() < target.length()) {
            return -1;
        }
        if (source.length() == 0 && target.length() == 0) {
            return 0;
        }
        int m = target.length();
        int n = source.length();
        int tCode = 0;
        int sCode = 0;
        //M = 31 ^ (m - 1)
        int M = 1;

        //compute hashCode of target and initialize source hashCode
        for (int i = 0; i < m; i++) {
            tCode = (tCode * 31 + target.charAt(i)) % BASE;
            //System.out.println(tCode);
            sCode = (sCode * 31 + source.charAt(i)) % BASE;

        }
        for (int i = 0; i < m - 1; i++) {
            M = M * 31 % BASE;
        }
        //System.out.println(tCode);

        //traverse the source string
        for (int i = 0; i < n - m + 1; i++) {
            //System.out.println(i + " " + sCode);
            if (sCode == tCode && source.substring(i, i + m).equals(target)) {
                return i;
            }
            if (i == n - m) {
                break;
            }
            sCode = sCode - (source.charAt(i) * M) % BASE;
            if (sCode < 0) {
                sCode += BASE;
            }
            sCode = (sCode * 31 + source.charAt(i + m)) % BASE;
        }

        return -1;
    }

    public static String decode(String encoded) {
        StringBuilder sb = new StringBuilder(encoded);
        sb.reverse();
        StringBuilder res = new StringBuilder();

        int i = 0;
        while (i < encoded.length()) {
            String temp = "";
            char c = sb.charAt(i);
            temp += String.valueOf(c);
            if (c == '1') {
                i++;
                c = sb.charAt(i);
                temp += String.valueOf(c);
            }
            i++;
            c = sb.charAt(i);
            temp += String.valueOf(c);
            System.out.println(temp);
            res.append(convertCode(temp));
            i++;
        }
        return res.toString();
    }

    private static char convertCode (String s) {
        int code = Integer.parseInt(s);
        return (char) code;
    }

    //lintcode 136
    public List<List<String>> partition(String s) {
        List<List<String>> initial = new LinkedList<>();
        if (s == null) {
            return initial;
        }
        int len = s.length();
        if (len == 0) {
            List<String> temp = new LinkedList<>();
            temp.add("");
            initial.add(temp);
            return initial;
        }
        List<List<List<String>>> memo = new ArrayList<>();
        //memo.get(i) is correspoding to ending index i - 1
        //find all partition combinations ending from index 0 to len - 1
        //initialization
        initial.add(new LinkedList<>());
        memo.add(initial);
        for (int i = 0; i < len; i++) {
            // extend to previous positions so that substring(k, i + 1) is a pal, add all memo[k] plus the current palindrome\
            //as memo[i]
            List<List<String>> resOfIndex = new LinkedList<>();
            for (int k = i; k >= 0; k--) {
                if (!isPal(s, k, i)) {
                    continue;
                }
                for (List lis : memo.get(k)) {
                    List<String> temp = new LinkedList<>(lis);
                    temp.add(s.substring(k, i + 1));
                    resOfIndex.add(temp);
                }
            }
            memo.add(resOfIndex);
        }
        return memo.get(len);
    }

    private boolean isPal(String s, int k, int i) {
        while (k < i) {
            if (s.charAt(k) == s.charAt(i)) {
                k++;
                i--;
            } else {
                return false;
            }
        }
        return true;
    }
    //lintcode 829 word pattern II
    public boolean wordPatternMatch(String pattern, String str) {
        // bijection between a letter in pattern and a non-empty substring in str
        if (str == null || pattern == null) {
            return false;
        }
        Map<Character, String> map = new HashMap<>();
        Set<String> used = new HashSet<>();
        //match from the 0th char of pattern and 0th char of str
        return match(pattern, 0, str, 0, map, used);

    }

    private boolean match (String pattern, int p, String s, int q, Map<Character, String> map, Set<String> used) {
        if (p == pattern.length() && q == s.length()) {
            return true;
        }
        if (pattern.length() == p || (pattern.length() - p) > (s.length() - q)) {
            return false;
        }
        char cur = pattern.charAt(p);
        String curMap = map.get(cur);
        //match cur to [q, i] in s
        for (int i = q; i < s.length(); i++) {
            String substr = s.substring(q, i + 1);
            //System.out.println(cur + " " + substr);
            if (curMap != null && !curMap.startsWith(substr)) {
                return false;
            }
            if ((used.contains(substr) || curMap != null) && !substr.equals(curMap)) {
                continue;
            }

            if (curMap == null) {
                map.put(cur, substr);
                used.add(substr);
                boolean temp = match(pattern, p + 1, s, i + 1, map, used);
                if (temp) {
                    return true;
                }
                map.remove(cur);
                used.remove(substr);
            } else {
                //used.contains(substr) && substr.equals(curMap)
                boolean temp = match(pattern, p + 1, s, i + 1, map, used);
                if (temp) {
                    return true;
                }
            }

        }
        return false;
    }
    //lintcode 132
    public List<String> wordSearchII(char[][] board, List<String> words) {
        Set<String> res = new HashSet<>();
        if (words == null || board == null) {
            return new ArrayList<>();
        }

        Set<String> prefix = getPreix (words);
        Set<String> dict = new HashSet<>(words);
        boolean[][] visited = new boolean[board.length][board[0].length];

        //get all qualified words start with an empty prefix followed by each position and
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs132(board, dict, prefix, res, new StringBuilder(), visited, i, j);
            }
        }

        return new ArrayList<>(res);
    }

    //get all qualified words start with a prefix pre followed by position (row, col)
    private void dfs132(char[][] board, Set<String> dict, Set<String> prefix, Set<String> res, StringBuilder pre, boolean[][] visited, int i, int j) {
        String temp = pre.toString();
        if (dict.contains(temp)) {
            res.add(temp);
        }
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || visited[i][j]) {
            return;
        }

        pre.append(board[i][j]);
        if (!prefix.contains(pre.toString())) {
            pre.deleteCharAt(pre.length() - 1);
            return;
        }
        visited[i][j] = true;
        //up
        dfs132(board, dict, prefix, res, pre, visited, i - 1, j);
        //down
        dfs132(board, dict, prefix, res, pre, visited, i + 1, j);
        //left
        dfs132(board, dict, prefix, res, pre, visited, i, j - 1);
        //right
        dfs132(board, dict, prefix, res, pre, visited, i, j + 1);
        visited[i][j] = false;
        pre.deleteCharAt(pre.length() - 1);
    }



    private Set<String> getPreix(List<String> words) {
        Set<String> prefix = new HashSet<>();
        for (String s : words) {
            for (int i = 0; i < s.length(); i++) {
                prefix.add(s.substring(0, i + 1));
            }
        }
        return prefix;
    }

    //No.99
    public int numUniqueEmails(String[] emails) {
        if (emails == null || emails.length == 0) {
            return 0;
        }
        Set<String> addr = new HashSet<>();
        for (String s : emails) {
            if (s == null) {
                continue;
            }
            addr.add(realAddress(s));
        }
        return addr.size();
    }

    private String realAddress(String s) {

        boolean plus = false;
        boolean domain = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == '@') {
                domain = true;
            }
            if (domain) {
                sb.append(cur);
                continue;
            }
            if (plus || cur == '.') {
                continue;
            }
            if (cur == '+') {
                plus = true;
            } else {
                sb.append(cur);
            }
        }
        return sb.toString();
    }

    //lintcode 209
    public char firstUniqChar(String str) {
        if (str == null || str.length() == 0) {
            return '0';
        }
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            count.putIfAbsent(c, 0);
            count.put(c, count.get(c) + 1);
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (count.get(c) == 1) {
                return c;
            }
        }
        return '0';
    }

    //151
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        String[] s1 = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = s1.length - 1; i >= 0; i--) {
            if (s1[i].length() != 0) {
                sb.append(s1[i] + " ");
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    //8
    public int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int res = 0;

        int i = 0;
        boolean isNeg = false;
        while (i < str.length() && str.charAt(i) == ' ') {
            i++;
        }
        if (i >= str.length()) {
            return 0;
        }
        if (str.charAt(i) < '0' || str.charAt(i) > '9') {
            if (str.charAt(i) == '-') {
                isNeg = true;
                i++;
            } else if (str.charAt(i) == '+') {
                i++;
            } else {
                return 0;
            }
        }
        int firstNonZero = Integer.MAX_VALUE;
        for (int j = i; j < str.length() && str.charAt(j) >= '0' && str.charAt(j) <= '9'; j++) {
            int temp = str.charAt(j) - '0';
            if (temp != 0 && firstNonZero == Integer.MAX_VALUE) {
                firstNonZero = j;
            }
            if (j - firstNonZero == 9) {
                //10 digits now
                if (res > Integer.MAX_VALUE / 10) {
                    return isNeg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                }
                if (res == Integer.MAX_VALUE / 10) {

                    if (isNeg && temp >= 8) {
                        return Integer.MIN_VALUE;
                    }
                    if (!isNeg && temp >= 7) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            if (j - firstNonZero == 10) {
                // > 10 digits
                return isNeg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            res = res * 10 + temp;

        }
        if (isNeg) {
            res = -res;
        }
        return res;
    }

    //394
    int pos = 0;
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        String res = "";
        while (pos < s.length()) {
            res += decodeString(s, pos);
        }
        return res;
    }

    private String decodeString(String s, int start) {
        int i = start;
        String res = "";
        while (i < s.length()) {
            int n = 0;
            String str = "";
            while(s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                n = 10 * n + s.charAt(i) - '0';
                i++;
            }

            if (i < s.length() && s.charAt(i) == '[') {
                i++;
            }
            while (i < s.length() && s.charAt(i) != ']') {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    pos = i;
                    str += decodeString(s, pos);
                    i = pos;
                } else {
                    str += s.charAt(i);
                    i++;
                }
            }
            if (i < s.length() && s.charAt(i) == ']') {
                res += decodeHelper(str, n);
                i++;
                break;
            }
            res = str;
        }
        pos = i;
        return res;
    }

    private String decodeHelper(String str, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    //844
    public boolean backspaceCompare(String S, String T) {
        if (S == null || T == null) {
            return false;
        }
        int i = S.length() - 1, j = T.length() - 1;
        int bs1 = 0, bs2 = 0;
        while (i >= 0 && j >= 0) {
            while (i >= 0 && S.charAt(i) == '#') {
                i--;
                bs1++;
            }
            while (j >= 0 && T.charAt(j) == '#') {
                j--;
                bs2++;
            }
            while (i >= 0 && S.charAt(i) != '#' && bs1 > 0) {
                i--;
                bs1--;
            }
            while (j >= 0 && T.charAt(j) != '#' && bs2 > 0) {
                j--;
                bs2--;
            }
            if (i >= 0 && j >= 0 && bs1 == 0 && bs2 == 0 && S.charAt(i) != '#' && T.charAt(j) != '#') {
                if (S.charAt(i) != T.charAt(j)) {
                    return false;
                } else {
                    i--;
                    j--;
                }
            }

        }

        if (i < 0) {
            while (j >= 0) {
                while (j >= 0 && T.charAt(j) == '#') {
                    j--;
                    bs2++;
                }
                while (j >= 0 && T.charAt(j) != '#' && bs2 > 0) {
                    j--;
                    bs2--;
                }
                if (j >= 0 && bs2 == 0 && T.charAt(j) != '#') {
                    return false;
                }
            }
        } else {
            while (i >= 0) {
                while (i >= 0 && S.charAt(i) == '#') {
                    i--;
                    bs1++;
                }
                while (i >= 0 && S.charAt(i) != '#' && bs1 > 0) {
                    i--;
                    bs1--;
                }
                if (i >= 0 && bs1 == 0 && S.charAt(i) != '#') {
                    return false;
                }
            }
        }
        return true;
    }

    //301



    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();

        int left = 0, right = 0;
        //left and right are the # of parentheses to remove
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                left++;
            } else if (c == ')') {
                //this part is essential!!
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }
        Helper(res, s, 0, left, right);
        return res;
    }

    private boolean isValid(String s) {
        int netPar = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                netPar++;
            } else if (c == ')') {
                netPar--;
            }
            if (netPar < 0) {
                return false;
            }
        }
        return true;
    }

    private void Helper(List<String> res, String s, int start, int left, int right) {
        if (left == 0 && right == 0) {
            if (isValid(s)) {
                res.add(s);
            }
            return;
        }

        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (i > start && c == s.charAt(i - 1)) {
                //belongs to the same group (identical), skip to avoid duplicate
                //only the first element of the same group would be deleted
                continue;
            }
            if (left > 0 && c == '(') {
                Helper(res, s.substring(0, i) + s.substring(i + 1), i, left - 1, right);
            } else if (right > 0 && c == ')') {
                Helper(res, s.substring(0, i) + s.substring(i + 1), i, left, right - 1);
            }
        }
    }

    //242
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            count[c - 'a']--;
            if (count[c - 'a'] < 0) {
                return false;
            }
        }
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            count.putIfAbsent(c, 0);
            count.put(c, count.get(c) + 1);
        }
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (!count.containsKey(c)) {
                return false;
            } else {
                int charCOunt = count.get(c) - 1;
                if (charCOunt == 0) {
                    count.remove(c);
                } else {
                    count.put(c, charCOunt);
                }

            }
        }
        return count.isEmpty();
    }

    //76
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0) {
            return "";
        }

        //count chars in t
        Map<Character, Integer> countT = new HashMap<>();

        for (char c : t.toCharArray()) {
            countT.put(c, countT.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> countS = new HashMap<>();
        //traverse substrings of s
        int start = 0, end = 0;
        int lastStart = 0, lastEnd = s.length();


        Set<Character> visited = new HashSet<>();       //count is enough for chars in the set
        while(end < s.length()) {
            char c = s.charAt(end);
            int count = countS.getOrDefault(c, 0) + 1;
            if (!countT.containsKey(c)) {
                end++;
                continue;
            }
            countS.put(c, count);
            int targetCount = countT.get(c);
            if (count == targetCount) {
                visited.add(c);
                if (visited.size() == countT.size()) {
                    //move start
                    while(visited.size() == countT.size()){
                        char startChar = s.charAt(start);
                        if (countT.containsKey(startChar)) {
                            int startCount = countS.get(startChar) - 1;
                            countS.put(startChar, startCount);
                            if (startCount < countT.get(startChar)) {
                                visited.remove(startChar);
                            }
                        }
                        start++;
                    }
                    //start--;
                    if (end - start + 1 < lastEnd - lastStart) {
                        lastStart = start - 1;
                        lastEnd = end;
                    }
                }
            }
            end++;
        }

        if(lastEnd == s.length()) {
            return "";
        }
        return s.substring(lastStart, lastEnd + 1);
    }

    public static void main(String[] args) {
        Strings r = new Strings();
        //System.out.print(r.numJewelsInStones("aA", "bbbb"));
        //System.out.println(r.wordPatternMatch("aa", "def"));
        /*char[][] board = {{'d', 'o', 'a', 'f'}, {'a', 'g', 'a', 'i'}, {'d', 'c', 'a', 'n'}};
        char[][] board2 = {{'d', 'o'}, {'a', 'g'}};
        List<String> words = new ArrayList<>();

        words.add("dog");
        words.add("dad");
        words.add("dgdg");
        words.add("can");
        words.add("again");
        List<String> res132= r.wordSearchII(board, words);
        for (String s : res132) {
            System.out.println(s);
        }*/
        //String s = "()()))r)";
        //Helper.printList(r.removeInvalidParentheses(s));
        System.out.println(r.minWindow("ADOBECODEBANC", "ABKC"));


    }


}
