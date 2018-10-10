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

    //No.240
//    public boolean searchMatrix1(int[][] matrix, int target) {
//        if (matrix == null || matrix.length == 0|| matrix[0].length == 0) {
//            return false;
//        }
//        int m = matrix.length, n = matrix[0].length;
//        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
//            return false;
//        }
//        int lowX = 0, lowY = 0, highX = m - 1, highY = n - 1;
//        int midX = (m - 1)/2, midY = (n - 1)/2;
//        while (!(midX == lowX && midY == lowY) && !(midX == highX && midY == highY)) {
//            System.out.println(matrix[midX][midY]);     //for test
//            if (target < matrix[midX][midY]) {
//                highX = midX;
//                midX = (lowX + highX)/2;
//                highY = midY;
//                midY = (lowY + highY)/2;
//            } else if (target == matrix[midX][midY]) {
//                return true;
//            } else {
//                lowX = midX;
//                midX = (lowX + highX)/2;
//                lowY = midY;
//                midY = (lowY + highY)/2;
//            }
//        }
//        if (target == matrix[lowX][lowY]) {
//            return true;
//        }
//        int[] col = new int[highX + 1];
//        for (int i = 0; i <= highX; i++) {
//            col[i] = matrix[i][highY];
//        }
//        return searchHelper(matrix[highX], target, highY) || searchHelper(col, target, highX);
//
//    }
    //above 240 code is wrong

    private boolean searchHelper(int[] nums, int target) {
        int end = nums.length;
        if (target < nums[0]) {
            return false;
        }
        int min = 0, max = end, mid = end/2;
        while (mid != min && mid != max) {
            System.out.println(nums[mid]);      //for test
            if (target < nums[mid]) {
                max = mid;
                mid = (max + min)/2;
            } else if (target == nums[mid]) {
                return true;
            } else {
                min = mid;
                mid = (max + min)/2;
            }
        }
        if (target == nums[min] || target == nums[max]) {
            return true;
        }
        return false;
    }


    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0|| matrix[0].length == 0) {
            return false;
        }
        int m = matrix.length, n = matrix[0].length;
        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
            return false;
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] <= target && target <= matrix[i][n - 1]) {
                if (searchHelper(matrix[i], target)) {
                    return true;
                }
            }
        }
        return false;
    }
    //above is time consuming

    //start searching from the top right corner(!), considering as a DP problem(?)
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0|| matrix[0].length == 0) {
            return false;
        }
        int m = matrix.length, n = matrix[0].length;
        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
            return false;
        }
        int i = 0, j = n - 1;
        while (i < m && j >= 0 && target != matrix[i][j]) {
            if (target > matrix[i][j]) {
                i++;
            } else {
                j--;
            }
        }
        if (i == m || j < 0) {
            return false;
        }
        return true;
    }

    //No.73
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        int[] zeroRow = new int[matrix.length];
        int[] zeroCol = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    zeroRow[i] = 1;
                    zeroCol[j] = 1;
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            if (zeroRow[i] == 1) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] = 0;
                }
            } else {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (zeroCol[j] == 1) {
                        matrix[i][j] = 0;
                    }
                }
            }
        }
    }

//    //No.200
//    public int numIslands(char[][] grid) {
//        if (grid == null || grid.length == 0 || grid[0].length == 0) {
//            return 0;
//        }
//        int m = grid.length;
//        int n = grid[0].length;
//        int isl = 0;
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if(isNewIsland(grid, i, j)) {
//                    isl++;
//                }
//            }
//        }
//        return isl;
//    }
//
//    private boolean isNewIsland(char[][] grid, int i, int j) {
//        if (grid[i][j] != '1') {
//            return false;
//        }
//        if (i == 0) {
//            if (j == 0) {
//                return true;
//            } else {
//                return grid[0][j - 1] == '0';
//            }
//        } else if (j == 0) {
//            return grid[i - 1][0] == '0';
//        }
//        return grid[i - 1][j] == '0' && grid[i][j - 1] == '0';
//    }

    //lintcode 433 same as the problem above
    public int numIslands(boolean[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int isl = 0;
        int row = grid.length;
        int col = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j]) {
                    isl++;
                    markLand(grid, i, j, row, col);
                }
            }
        }
        return isl;
    }

    private void markLand(boolean[][] grid, int i, int j, int row, int col) {
        grid[i][j] = false;
        if (i != 0 && grid[i - 1][j]) {
            markLand(grid, i - 1, j, row, col);
        }
        if (j != 0 && grid[i][j - 1]) {
            markLand(grid, i, j - 1, row, col);
        }
        if (i != row - 1 && grid[i + 1][j]) {
            markLand(grid, i + 1, j, row, col);
        }
        if (j != col - 1 && grid[i][j + 1]) {
            markLand(grid, i, j + 1, row, col);
        }
    }

    public static void main(String[] args) {
        Matrix m = new Matrix();
        int[][] grid = {{0, 1, 3}, {2, 3, 5}, {4, 5, 7}};
        int[][] grid5 = {{1, 2, 3, 4, 5}, {6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        //System.out.println(minPathSum(grid));
        System.out.println(m.searchMatrix(grid5, 5));
    }


}
