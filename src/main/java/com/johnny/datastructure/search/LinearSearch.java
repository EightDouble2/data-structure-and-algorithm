package com.johnny.datastructure.search;

/**
 * @author johnnyhao
 *
 * 线性查找
 *
 * 按顺序进行查找，数组可以是没有顺序的
 */
public class LinearSearch {

    public static void main(String[] args) {
        int[] array = {1, 9, 11, -1, 34, 89};
        int index = search(array, 34);
        System.out.printf("下标为%d\n", index);
    }

    /**
     * 线性查找
     * @param array 数组
     * @param value 值
     * @return 下标
     */
    public static int search(int[] array, int value) {
        // 按顺序进行查找
        for (int i = 0; i < array.length; i++) {
            // 找到值返回下标
            if (array[i] == value) {
                return i;
            }
        }

        // 未找到则返回-1
        return -1;
    }
}
