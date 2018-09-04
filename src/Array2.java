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
        int l1 = (m - 1)/2, l2 = HALF - l1;
        int r1 = l1 + 1, r2 = l2 + 1;
        int low = 0, high = n - 1;
        while (low <= high) {
            System.out.println(l1 + " " + r1 + " " + l2 + " " + r2);
            if (nums1[l1] > nums2[r2]) {
                //move cut1 to the left and cut2 to the right
                low = r2 + 1;
            } else {
                high = l2 - 1;
            }
            l2 = (low + high)/2;
            l1 = HALF - l2;
            r1 = l1 + 1;
            r2 = l2 + 1;
            if (l2< 0 || r2 > n - 1 || (nums1[l1] <= nums2[r2] && nums1[l2] <= nums2[r1])) {
                break;
            }
        }
        if ((m + n)%2 == 0) {   //even
            int a = Math.max(l1 >= 0 ? nums1[l1] : Integer.MIN_VALUE, l2 >= 0 ? nums2[l2] : Integer.MIN_VALUE);
            int b = Math.min(r1 > m - 1 ? nums1[r1] : Integer.MIN_VALUE, r2 > n - 1 ? nums2[r2] : Integer.MAX_VALUE);
        }
        return 0.0;
    }

    //No.15
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3 || nums[nums.length - 1] < 0) {
            return res;
        }
        //List<Integer> lis = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i == 0 || nums[i] != nums[i - 1]) {
                //lis.add(nums[i]);
                twoSum(nums, i + 1, 0 - nums[i], res);
                //lis.remove(0);
            }
        }
        return res;
    }

    private void twoSum (int[] nums, int k, int sum, List<List<Integer>> res) {
        int len = nums.length;
        int l = k, r = len - 1;
        while (l < r) {
            int left = nums[l];
            int right = nums[r];
            int temp = left + right;
            if (temp == sum) {
//                lis.add(nums[l]);
//                lis.add(nums[r]);
//                res.add(new ArrayList<>(lis));
//                lis.remove(2);
//                lis.remove(1);
                res.add(Arrays.asList(nums[k - 1], nums[l], nums[r]));
                for (; l < len && nums[l] == left; l++);
                for (; r >= 0 && nums[r] == right; r--);
            } else if (temp < sum) {
                for (; l < len && nums[l] == left; l++);
            } else {
                for (; r >= 0 && nums[r] == right; r--);
            }
        }
    }

    //No.33
    //may assume no duplicate exists in the array.
    //solution below is not optimal--O(n) not O(logn)
    public int search1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        //find the pivot
        int p = 0;
        int len = nums.length;
        for (; p < len - 1; p++) {
            if (nums[p] > nums[p + 1]) {
                break;
            }
        }
        //int low = p + 1,high = p;
        int[] ar = new int[len];
        System.arraycopy(nums, p + 1, ar, 0, len - p - 1);
        System.arraycopy(nums, 0, ar, len - p - 1, p + 1);
        //binary search
        int l = 0, r = len - 1, mid, ind = -1;
        while (l <= r) {
            mid = (l + r)/2;
            if (ar[mid] < target) {
                l = mid + 1;
            } else if (ar[mid] > target) {
                r = mid - 1;
            } else {
                ind = mid;
                break;
            }
        }
        if (ind < 0) {
            return ind;
        }
        if (ind < len - p - 1) {
            return p + 1 + ind;
        }
        return ind - len + p + 1;
    }

    public int search(int[] nums, int target) {
        if (nums == null) {
            return -1;
        }
        int len = nums.length;
        int l = 0, r = len - 1, mid;
        while (l <= r) {
            mid = (l + r)/2;
            if (nums[mid] < nums[l]) {
                if ((target >= nums[l] && target > nums[mid]) || (target < nums[mid] && target < nums[r])) {
                    r = mid - 1;
                } else if (target == nums[mid]) {
                    return mid;
                } else {
                    l = mid + 1;
                }
            } else if (nums[mid] >= nums[r]){
                if ((target > nums[l] && target > nums[mid]) || (target < nums[mid] && target <= nums[r])) {
                    l = mid + 1;
                } else if (target == nums[mid]) {
                    return mid;
                } else {
                    r = mid - 1;
                }
            } else {
                if (target < nums[mid]) {
                    r = mid - 1;
                } else if (target == nums[mid]) {
                    return mid;
                } else {
                    l = mid + 1;
                }
            }
        }
        return -1;
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
        int[] nums2 = {4,5,6,7,0,1,2};
        System.out.print(a.search(nums2, 3));
    }
}
