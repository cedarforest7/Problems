import java.util.*;


public class Array2 {
    //No.47
    //dp, find the permutations of the first n - 1 elments, then add the nth element in different proper positions
    public List<List<Integer>> permuteUnique1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        if (len == 0) {
            return res;
        }
        if (len == 1) {
            List<Integer> lis = new ArrayList<>();
            lis.add(nums[0]);
            res.add(lis);
            return res;
        }
        List<List<Integer>> pre = permuteUnique1(Arrays.copyOf(nums, len - 1));
        int last = nums[len - 1];
        for (List lis : pre) {
            int i = lis.size() - 1;
            for (; i >= 0 && (int)lis.get(i) != last; i--);     //get the index of the last dup; if no dup i = -1
            for (int j = i + 1; j <= lis.size(); j++) {
                lis.add(j, last);
                res.add(new ArrayList<>(lis));
                lis.remove(j);
            }
        }
        return res;
    }

    //dfs
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        boolean[] marked = new boolean[n];
        List<List<Integer>> res = new ArrayList<>();
        dfs47(res, new ArrayList<Integer>(), marked, nums);
        return res;
    }
    //parameters: result list, a temp lis(as edgeTo, record the route visited), current node(not necessary in this problem?),
    // marked(if visited), orginal input(graph)
    private void dfs47 (List<List<Integer>> res, ArrayList<Integer> lis, boolean[] marked, int[] nums) {
        if(lis.size() == nums.length) {
            res.add(new ArrayList<>(lis));
            return;
        }
        for (int j = 0; j < nums.length; j++) {
            //if nums[j] is a dup, only put it at current position of the lis when its previous dup is already in the lis
            if(!marked[j] && (j == 0|| nums[j] != nums[j - 1] || (nums[j] == nums[j - 1] && marked[j - 1]))) {
                marked[j] = true;
                lis.add(nums[j]);
                dfs47(res, lis, marked, nums);
                lis.remove(lis.size() - 1);
                marked[j] = false;
            }
        }
    }

    public static void main(String[] args) {
        Array2 a = new Array2();
        int[] nums = {1, 2, 2, 1, 3};
        List<List<Integer>> l = a.permuteUnique(nums);
        for (List lx : l) {
            for (Object x : lx) {
                System.out.print(x + " ");
            }
            System.out.println("end\n");
        }
    }
}
