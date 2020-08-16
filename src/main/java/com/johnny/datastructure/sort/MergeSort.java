package com.johnny.datastructure.sort;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 归并排序
 *
 * 利用归并的思想实现的排序方法，该算法采用经典的分治(divide-and-conquer)策略
 * 分治法将问题分(divide)成一些小的问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之
 */
public class MergeSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sort(array, new int[array.length], 0, array.length - 1);
        System.out.println("排序后 " + Arrays.toString(array));
    }

    /**
     * 归并排序-分解
     * @param array 数组
     * @param temp 临时数组
     * @param left 序列最左侧下标
     * @param right 序列最右侧下标
     */
    public static void sort(int[] array, int[] temp, int left, int right) {
        // 如果序列最左侧下标小于序列最右侧下标，进行分解
        if (left < right) {
            // 中间位置下标
            int mid = (left + right) / 2;
            //向左递归进行分解
            sort(array, temp, left, mid);
            //向右递归进行分解
            sort(array, temp, mid + 1, right);

            // 合并
            merge(array, temp, left, right);
        }
    }

    /**
     * 归并排序-合并
     * @param array 数组
     * @param temp 临时数组
     * @param left 序列最左侧下标
     * @param right 序列最右侧下标
     */
    public static void merge(int[] array, int[] temp, int left, int right) {
        // 中间位置下标
        int mid = (left + right) / 2;
        // 对左侧和右侧数组分别初始化指针
        int l = left;
        int r = mid + 1;

        // 对临时数组初始化指针
        int t = 0;

        // 先把左右两边(有序)的数据按照规则填充到temp数组
        // 直到左右两边的有序序列，有一边处理完毕为止
        while (l <= mid && r <= right) {
            // 如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素
            // 即将左边的当前元素，填充到临时数组，然后t++，l++
            if(array[l] <= array[r]) {
                temp[t++] = array[l++];
            }
            // 反之,将右边有序序列的当前元素，填充到临时数组
            else {
                temp[t++] = array[r++];
            }
        }

        // 把有剩余数据的一边的数据依次全部填充到临时数组
        // 左边的有序序列还有剩余的元素，就全部填充到临时数组
        while (l <= mid) {
            temp[t++] = array[l++];
        }
        // 右边的有序序列还有剩余的元素，就全部填充到临时数组
        while (r <= right) {
            temp[t++] = array[r++];
        }

        // 将临时数组的元素拷贝到原数组
        // 注意，并不是每次都拷贝所有
        t = 0;
        l = left;
        while (l <= right) {
            array[l++] = temp[t++];
        }

    }
}
