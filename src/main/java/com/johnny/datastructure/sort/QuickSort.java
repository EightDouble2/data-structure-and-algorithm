package com.johnny.datastructure.sort;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 快速排序
 *
 * 快速排序是对冒泡排序的优化
 *
 * 通过一次排序将要配虚的数据分成独立的两份，其中一部分数据比另一部分数据都要小
 * 然后将两部分数据分别进行快速排序，递归完成排序
 */
public class QuickSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sort(array, 0, array.length - 1);
        System.out.println("排序后 " + Arrays.toString(array));
    }

    /**
     * 快速排序
     * @param array 数组
     * @param left 序列最左侧下标
     * @param right 序列最右侧下标
     */
    public static void sort(int[] array, int left, int right) {
        // 最左侧下标比最右侧下标大则排序完成
        if(left > right) {
            return;
        }

        // 左右下标
        int l = left;
        int r = right;

        // 设置最左侧的为基准数值
        int standard = array[left];

        // 临时变量
        int temp;

        // 当循环到l和r相等时跳出循环
        while (l < r) {

            // 从左侧找到找到比基准数大的值的下标
            while (array[l] <= standard && l < r) {
                l++;
            }

            // 从右侧找到找到比基准数小的值的下标
            while (array[r] >= standard && l < r) {
                r--;
            }

            // 比基准数大的值在比基准数小的值的左边，则交换位置
            if (l < r) {
                temp = array[l];
                array[l] = array[r];
                array[r] = temp;
            }
        }

        // 将基准数值与l和r相等的位置交换，此时基准值左侧比右侧的值都要小
        array[left] = array[l];
        array[l] = standard;

        // 递归左侧序列
        sort(array, left, r - 1);

        // 递归右侧序列
        sort(array, l + 1, right);
    }
}
