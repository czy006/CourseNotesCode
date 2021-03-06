package com.gzczy.datastructures.atguigu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 三数之和
 * @url https://leetcode-cn.com/problems/3sum/
 * @Author chenzhengyu
 */
public class ThreeSum {

  public static void main(String[] args) {
    int nums[] = {-1, 0, 1, 1, 2, -1, -4};
    int nums2[] = {};
    int nums3[] = {0};
    System.out.println(threeSum2(nums));
    System.out.println(threeSum2(nums2));
    System.out.println(threeSum2(nums3));
  }

  /**
   * 方法1：暴力法
   * 缺点 时间复杂度为o（n的三次方）且无法去重
   * 运算结果：[[-1, 0, 1], [-1, 0, 1], [-1, 2, -1], [0, 1, -1], [0, 1, -1]]
   *
   * @param nums
   * @return
   */
  public static List<List<Integer>> threeSum(int[] nums) {
    int n = nums.length;
    List<List<Integer>> resultList = new ArrayList<>();
    // 三重循环，遍历所有的三数组合
    for (int i = 0; i < n - 2; i++) {
      for (int j = i + 1; j < n - 1; j++) {
        for (int k = j + 1; k < n; k++) {
          if (nums[i] + nums[j] + nums[k] == 0) {
            resultList.add(List.of(nums[i], nums[j], nums[k]));
          }
        }
      }
    }
    return resultList;
  }

  /**
   * 方法二：双指针
   * 时间复杂度 O(N^2)：其中固定指针k循环复杂度 O(N)，双指针 i，j 复杂度 O(N)。比暴力法的O(n^3)，显然有了很大的改善。
   * 空间复杂度 O(1)：指针使用常数大小的额外空间
   *
   * @param nums
   * @return
   */
  public static List<List<Integer>> threeSum2(int[] nums) {
    int n = nums.length;
    List<List<Integer>> result = new ArrayList<>();
    //0、进行排序 快排的时间复杂度为 n * logn
    Arrays.sort(nums);
    //1、进入数组遍历 每一次作为1个数进行固定 左右指针同时移动查找数据为0的值
    for (int i = 0; i < n; i++) {
      //2.1 如果当前的数值是大于0 则无论怎么相加都没可能等于0 故中断
      if (nums[i] > 0) break;
      //2.2 因为当i第一次被赋值为某个值的时候,已经和后面所有的数值已经过相加, 所以当i再次被赋值的时候,
      // 就没必要再进行一次了,所以就跳过了
      if (i > 0 && nums[i] == nums[i - 1]) continue;
      //2.3 定义左右指针
      //2.3.1 左指针 当前i的下标右面，因为前面的数已经曾今作为过核心了 故不用再次尝试
      int lp = i + 1;
      //2.3.2 右指针 数组最大长度 - 1 注意这里是 n - 1
      int rp = n - 1;
      // 2.4 左指针和右指针进行比较 如果左指针小于右指针 左右指针重合，相交后都需要中断
      while (lp < rp) {
        int sum = nums[i] + nums[lp] + nums[rp];
        //如果计算结果等于0
        if (sum == 0) {
          //添加进入List，并且左下标向左移动，由下标向右移动
          result.add(List.of(nums[i], nums[lp], nums[rp]));
          lp++;
          rp--;
          // 如果移动之后的元素相同，那么直接跳过
          // 左指针 小于 右指针 且 下一个左下标的值 等于 当前左下标
          while (lp < rp && nums[lp] == nums[lp - 1]) {
            lp++;
          }
          // 左指针 小于 右指针 且 当前左下标的值 等于 当前右下标
          while (lp < rp && nums[lp] == nums[rp + 1]) {
            rp--;
          }
          //如果这个统计的和 小于0 证明了 我们的小的数偏小了，所以要往右移一点
        } else if (sum < 0) {
          lp++;
          //如果统计这个数大于0了 证明我们大的数那边偏大了 要往小的方向挪动一点点
        } else {
          rp--;
        }
      }
    }
    return result;
  }
}
