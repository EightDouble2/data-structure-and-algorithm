package com.johnny.datastructure.sort;


import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 冒泡排序
 *
 * 通过对待排序序列从前向后，依次比较相邻元素的值，若发现逆序则交换位置，使值较大的元素逐渐后移
 *
 * 优化：
 * 如果有一轮比较没有发现逆序则说明序列已经有序
 */
public class BubbleSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sort(array);
        System.out.println("排序后 " + Arrays.toString(array));

        // 优化排序后
        sortImprove(array);
        System.out.println("优化排序后 " + Arrays.toString(array));
    }

    /**
     * 冒泡排序
     * @param array 数组
     */
    public static void sort(int[] array) {
        // 临时变量
        int temp;

        // 第一个循环控制遍历次数
        for (int i = 0; i < array.length - 1; i++) {
            // 第二个循环控制冒泡位置
            for (int j = 0; j < array.length - 1 - i; j++) {
                // 发现逆序交换位置
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序优化
     * @param array 数组
     */
    public static void sortImprove(int[] array) {
        int temp;

        for (int i = 0; i < array.length - 1; i++) {
            // 临时标识，表示是否发现逆序
            boolean flag = true;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    // 发现逆序改变标识
                    flag = false;
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            // 如果没有发现逆序说明序列已经有序，跳出循环
            if (flag) {
                break;
            }
        }
    }
}
