import java.util.LinkedList;

public class Array3 {
    //239
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null) {
            return null;
        }
        if (nums.length == 0 || k < 1) {
            return new int[0];
        }
        int len = k > nums.length ? 1 : nums.length - k + 1;
        int[] res = new int[len];
        LinkedList<Integer> lis = new LinkedList<>();   //monotonically decreasing stack
        for (int i = 0; i < nums.length; i++) {
            pushMonoStack(lis, nums[i]);
            if (i >= k - 1){
                int max = lis.peekFirst();
                res[i - k + 1] = max;
                if (max == nums[i - k + 1]) {
                    lis.pollFirst();
                }
            }
        }
        return res;
    }

    private void pushMonoStack(LinkedList<Integer> lis, int x) {
        while(!lis.isEmpty() && lis.peekLast() < x) {
            lis.pollLast();
        }
        lis.offerLast(x);
    }
}
