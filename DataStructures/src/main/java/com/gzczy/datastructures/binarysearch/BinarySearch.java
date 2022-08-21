package com.gzczy.datastructures.binarysearch;

/**
 * 二分查找 #704
 * https://leetcode.cn/problems/binary-search/
 *
 * @author ConradJam
 * @date 2022-08-21 16:04:27
 */
public class BinarySearch {

  public static void main(String[] args) {
    int arr[] = new int[]{-1, 0, 3, 5, 9, 12};
    System.out.println(search(arr, 2));
  }

  public static int search(int[] nums, int target) {
    int n = nums.length;
    int low = 0;
    int high = n - 1;

    if (target < nums[low] && target > nums[high])
      return -1;

    while (low <= high) {
      int mid = (low + high) / 2;
      if (nums[mid] < target) {
        low = mid + 1;
      } else if (nums[mid] > target) {
        high = mid - 1;
      } else {
        return mid;
      }
    }
    return -1;
  }
}
