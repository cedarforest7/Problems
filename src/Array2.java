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

    //No.4
//    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        int m = nums1.length;
//        int n = nums2.length;
//        if (m < n) {
//            return findMedianSortedArrays(nums2, nums1);
//        }
//        double med;
//        if (m == 1 && n == 1) {
//            med = (double) (nums1[0] + nums2[0])/2;
//            return med;
//        }
//        int HALF = (m + n - 3)/2;
//        int d;
//        if ((m + n) %2 == 0) {
//            d = 1;
//        } else {
//            d = 0;
//        }
//        int l1 = (m - 1)/2, r1 = l1 + d;
//        int low = 0, high = n - 1;
//        //l2 = (low + high)/2, r2 = (2*l2 + 1)/2;
////        int d2;
////        if (n%2 == 0) {
////            d2 = 1;
////        } else {
////            d2 = 0;
////        }
//        int l2 = (n - 1)/2, r2 = l2 + d;
//        while (l2 > 0 && r2 < n - 1 && (nums1[l1] > nums2[r2] || nums2[l2] > nums1[r1])) {
//            if (nums2[l2] > nums1[r1]) {
//                //move cut1 to the right and cur2 to the left
//                high = l2 - 1;
//            } else {
//                //move cut1 to the left and cur2 to the right
//                low = l2 + 1;
//            }
//            if (high < 0) {
//                l2 = (low + high - 1)/2;
//            } else if (low > n - 1) {
//                l2 = (low + high + 1)/2;
//            } else {
//                l2 = (low + high)/2;
//            }
//            r2 = l2 + d;
//            l1 = HALF - l2;
//            r1 = l1 + d;
//        }
//        if (d == 0) {
//            med = Math.max(nums1[l1], nums2[l2]);
//        } else {
//            int left = Math.max(l1 < 0 ? nums1[0] : nums1[l1], (l2 < 0 || l2 > n - 1) ? Integer.MIN_VALUE : nums2[l2]);
//            int right = Math.min(r1 > m - 1 ? nums1[m - 1] : nums1[r1], (r2 > n - 1 || r2 < 0) ? Integer.MAX_VALUE : nums2[r2]);
//            med = (double) (left + right)/2;
//        }
//        return med;
//    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m < n) {
            return findMedianSortedArrays(nums2, nums1);
        }
        double med;
        int HALF = (m + n - 3)/2;
        int p = (m - 1)/2, q = HALF - p;
        int low = 0, high = n - 1;
        while (low <= high) {

        }

        return 0.0;
    }

    public static void main(String[] args) {
        Array2 a = new Array2();
 //       int[] nums = {1, 2, 2, 1, 3};
//        List<List<Integer>> l = a.permuteUnique(nums);
//        for (List lx : l) {
//            for (Object x : lx) {
//                System.out.print(x + " ");
//            }
//            System.out.println("end\n");
//        }
        int[] nums1 = {2, 3, 4, 4};
        int[] nums2 = {5, 6, 7};
        System.out.print(a.findMedianSortedArrays(nums1, nums2));
    }
}
