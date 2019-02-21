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

    public static int roverMove(int matrixSize, List<String> cmds) {
        //position after current move
        int[] pos = {0, 0};
        for (String cmd : cmds) {
            move(pos, cmd, matrixSize);
            System.out.println(pos[0] + " " + pos[1]);
        }
        return pos[0] * matrixSize+ pos[1];
    }

    private static void move (int[] pos, String cmd, int matrixSize) {

        switch(cmd) {
            case "UP": if (pos[0] != 0) {pos[0]--;} break;
            case "DOWN": if (pos[0] != matrixSize - 1) {pos[0]++;} break;
            case "RIGHT": if (pos[1] != matrixSize - 1) {pos[1]++;} break;
            case "LEFT": if (pos[1] != 0) {pos[1]--;} break;
        }

    }

    public static int countMatches(List<String> grid1, List<String> grid2) {
        //list to matrix
        int[][] matrix1 = toMatrix(grid1);
        int row1 = matrix1.length, col1 = matrix1[0].length;

        int[][] matrix2 = toMatrix(grid2);
        //int row2 = matrix2.length, col2 = matrix2[0].length;
        int count = 0;
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                if (matrix1[i][j] == 1 && matrix2[i][j] == 1) {
                    if (match(matrix1, matrix2, i, j, row1, col1)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static int[][] toMatrix(List<String> grid) {
        int len = grid.get(0).length();
        int[][] matrix = new int[grid.size()][len];
        int i = 0;
        for (String s : grid) {
            for (int j = 0; j < len; j++) {
                if (s.charAt(j) == '0') {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = 1;
                }

            }
            i++;
        }
        return matrix;
    }

    private static boolean match(int[][] mat1, int[][] mat2, int i, int j, int row, int col) {
        mat1[i][j] = 0;
        mat2[i][j] = 0;
        boolean up = true, down = true, left = true, right = true;
        if (i != 0) {
            if (mat1[i - 1][j] != mat2[i - 1][j]) {
                up = false;
                markVisited(mat1, i - 1, j, row, col);
                markVisited(mat2, i - 1, j, row, col);
            } else if (mat1[i - 1][j] == 1 ) {
                up = match(mat1, mat2, i - 1, j, row, col);
            }
        }
        if (j != 0) {
            if (mat1[i][j - 1] != mat2[i][j - 1]) {
                left = false;
                markVisited(mat1, i, j - 1, row, col);
                markVisited(mat2, i, j - 1, row, col);
            } else if (mat1[i][j - 1] == 1 ) {
                left = match(mat1, mat2, i, j - 1, row, col);
            }
        }
        if (i != row - 1) {
            if (mat1[i + 1][j] != mat2[i + 1][j]) {
                down = false;
                markVisited(mat1, i + 1, j, row, col);
                markVisited(mat2, i + 1, j, row, col);
            } else if (mat1[i + 1][j] == 1 ) {
                down = match(mat1, mat2, i + 1, j, row, col);
            }
        }
        if (j != col - 1) {
            if (mat1[i][j + 1] != mat2[i][j + 1]) {
                right = false;
                markVisited(mat1, i, j + 1, row, col);
                markVisited(mat2, i, j + 1, row, col);
            } else if (mat1[i][j+ 1] == 1 ) {
                right = match(mat1, mat2, i, j + 1, row, col);
            }
        }
        return up && down && left && right;
    }

    private static void markVisited(int[][] mat, int i, int j, int row, int col) {
        if (mat[i][j] == 0) {
            return;
        }
        mat[i][j] = 0;
        if (i != 0 && mat[i - 1][j] == 1) {
            markVisited(mat, i - 1, j, row, col);
        }
        if (j != 0 && mat[i][j - 1] == 1) {
            markVisited(mat, i, j - 1, row, col);
        }
        if (i != row - 1 && mat[i + 1][j] == 1) {
            markVisited(mat, i + 1, j, row, col);
        }
        if (j != col - 1 && mat[i][j + 1] == 1) {
            markVisited(mat, i, j + 1, row, col);
        }
    }

    public int maxSubmatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[][] psr = getPrefixSumRows(matrix);
        int maxSum = Integer.MIN_VALUE;
        for (int n = 0; n < matrix[0].length; n++) {
            for (int m = 0; m <= n; m++) {
                maxSum = Math.max(maxSum, maxSubSum(matrix, m, n, psr));
            }
        }
        return maxSum;
    }

    private int maxSubSum(int[][] matrix, int m, int n, int[][] psr) {
        int[] row = new int[matrix.length + 1];
        for (int i = 0; i < matrix.length; i++) {
            row[i + 1] = psr[i][n + 1] - psr[i][m];
        }
        return getMaxSubArray(row);
    }

    private int getMaxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        int minPrefix = 0;
        int maxSum = nums[0];
        for (int i = 1; i < nums.length + 1; i++) {

            if (prefixSum[i] - minPrefix > maxSum) {
                maxSum = prefixSum[i] - minPrefix;
            }
            if (prefixSum[i] < minPrefix) {
                minPrefix = prefixSum[i];

            }
        }
        return maxSum;
    }

    private int[][] getPrefixSumRows(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] psr = new int[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                psr[i][j + 1] = psr[i][j] + matrix[i][j];
            }
        }
        return psr;
    }

    //lintcode 654
    public int[][] multiply(int[][] A, int[][] B) {
        if (A == null || B == null) {
            return null;
        }
        int m = A.length;
        int k = A[0].length;
        int n = B[0].length;
        List<List<Integer>> validCols = new ArrayList<>();  //in A
        List<Set<Integer>> validRows = new ArrayList<>();  //in B
        for (int i = 0; i < m; i++) {
            validCols.add(new ArrayList<>());
            for (int j = 0; j < k; j++) {
                if (A[i][j] != 0) {
                    validCols.get(i).add(j);
                }
            }
        }

        for (int j = 0; j < n; j++) {
            validRows.add(new HashSet<>());
            for (int i = 0; i < k; i++) {
                if (B[i][j] != 0) {
                    validRows.get(j).add(i);
                }
            }
        }

        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int p : validCols.get(i)) {
                    if (validRows.get(j).contains(p)) {
                        res[i][j] += A[i][p] * B[p][j];
                    }
                }
            }
        }
        return res;
    }

    //lintcode 110
    public int minPathSum2(int[][] grid) {
        // DP
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        int[][] minPath = new int[m][n];
        minPath[0][0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            minPath[0][j] = minPath[0][j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            minPath[i][0] = minPath[i - 1][0] + grid[i][0];
            for (int j = 1; j < n; j++) {
                minPath[i][j] = Math.min(minPath[i][j - 1], minPath[i - 1][j]) + grid[i][j];
            }
        }
        return minPath[m - 1][n - 1];
    }

    //No.54
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        int m = matrix.length, n = matrix[0].length;
        spiralHelper(res, matrix, 0, m, n);
        return res;
    }

    private void spiralHelper(List<Integer> res, int[][] matrix, int k, int m, int n) {
        if (k >= n - k || k >= m - k) {
            return;
        }
        int i = k, j = k;
        for (; j < n - k; j++) {
            res.add(matrix[i][j]);
        }
        j--;
        i++;
        if (i == m - k) {
            return;
        }
        for (; i < m - k; i++) {
            res.add(matrix[i][j]);
        }
        i--;
        j--;
        if (j < k || j == n - k) {
            return;
        }
        for (; j >= k; j--) {
            res.add(matrix[i][j]);
        }
        j++;
        i--;
        for (; i > k; i--) {
            res.add(matrix[i][j]);
        }
        spiralHelper(res, matrix, k + 1, m, n);
    }

    //695
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0 ||grid[0].length == 0) {
            return 0;
        }
        int m = grid.length, n = grid[0].length;
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                max = Math.max(max, dfsMaxIsland(grid, i, j, 0, m, n));
            }
        }
        return max;
    }

    private int dfsMaxIsland(int[][] grid, int i, int j, int area, int m, int n) {
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == 0) {
            return area;
        }
        area++;
        grid[i][j] = 0;
        area = dfsMaxIsland(grid, i - 1, j, area, m, n);
        area = dfsMaxIsland(grid, i + 1, j, area, m, n);
        area = dfsMaxIsland(grid, i, j - 1, area, m, n);
        area = dfsMaxIsland(grid, i, j + 1, area, m, n);
        return area;
    }

    //547
    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0 || M.length != M[0].length) {
            return 0;
        }
        int n = M.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (M[i][i] == 0) {
                continue;
            }
            count++;
            dfsCircle(M, i, n);
        }
        return count;
    }

    private void dfsCircle(int[][] M, int i, int n) {
        if (i < 0 || i >= n || M[i][i] == 0) {
            return;
        }
        for (int j = 0; j < n; j++) {
            if (j == i || M[i][j] == 0) {
                continue;       //skip self
            }
            M[i][j] = 0;   //visited
            M[j][i] = 0;
            dfsCircle(M, j, n);
        }
        M[i][i] = 0;
    }

    //924
    public int minMalwareSpread(int[][] graph, int[] initial) {
        if (graph == null || initial == null|| initial.length == 0) {
            return 0;
        }
        int n = graph.length;
        int[] parent = new int[n];         //ith node's parent is parent[i]
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            count.put(i, 1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph[i][j] == 0) {
                    continue;
                }
                //union the two nodes, change the count of elements in each disjoint set accordingly
                int iRoot = findRoot(parent, i);
                int jRoot = findRoot(parent, j);
                if (iRoot == jRoot) {
                    continue;
                }
                //union root node
                if (count.get(iRoot) > count.get(jRoot)) {
                    parent[jRoot] = iRoot;
                    parent[j] = iRoot;
                    count.put(iRoot, count.get(iRoot) + count.get(jRoot));
                    count.remove(jRoot);
                } else {
                    parent[iRoot] = jRoot;
                    parent[i] = jRoot;
                    count.put(jRoot, count.get(iRoot) + count.get(jRoot));
                    count.remove(iRoot);
                }
            }
        }
        int res = initial[0];
        for (int i = 1; i < initial.length; i++) {
            int iCount = count.get(findRoot(parent, initial[i]));
            int rCount = count.get(findRoot(parent, res));
            if ( iCount > rCount || ( iCount == rCount && initial[i] < res)) {
                res = initial[i];
            }
        }
        return res;
    }

    private int findRoot1(int[] parent, int i) {
        if (parent[i] == i) {
            return i;
        }
        //parent[i] != i
        return findRoot(parent, parent[i]);
    }

    //with path compression
    private int findRoot(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = findRoot(parent, parent[i]);
        }
        return parent[i];
    }



    public static void main(String[] args) {
        Matrix m = new Matrix();
        int[][] grid = {{1, 0, 0, 1}, {0,1,1,0}, {0,1,1,1}, {1,0,1,1}};
        int[][] grid1 = {{1},{2},{3}};
        int[][] grid5 = {{1, 2, 3, 4, 5}, {6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        /*List<Integer> res = m.spiralOrder(grid1);
        for (int x : res) {
            System.out.print(x + " ");
        }*/
        System.out.println(m.findCircleNum(grid));


    }


}
