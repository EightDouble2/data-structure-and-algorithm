package com.johnny.datastructure.sort;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 插入排序
 *
 * 将待排序序列看成是一个有序表(第一个元素)和无序表(其余元素)，依次在无序列表中找出一个元素插入到有序列表的对应位置，使之形成新的有序表
 */
public class InsertionSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sort(array);
        System.out.println("排序后 " + Arrays.toString(array));
    }

    /**
     * 插入排序
     * @param array 数组
     */
    public static void sort(int[] array) {
        // 第一个循环控制遍历次数
        for (int i = 1; i < array.length; i++) {
            // 临时变量，存放待无序列表中第一个元素的坐标和值
            int insertIndex = i;
            int insertValue = array[i];

            // 第二个循环控制判断位置，从有序序列尾部开始判断，如果遍历到的值大于待插入的值，就将其后移，否则说明找到位置
            while (insertIndex > 0 && array[insertIndex - 1] > insertValue) {
                array[insertIndex] = array[insertIndex - 1];
                insertIndex--;
            }

            // 跳出循环则说明找到待插入的位置，插入待插入的值
            array[insertIndex] = insertValue;
        }
    }
}
