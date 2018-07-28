import java.util.*;

public class Sets {
    //No.90 Subsets II
    public static List<List<Integer>> subsetsWithDup1(int[] nums) {
        List<Integer> list = new LinkedList<>();
        List<List<Integer>> subsets = new LinkedList<>();
        if (nums.length == 0) {
            subsets.add(list);
            return subsets;
        }
        Map<Integer, Integer> countMap = new HashMap<>(); //count the frequency
        for (int i = 0; i < nums.length; i++) {
            countMap.putIfAbsent(nums[i], 0);
            countMap.put(nums[i], countMap.get(nums[i]) + 1);
        }
        subsets.addAll(subsetsWithDup1(Arrays.copyOfRange(nums, 0, nums.length - 1)));
        List<List<Integer>> pre = subsetsWithDup1(Arrays.copyOfRange(nums, 0, nums.length - 1));
        int x = nums[nums.length - 1];
        System.out.println(x);      //for test
        for (List l : pre) {
            int count = 0;
            for (Object num : l) {
                if ((int)num == x) {
                    count++;
                }
            }
            if (count == countMap.get(x) - 1) {
                l.add(x);
                subsets.add(l);
            }
        }
        return subsets;
    }

    public static List<List<Integer>> subsetsWithDup(int[] nums) {

    }



    public static void main(String[] args) {
        int[] nums = {1,2,2};
        subsetsWithDup(nums);
    }
}
