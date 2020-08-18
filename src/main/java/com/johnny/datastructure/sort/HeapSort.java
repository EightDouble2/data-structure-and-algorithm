package com.johnny.datastructure.sort;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 堆排序
 *
 * - 将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
 * - 将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
 * - 重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序
 */
public class HeapSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sort(array);
        System.out.println("排序后 " + Arrays.toString(array));
    }

    /**
     * 堆排序
     * @param array 数组
     */
    public static void sort(int[] array) {
        // 将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            buildHeap(array, i, array.length);
        }

        // 将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
        // 重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序
        for (int i = array.length - 1; i > 0; i--) {
            int temp = array[i];
            array[i] = array[0];
            array[0] = temp;

            buildHeap(array, 0, i);
        }
    }

    /**
     * 构建大顶堆
     * @param array 数组
     * @param index 表示非叶子节点在数组中索引
     * @param length 待构建数组长度
     */
    public static void buildHeap(int[] array, int index, int length) {
        // 取出当前节点
        int temp = array[index];

        // 从当前节点的左子节点开始遍历
        for (int i = index * 2 + 1; i < length; i = i * 2 + 1) {
            // 存在右子节点，并且左子节点的值小于右子节点的值
            if (i + 1 < length && array[i] < array[i + 1]) {
                // 指向右子节点
                i++;
            }
            // 子节点的值大于父节点的值
            if (array[i] > temp) {
                // 将子节点的值赋值给父节点
                array[index] = array[i];
                // 继续循环比较
                index = i;
            }
            // 否则说明该节点已经是大顶堆，跳出循环
            else {
                break;
            }
        }

        // 循环结束说明该节点已经是子树的最大值
        // 将当前节点的值赋值到调整的位置
        array[index] = temp;
    }
}
