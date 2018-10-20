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

    //No.273
    public String numberToWords(int num) {
        return null;
    }

    public static void main(String[] args) {
        Strings r = new Strings();
        System.out.print(r.numJewelsInStones("aA", "bbbb"));

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


}
