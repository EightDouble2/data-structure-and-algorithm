package com.johnny.datastructure.sort;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 希尔排序
 *
 * 又名缩小增量排序，是对插入排序的优化
 *
 * 插入排序时，当需要插入的数较小，后移的次数明显增多，效率变低
 *
 * 希尔排序是将待排序序列按下标一定增量进行分组，对每组进行插入排序
 * 随着增量的逐渐减少，每组包含的元素越来越多，当增量减至1时，整个序列被分为一组，完成排序
 */
public class ShellSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sort(array);
        System.out.println("排序后 " + Arrays.toString(array));
    }

    /**
     * 希尔排序
     * @param array 数组
     */
    public static void sort(int[] array) {
        // 第一个循环控制分组增量
        for (int step = array.length / 2; step > 0; step /= 2) {
            // 第二个循环控制每组遍历次数，对每组进行
            for (int i = step; i < array.length; i++) {
                int insertIndex = i;
                int insertValue = array[i];

                while (insertIndex - step >= 0 && array[insertIndex - step] > insertValue) {
                    array[insertIndex] = array[insertIndex - step];
                    insertIndex -= step;
                }

                array[insertIndex] = insertValue;
            }
        }
    }
}
