package com.practice.bom.algorithm;

import java.util.Arrays;

/**
 * @author ljf
 * @description 快速排序
 * @date 2023/1/30 5:07 PM
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] nums = {11, 21, 9, 10, 15, 4, 8, 2, 99, 66, 77, 25, 21, 39};
        quickSort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    public static void quickSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int low = start;
        int high = end;
        int temp = nums[start];
        while (low < high) {
            while (low < high && nums[high] >= temp) {
                high--;
            }
            while (low < high && nums[low] <= temp) {
                low++;
            }
            if (low < high) {
                int p = nums[low];
                nums[low] = nums[high];
                nums[high] = p;
            }
        }
        nums[start] = nums[low];
        nums[low] = temp;
        quickSort(nums, start, low - 1);
        quickSort(nums, low + 1, end);
    }

}
