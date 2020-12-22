// LeetCode 189. Rotate Array.

class Solution {
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return;
        }
        if (k == 0) {
            return;
        }
        int cnt = 0;
        
        while (cnt < k) {
            int tmp = nums[nums.length - 1];
            
            for (int i = nums.length - 1; i > 0; i--) {
                nums[i] = nums[i - 1];
            }
            
            nums[0] = tmp;
            cnt++;
        }
    }
}