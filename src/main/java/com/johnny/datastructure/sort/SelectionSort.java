package com.johnny.datastructure.sort;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 选择排序
 *
 * 从待排序序列按一定规则选择出某一元素，再依规定交换位置后达到排序的目的
 */
public class SelectionSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sort(array);
        System.out.println("排序后 " + Arrays.toString(array));
    }

    /**
     * 选择排序
     * @param array 数组
     */
    public static void sort(int[] array) {
        // 第一个循环控制遍历次数
        for (int i = 0; i < array.length - 1; i++) {
            // 假定当前数为最小数
            int minIndex = i;
            int min = array[i];

            // 第二个循环控制判断位置
            for (int j = i + 1; j < array.length; j++) {
                // 如果发现假定的并不是最小数则重置
                if (min > array[j]) {
                    min = array[j];
                    minIndex = j;
                }
            }

            // 将找到的最小数和当前数交换
            if (minIndex != i) {
                array[minIndex] = array[i];
                array[i] = min;
            }
        }
    }
}
