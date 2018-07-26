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
}
