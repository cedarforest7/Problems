import java.util.*;

public class DisjointSet {
    //No.200
    //use individual inspection is faster (like set matrix 0): each time 1 occurs, set to 0, search in four directions for next 1
    // and set the next 1 to 0, until meet 0

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;

        Set<Integer> dSet = new HashSet<>();
        Map<Integer, Integer> dsMap = new HashMap<>();      //store the parent of the coord

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(grid[i][j] == '1') {
                    int index = i*n + j;
                    int up = index - n;
                    int left = index - 1;
                    if (dsMap.keySet().contains(up)) {      //up is 1
                        int upParent = dsMap.get(up);
                        dsMap.put(index, upParent);     //current's parent is the same as upParent
                        if (j > 0 && dsMap.keySet().contains(left) && upParent != dsMap.get(left)) {         //left is 1
                            int leftParent = dsMap.get(left);
                            for (int x : dsMap.keySet()) {          //set all leftParent's children to be upParent's children
                                if (dsMap.get(x) == leftParent) {
                                    dsMap.put(x, upParent);
                                }
                            }
                        }
                    } else if (j > 0 && dsMap.keySet().contains(left)) {  //up is 0, left is 1
                        dsMap.put(index, dsMap.get(left));
                    }
                    dsMap.putIfAbsent(index, index);
                }
            }
        }
        for (int x : dsMap.keySet()) {
            dSet.add(dsMap.get(x));
        }
        return dSet.size();
    }

    class DisjointSet1{
        private int[] parent;

        public DisjointSet1(int size) {
            parent = new int[size];
            //initialization
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        //find highest ancestor
        public int find(int k) {
            if (parent[k] != k) {
                parent[k] = find(parent[k]);
            }
            return parent[k];
        }

        //union two set of nodes
        public boolean union(int i, int j) {
            int par1 = find(i);
            int par2 = find(j);
            if (par1 != par2) {
                parent[par1] = par2;
                return true;
            }
            return false;
        }

    }
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        boolean[][] grid = new boolean[m][n];
        DisjointSet1 ds = new DisjointSet1(m * n);
        List<Integer> res = new ArrayList<>();

        int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};   //go left, right, up, down
        for (int i = 0; i < positions.length; i++) {
            int[] pos = positions[i];
            if (grid[pos[0]][pos[1]]) {
                res.add(res.get(i - 1));
                continue;
            }
            int index = pos[0] * n + pos[1];
            int lands = i == 0 ? 1 : (res.get(i - 1) + 1);
            grid[pos[0]][pos[1]] = true;
            for (int j = 0; j < 4; j++) {
                int p = pos[0] + dir[j][0], q = pos[1] + dir[j][1];
                if (inBoard(p, q, m, n) && grid[p][q]) {
                    if(ds.union(index, p * n + q)) {
                        lands--;
                    }
                }
            }
            res.add(lands);
        }
        return res;
    }

    private boolean inBoard(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }

    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet();
        char[][] grid = {{'1', '1', '0', '0', '0'}, {'1', '1', '1', '1', '0'}, {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}};
//        int res = ds.numIslands(grid);
//        System.out.println(res);
        System.out.println(ds.numIslands2(3, 3, new int[][]{{0,0},{0,1},{1,2},{2,1},{1,0},{0,0},{2,2},{1,2},{1,1},{0,1}}));
    }

}
