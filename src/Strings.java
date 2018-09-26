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
}
