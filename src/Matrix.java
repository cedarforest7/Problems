import java.util.*;
import java.util.HashMap;

public class Matrix {
    //No.59
    public static int[][] generateMatrix1(int n) {
        int[][] matrix = new int[n][n];
        if (n == 1) {
            matrix[0][0] = 1;
            return matrix;
        }
        int x = 2, i = -1, j = 0;
        for (int k = n; x < n*n + 1; k--) {
            for (i++, x--; j < k; j++, x++ ) {
                matrix[i][j] = x;
            }
            for (j--, x--; i < k; i++, x++) {
                matrix[i][j] = x;
            }
            for (i--, x--; j > n - k - 1; j--, x++) {
                matrix[i][j] = x;
            }
            if (x == n*n) {
                return matrix;
            }
            for (j++, x--; i > n - k; i--, x++) {
                matrix[i][j] = x;
            }
        }
        return matrix;
    }

    public static int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int i = 0, j = 0, di = 0, dj = 1, x = 1;
        while (x < n*n + 1) {
            matrix[i][j] = x++;
            i += di;
            j += dj;
            if (i + j == n - 1) {
                int temp = di;
                di = dj;
                dj = temp;
            }
            if ((i == j && i >= n/2) || (i == j + 1 && j < n/2)){
                int temp = -di;
                di = dj;
                dj = temp;
            }
        }
        return matrix;
    }

    public void rotate(int[][] matrix) {
        int len = matrix.length;
        if (len == 0) {
            return;
        }
        transpose(matrix);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len/2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][len - 1 - j];
                matrix[i][len - 1 - j] = temp;
            }
        }
    }

    private void transpose (int[][] matrix) {
        int len = matrix.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
    //No.64
    public static int minPathSum1(int[][] grid) {
//        class node {
//            boolean marked;
//            int distance;
//            int weight;
//            node edgeTo;
//            node (int i, int j) {
//                weight = grid[i][j];
//                edgeTo = null;
//            }
//        }
//        public int
//        PriorityQueue<node> fringe;
//        A* does not work as distance to goal cannot be estimated
        return minPathHelper(grid, grid.length - 1, grid[0].length - 1);
    }
    //min path to grid[m][n]
    private static int minPathHelper (int[][] grid, int m, int n) {
        int min = 0;
        if(m == 0) {
            int j;
            for (j = 0; j <= n; j++) {
                min += grid[0][j];
            }
            return min;
        }
        if (n == 0) {
            int i;
            for (i = 0; i <= m; i++) {
                min += grid[i][0];
            }
            return min;
        }
        min = Math.min(minPathHelper(grid, m - 1, n), minPathHelper(grid, m, n - 1)) + grid[m][n];
        return min;
    }

    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if (m*n == 0) {
            return 0;
        }
        int[][] min = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    if (j == 0) {
                        min[0][0] = grid[0][0];
                    } else {
                        min[0][j] = min[0][j-1] + grid[0][j];
                    }
                } else if (j == 0) {
                    min[i][0] = min[i-1][0] + grid[i][0];
                } else {
                    min[i][j] = Math.min(min[i][j-1], min[i-1][j]) + grid[i][j];
                }
            }
        }
        return min[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        //System.out.println(minPathSum(grid));
        System.out.println(minPathSum(grid));
    }


}
