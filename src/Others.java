import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Others {
    //There is a heap of stones on the table, each time one of you take turns to remove 1 to 3 stones.
    // The one who removes the last stone will be the winner. You will take the first turn to remove the stones.
    public boolean canWinNim(int n) {
//        if (n < 4) {
//            return true;
//        }
//        return !(canWinNim(n - 1) && canWinNim(n - 2) && canWinNim(n - 3));

        return n % 4 != 0;
    }
    //Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.
    public int addDigits(int num) {
        if (num == 0) {
            return 0;
        }
        if (num % 9 != 0) {
            return num % 9;
        }
        return 9;
    }
    //Given a column title as appear in an Excel sheet, return its corresponding column number.
    public int titleToNumber(String s) {
        char[] chars = s.toCharArray();
        int num = 0;
        for (int i = 0; i < chars.length; i++) {
            num = num*26 + (chars[i] - 64);
        }
        return num;
    }
    //Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
    //I             1
    //V             5
    //X             10
    //L             50
    //C             100
    //D             500
    //M             1000
    public int romanToInt(String s) {
        int len = s.length();
        int[] digit = new int[len];
        for (int i = 0; i < len; i++) {
            switch (s.charAt(i)) {
                case 'I': digit[i] = 1; break;
                case 'V': digit[i] = 5; break;
                case 'X': digit[i] = 10; break;
                case 'L': digit[i] = 50; break;
                case 'C': digit[i] = 100; break;
                case 'D': digit[i] = 500; break;
                case 'M': digit[i] = 1000; break;
                default: break;
            }
        }
        int num = 0, j = 0;
        while (j < len) {
            if (j < len - 1 && digit[j] < digit [j + 1]) {
                num = num - digit[j] + digit[j + 1];
                j += 2;
            } else {
                num = num + digit[j];
                j++;
            }
        }
        return num;
    }

    public static String intToRoman(int num) {
        String roman = "";
        for (int i = 0; i < num/1000; i++) {
            roman = roman + "M";
        }
        num = num % 1000;
        if (num >= 900) {
            roman = roman + "CM";
            num = num % 900;
        } else if (num >= 500) {
            roman = roman + "D";
            num = num % 500;
        } else if (num >= 400) {
            roman = roman + "CD";
            num = num % 400;
        }
        for (int i = 0; i < num/100; i++) {
            roman = roman + "C";
        }
        num = num % 100;

        if (num >= 90) {
            roman = roman + "XC";
            num = num % 90;
        } else if (num >= 50) {
            roman = roman + "L";
            num = num % 50;
        } else if (num >= 40) {
            roman = roman + "XL";
            num = num % 40;
        }
        for (int i = 0; i < num/10; i++) {
            roman = roman + "X";
        }
        num = num % 10;


        if (num >= 9) {
            roman = roman + "IX";
            num = num % 9;
        } else if (num >= 5) {
            roman = roman + "V";
            num = num % 5;
        } else if (num >= 4) {
            roman = roman + "IV";
            num = num % 4;
        }

        for (int i = 0; i < num; i++) {
            roman = roman + "I";
        }

        return roman;
    }

    //Given an integer n, return the number of distinct solutions to the n-queens puzzle.
    int numSol = 0;
    public int totalNQueens(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        int[] sol = new int[n];       //store the solution (column number)
        queen(n,0, sol);
        return numSol;

    }

    private void queen(int n, int i, int[] sol) {          //put the queen in the ith row
        if (i == n) {                                           // all rows has been filled
            numSol++;
            //for test
            for (int x : sol) {
                System.out.print(x);
            }
            System.out.print("\n");
        }
        for (int j = 0; j < n; j++) {                           //j is the column of the queen in the ith row
            if (allowQueen(i, j, sol)) {
                sol[i] = j;
                queen(n, i+1, sol);                     //go to consider the next row
            }
        }
    }

    private boolean allowQueen (int i, int j, int[] sol) {     // if col j is allowed in row i
        if (i == 0) {
            return true;
        }
        for (int k = 0; k < i; k++) {                       //scan from the first col in the sol[]
            if (j == sol[k] || i - k == j - sol[k] || k - i == j - sol[k] ) {
                return false;
            }
        }
        return true;
    }
    //Given a string of numbers and operators, return all possible results from computing
    // all the different possible ways to group numbers and operators. The valid operators are +, - and *.
    public static List<Integer> diffWaysToCompute(String input) {
        int level = 1;
        char[] chars = input.toCharArray();
        for (char x : chars) {
            if (x < '.') {
                level++;
            }
        }
        //System.out.println(level);
        return computeHelper(input, level);
    }

    private static LinkedList<Integer> computeHelper(String input, int level) {
        LinkedList<Integer> l = new LinkedList<>();
        if (level == 1) {           //e.g. 1
            l.add(Integer.parseInt(input));
            return l;
        }
        if (level == 2) {       //e.g. 1+22
            l.add(calc(input));
            return l;
        }
        for ( int k = 1; k < level; k++) {
            String[] strings = splitString(input, k);
            LinkedList<Integer> l1 = computeHelper(strings[0], k);
            char operator = strings[2].charAt(0);
            LinkedList<Integer> l2 = computeHelper(strings[1], level - k);
            l.addAll(crossList(l1, l2, operator));
        }
        return l;
    }
    //split into two strings, the first onw is of level 1
    private static String[] splitString (String s, int k) {
        String[] strings = new String[3];
        int i = 0, j = 0;
        for (; i < k; i++, j++) {
            for (; s.charAt(j) > '-'; j++ );
        }
        strings[0] = s.substring(0, j - 1);
        strings[1] = s.substring(j);
        strings[2] = s.substring(j - 1);
        return strings;
    }

    private static int calc (String s) {           //s is a simple equation consists of 2 integers and one + or - or *
        char[] c = s.toCharArray();
        int a = 0, b = 0;
        char operator;
        int i = 0;
        for (; c[i] > '-'; i++ ) {
            a = a*10 + c[i] - '0';
        }
        operator = c[i];
        for (i++; i < s.length(); i++) {
            b = b*10 + c[i] - '0';
        }
        return operation(a, b, operator);
    }

    private static int operation (int x, int y, char operator) {
        switch (operator) {
            case '+': return x + y;
            case '-': return x - y;
            case '*': return x*y;
        }
        return 0;
    }

    private static LinkedList<Integer> crossList (LinkedList<Integer> l1, LinkedList<Integer> l2, char operator) {
        LinkedList<Integer> l = new LinkedList<>();
        for (int x : l1) {
            for (int y : l2) {
                l.add(operation(x, y, operator));
            }
        }
        return l;
    }

    public static void printList(LinkedList<Integer> l) {
        if (l == null) {
            System.out.print("Empty list!");
            return;
        }
        for (int x : l) {
            System.out.print(x + " ");
        }
        System.out.print("\n");
    }

    //Given a set of distinct integers, nums, return all possible subsets (the power set).
    public List<List<Integer>> subsets(int[] nums) {
        LinkedList<LinkedList<Integer>> l = new LinkedList<>();
        l.addAll(subsetHelper(nums));
        return (List)l;
    }

    HashSet<LinkedList<Integer>> s = new HashSet<>();
    public HashSet<LinkedList<Integer>> subsetHelper (int[] nums) {
        LinkedList<Integer> l1 = new LinkedList<>();
        if (nums.length == 0) {
            s.add(l1);
            return s;
        }
        int len = nums.length;
        int[] a = new int[len - 1];
        System.arraycopy(nums, 0, a, 0, len - 1);
        return subsetAddNum(subsetHelper(a), nums[len - 1]);
    }

    public HashSet<LinkedList<Integer>> subsetAddNum (HashSet<LinkedList<Integer>> hs, int num) {
        HashSet<LinkedList<Integer>> h = new HashSet<>();
        h.addAll(hs);
        for (LinkedList<Integer> l : hs) {
            LinkedList<Integer> lx = new LinkedList<>();
            lx.addAll(l);
            lx.add(num);
            h.add(lx);
        }
        return h;
    }




    public static void main(String[] args) {
        Others o = new Others();
//        int x = o.totalNQueens(4);
//        System.out.println( calc("123-22"));
//        LinkedList<Integer> l1 = new LinkedList<>();
//        l1.add(1);
//        l1.add(2);
//        l1.add(3);
//        LinkedList<Integer> l2 = new LinkedList<>();
//        l2.addAll(l1);
//        printList(crossList(l1, l2, '+'));
//        printList(computeHelper("12*2+1*2", 4));
//        printList((LinkedList<Integer>) diffWaysToCompute("12*2+1*2"));
        int[] a = {1, 2, 3};
        List<List<Integer>> l = o.subsets(a);
        for (List<Integer> x : l) {
            printList((LinkedList<Integer>) x);
            System.out.print("\n");
        }

    }
}
