import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Strings2 {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null) {
            return res;
        }

        s = cleanString(s);

        //get the number of misplaced ( and )
        int[] excess = numMisplaced(s);

        //remove misplaced par
        removePar(res, s, 0, excess[0], excess[1]);
        return res;
    }

    //dfs
    private void removePar(List<String> res, String s, int start, int misLeft, int misRight) {
        if (misLeft == 0 && misRight == 0) {
            if (isValid(s)) {
                res.add(s);
            }
            return;
        }
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' && misLeft > 0 && (i == 0 || s.charAt(i - 1) != '(')) {
                removePar(res, s.substring(0, i) + s.substring(i + 1), i, misLeft - 1, misRight);
            } else if (c == ')' && misRight > 0 && (i == 0 || s.charAt(i - 1) != ')')) {
                removePar(res, s.substring(0, i) + s.substring(i + 1), i, misLeft, misRight - 1);
            }
        }
    }

    private boolean isValid(String s) {
        int[] temp = numMisplaced(s);
        return temp[0] == 0 && temp[1] == 0;
    }

    private String cleanString(String s) {
        StringBuilder sb = new StringBuilder();
        int start = 0;
        //remove all ) at the beginning
        while (start < s.length()) {
            char c = s.charAt(start);
            if (c == '(') {
                break;
            } else if (c == ')') {
                start++;
            } else {
                //not ( or )
                sb.append(c);
                start++;
            }
        }

        s = sb.toString() + s.substring(start);
        sb = new StringBuilder();

        int end = s.length() - 1;
        while (end >= 0) {
            char c = s.charAt(end);
            if (c == ')') {
                break;
            } else if (c == '(') {
                end--;
            } else {
                //not ( or )
                sb.insert(0, c);
                end--;
            }
        }

        return s.substring(0, end + 1) + sb.toString();
    }


    private int[] numMisplaced(String s) {
        int misLeft = 0, misRight = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                misLeft++;
            } else if (s.charAt(i) == ')') {
                if (misLeft > 0) {
                    misLeft--;
                } else {
                    misRight++;
                }
            }
        }
        return new int[]{misLeft, misRight};
    }

    //76
    public String minWindow(String s, String t) {
        Map<Character, Integer> lack = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            lack.put(c, lack.getOrDefault(c, 0) + 1);
        }
        int i = 0, j = 0;     //substring, both inclusive
        int min = Integer.MAX_VALUE;
        int start = 0, end = 0;

        int totalNeed = t.length();

        while (j < s.length()) {
            char c = s.charAt(j);
            if (lack.containsKey(c)) {
                if (lack.get(c) > 0) {
                    totalNeed--;
                }
                lack.put(c, lack.get(c) - 1);
            }
            //squeeze i
            while (totalNeed == 0) {
                char temp = s.charAt(i);
                if (lack.containsKey(temp)) {
                    lack.put(temp, lack.get(temp) + 1);
                    if (lack.get(temp) > 0) {
                        if (j - i + 1 < min) {
                            min = j - i + 1;
                            start = i;
                            end = j;
                        }
                        totalNeed++;
                    }
                }
                i++;
            }
            j++;
        }

        if (min == Integer.MAX_VALUE) {
            return "";
        }
        return s.substring(start, end + 1);
    }

    public static void main(String[] args) {
        Strings2 r = new Strings2();

        System.out.println(r.minWindow("ABCBALCA", "ABCC"));
    }
}
