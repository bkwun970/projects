// Leetcode Remove Duplicates from Sorted Array

class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int cur = nums[0];
        int index = 1;
        
        // [0,0,1,1,1,2,2,3,3,4]
        // [0,1,2,3,4]
        for (int i = 0; i < nums.length; i++) {
            if (cur != nums[i]) {
                nums[index] = nums[i];
                cur = nums[i];
                index++;
            }
        }
        
        return index;
    }
}