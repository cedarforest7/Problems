public class Sort {
    //No.75
    //two-pass: counting sort
    //one-pass: insertion sort?
    public void sortColors(int[] nums) {
        int[] colors = new int[3];
        for (int x : nums) {
            colors[x]++;
        }
        int i = 0;
        for (; i < colors[0]; i++) {
            nums[i] = 0;
        }
        for (; i < colors[0] + colors[1]; i++) {
            nums[i] = 1;
        }
        for (; i < nums.length; i++) {
            nums[i] = 2;
        }
    }

    public void sortIntegers2(int[] A) {
        if (A == null) {
            return;
        }
        quicksort(A, 0, A.length - 1);
    }

    //lintcode 464
    private void quicksort (int[] A, int start, int end) {
        // quicksort
        if (end <= start) {
            return;
        }
        //partition
        int left = start, right = end;
        int mid = left + (right - left) / 2;
        int pivot = A[mid];
        while (left <= right) {
            while (left <= right && A[left] < pivot) {
                //current num is at the correct position
                left++;
            }
            while (left <= right && A[right] > pivot) {
                right--;
            }
            //swap left and right
            if (left <= right) {
                swap(A, left, right);
                left++;
                right--;
            }
        }
        quicksort(A, start, right);
        quicksort(A, left, end);
    }

    public int kthLargestElement(int n, int[] nums) {
        // partition
        if (nums == null || n > nums.length) {
            return -1;
        }
        return partition (nums, 0, nums.length - 1, n);
    }


    private int partition(int[] nums, int start, int end, int k) {
        if (k == end - start + 1) {
            int min = nums[start];
            for (int i = start + 1; i <= end; i++) {
                min = Math.min(min, nums[i]);
            }
            return min;
        }
        int left = start, right = end;
        int pivot = nums[left + (right - left) / 2];
        while (left <= right) {
            while (left <= right && nums[left] < pivot) {
                left++;
            }
            while (left <= right && nums[right] > pivot) {
                right--;
            }
            if (left <= right) {
                swap(nums, left, right);
                left++;
                right--;
            }
        }
        System.out.println(pivot + " " + left + " " + right + " " + start + " " + end);
        int rLen = end - left + 1;
        if (left + 2 == right && k == rLen + 1) {
            return nums[right - 1];
        }
        if (rLen < k) {
            //in the left half
            return partition(nums, start, right, k - rLen - (left - right - 1));
        } else {
            //rLen >= k, target in the right half
            return partition(nums, left, end, k);
        }
    }

    private void swap (int[] nums, int left, int right) {
        if (left == right) {
            return;
        }
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }


    public static void main(String[] args) {
        Sort s = new Sort();
        int[] nums = {9, 3, 2, 4, 8 ,9};
        System.out.println(s.kthLargestElement(4, nums));
    }
}
