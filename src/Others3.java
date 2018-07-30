import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Others3 {
    //No.263
    public boolean isUgly(int num) {
        if (num <= 0) {
            return false;
        }
        while (num % 2 == 0) {
            num = num/2;
        }
        while (num % 3 == 0) {
            num = num/3;
        }
        while (num % 5 == 0) {
            num = num/5;
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
        for (int i = 1; i < rowIndex;i++) {
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
        for (int i = 1; i < rowIndex;i++) {
            list.add(Math.toIntExact(temp));
            temp = temp*(rowIndex - i)/(i + 1);
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
                if ( x > 0 && x <= 9 ) {
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
                if ( x > 0 && x <= 9 ) {
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
                        if ( x > 0 && x <= 9 ) {
                            if (s.contains(board[i][j]))   {
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
}
