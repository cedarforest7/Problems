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

    private void swap (int[] A, int left, int right){
        int temp = A[left];
        A[left] = A[right];
        A[right] = temp;
    }
}
