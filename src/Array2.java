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


    //No.332
    Map<String, List<String>> iti = new HashMap<>();
    List<String> res332 = new LinkedList<>();
    public List<String> findItinerary(String[][] tickets) {
        int n = tickets.length;
        for (int i = 0; i < n; i++) {
            iti.putIfAbsent(tickets[i][0],new LinkedList<>());
            iti.get(tickets[i][0]).add(tickets[i][1]);
        }
        String start = "JFK";
        //res332.add(start);
        //sort the list of destinations of each staring point
        for (String s : iti.keySet()) {
            Collections.sort(iti.get(s));
        }
//        while (res332.size() <= n) {
//            List<String> dest = iti.get(start);
//            res332.add(dest.get(0));
//            String temp = dest.get(0);
//            dest.remove(0);
//            if (dest.isEmpty()) {
//                iti.remove(start);
//            }
//            start = temp;
//        }
        dfs332(n, start);
        return res332;
    }

    private boolean dfs332 (int n, String start) {
        res332.add(start);
        if (res332.size() == n + 1) {
            return true;
        }
        List<String> dest = iti.get(start);
        if (dest == null) {
            res332.remove(res332.size() - 1);
            return false;
        }
        for (int i = 0; i < dest.size(); i++) {
            String temp = dest.get(i);
            dest.remove(i);
            if (dfs332(n, temp)) {
                return true;
            }
            dest.add(i, temp);
        }
        res332.remove(res332.size() - 1);
        return false;
    }

    //No.238
    public int[] productExceptSelf1(int[] nums) {
        int prod = 1;
        int zero = 0;
        for (int n : nums) {
            if (n == 0) {
                zero++;
            } else {
                prod *= n;
            }
        }
        int[] res = new int[nums.length];
        if (zero == 0) {
            for (int i = 0; i < nums.length; i++) {
                res[i] = prod/nums[i];
            }
        } else if (zero == 1) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0) {
                    res[i] = prod;
                }
            }
        }

        return res;
    }

    public int[] productExceptSelf(int[] nums) {
        //len > 1
        int[] res = new int[nums.length];
        int prod = 1;
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        for (int i = nums.length - 2; i >=0; i--) {
            prod *= nums[i + 1];
            res[i] *= prod;
        }
        return res;
    }

    //No.88
    /*public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0, j = 0;
        int len = m;
        int k = m;  //traverse the numbers moved to the back
        while (k < m + n && i < len && j < n) {
            if (k < len) {
                if (i < m) {
                    //move i to the back
                    nums1[len] = nums1[i];
                    len++;
                }
                if (nums1[k] < nums2[j]) {
                    //put the smaller num to position i
                    nums1[i] = nums1[k];
                    k++;
                } else {
                    //put the smaller num to position i
                    nums1[i] = nums2[j];
                    j++;
                }
            } else if (nums1[i] > nums2[j]) {
                //move i to the back
                nums1[len] = nums1[i];
                len++;
                //put the smaller num to position i
                nums1[i] = nums2[j];
                j++;
            }
            i++;
        }
        if (j < n) {
            while (i < m + n) {
                nums1[i] = nums2[j];
                i++;
                j++;
            }
        }


    }*/
    //extra space O(m)
    public void merge1(int[] nums1, int m, int[] nums2, int n) {

        int[] temp = new int[m];
        for (int i = 0; i < m; i++) {
            temp[i] = nums1[i];
        }
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (temp[i] < nums2[j]) {
                nums1[i + j] = temp[i];
                i++;
            } else {
                nums1[i + j] = nums2[j];
                j++;
            }
        }
        if (j < n) {
            while (i + j < m + n) {
                nums1[i + j] = nums2[j];
                j++;
            }
        } else {
            while (i + j < m + n) {
                nums1[i + j] = temp[i];
                i++;
            }
        }
    }
    //use O(1) extra space
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = nums1.length - 1;
        //copy nums1 first m numbers to the end of nums1
        for (int j = m - 1; j >= 0; i--, j--) {
            nums1[i] = nums1[j];
        }
        i++;
        int st = i;
        int j = 0;
        while (i < nums1.length && j < n) {
            if (nums1[i] < nums2[j]) {
                nums1[i - st + j] = nums1[i];
                i++;
            } else {
                nums1[i - st + j] = nums2[j];
                j++;
            }
        }
        if (j < n) {
            while (i - st + j < m + n) {
                nums1[i - st + j] = nums2[j];
                j++;
            }
        } else {
            while (i - st + j < m + n) {
                nums1[i - st + j] = nums1[i];
                i++;
            }
        }
    }


    //No.31
    public void nextPermutation1(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        if (isLargest(nums, 0)) {
            Arrays.sort(nums);
            return;
        }
        int len = nums.length;
        if (isLargest(nums, 1)) {
            Arrays.sort(nums, 1, len);
            int i = 1;
            for (; i < len; i++) {
                if (nums[i] > nums[0]) {
                    break;
                }
            }
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
            Arrays.sort(nums, 1, len);
        } else {
            int[] next = Arrays.copyOfRange(nums, 1, len);
            nextPermutation(next);
            System.arraycopy(next, 0, nums, 1, len - 1);
        }
    }


    //whether nums is the largest permutation when only considering the digits >= st
    private boolean isLargest (int[] nums, int st) {
        for (int i = st; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void nextPermutation2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int len = nums.length;
        for (int i = len - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                Arrays.sort(nums, i, len);
                for (int k = i; k < len; k++) {
                    if (nums[k] > nums[i - 1]) {
                        int temp = nums[i - 1];
                        nums[i - 1] = nums[k];
                        nums[k] = temp;
                        break;
                    }
                }
                Arrays.sort(nums, i, len);
                return;
            }
        }
        Arrays.sort(nums);
    }

    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int len = nums.length;
        for (int i = len - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                int k = i;
                for (; k < len && nums[k] > nums[i - 1]; k++);
                switchNum(nums, i - 1, k - 1);
                for (int m = i, n = len - 1; m < n; m++, n--) {
                    switchNum(nums, m, n);
                }
                return;
            }
        }
        for (int m = 0, n = len - 1; m < n; m++, n--) {
            switchNum(nums, m, n);
        }
    }

    private void switchNum(int[] nums, int m, int n) {
        int temp = nums[m];
        nums[m] = nums[n];
        nums[n] = temp;
    }

    //lintcode 585. Maximum Number in Mountain Sequence
    public int mountainSequence(int[] nums) {
        // binary search
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int len = nums.length;
        int low = 0, hi = len - 1, mid = hi / 2;
        while (low <= hi) {
            if ((mid == 0 || nums[mid] >= nums[mid - 1]) && (mid == len - 1 || nums[mid] >= nums[mid + 1])) {
                //mid is top
                return nums[mid];
            } else if ((mid == 0 || nums[mid] > nums[mid - 1]) && (mid == len - 1 || nums[mid] < nums[mid + 1])) {
                //mid in incresing trend
                low = mid + 1;
                //System.out.println("low: " + low);
            } else {
                //mid in decresing trend
                hi = mid - 1;
                //System.out.println("hi: " + hi);
            }
            mid = (low + hi) / 2;
        }
        return -1;
    }



    /**
     * lintcode 460
     * @param A: an integer array
     * @param target: An integer
     * @param k: An integer
     * @return: an integer array
     */
    public int[] kClosestNumbers(int[] A, int target, int k) {
        // find the position of target assuming it is in A
        if (A == null || A.length == 0) {
            return A;
        }
        int[] res = new int[k];
        int len = A.length;
        int low = 0, hi = len - 1, mid = (len - 1) / 2;
        int left = -1, right;
        while (low <= hi) {
            int middle = A[mid];
            if (middle == target) {
                left = mid;
                break;
            } else if (middle > target) {
                hi = mid - 1;
            } else {
                low = mid + 1;
            }
            mid = (low + hi) / 2;
        }
        if (left == -1) {
            left = hi;
            right = low;
        } else {
            right = left + 1;
        }
        //System.out.println(left + " " + right);
        //put the closest num into res
        for (int i = 0; i < k; i++) {
            if (left >= 0 && (right > len - 1 || target - A[left] <= A[right] - target)) {
                res[i] = A[left];
                left--;
            } else {
                res[i] = A[right];
                right++;
            }
        }
        return res;
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
        int[] nums1 = {4,0,0, 0, 0, 0};
        int[] nums2 = {9,9,0};
//        System.out.print(a.search(nums2, 3));
/*        String[][] s = {{"JFK","KUL"},{"JFK","NRT"},{"NRT","JFK"}};
        List<String> lis = a.findItinerary(s);
        for (String x : lis) {
            System.out.print(x + " ");
        }*/

        /*a.merge(nums1, 1, nums2, 5);
        */
        a.nextPermutation(nums2);
        for (int x : nums2) {
            System.out.print(x + " ");
        }
    }
}
