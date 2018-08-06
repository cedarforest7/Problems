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

    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet();
        char[][] grid = {{'1', '1', '0', '0', '0'}, {'1', '1', '1', '1', '0'}, {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}};
//        int res = ds.numIslands(grid);
//        System.out.println(res);

    }

}
