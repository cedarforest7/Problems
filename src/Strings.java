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

    public static void main(String[] args) {
        Strings r = new Strings();
        //System.out.print(r.numJewelsInStones("aA", "bbbb"));
        //System.out.println(r.wordPatternMatch("aa", "def"));
        char[][] board = {{'d', 'o', 'a', 'f'}, {'a', 'g', 'a', 'i'}, {'d', 'c', 'a', 'n'}};
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
        }

    }


}
