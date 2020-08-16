package com.johnny.datastructure.search;

/**
 * @author johnnyhao
 *
 * 插值查找
 *
 * 插值查找算法类似于二分查找，不同的是插值查找每次从自适应mid处开始查找
 *
 * 对于数据量较大，关键字分布比较均匀的查找表来说，采用插值查找, 速度较快
 * 关键字分布不均匀的情况下，该方法不一定比折半查找要好
 */
public class InterpolationSearch {

    public static void main(String[] args) {
        int maxSize = 100;
        int[] array = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            array[i] = i;
        }

        int index = search(array, 34, 0, array.length - 1);
        System.out.printf("下标为%d\n", index);
    }

    /**
     * 插值查找
     * @param array 数组
     * @param value 值
     * @param left 左侧索引
     * @param right 右侧索引
     * @return 下标
     */
    public static int search(int[] array, int value, int left, int right) {
        // 左侧索引大于右侧索引则表示没有找到值
        // 插值查找必须判断查找值是否越界
        if (left > right || value < array[0] || value > array[array.length - 1]) {
            return -1;
        }

        // 通过插值算法找出中间索引和值
        int mid = left + (right - left) * (value - array[left]) / (array[right] - array[left]);
        int midValue = array[mid];

        // 值小于中间值，则向左侧继续进行插值查找
        if (value < midValue) {
            return search(array, value, left, mid - 1);
        }
        // 值大于中间值，则向右侧继续进行插值查找
        else if (value > midValue) {
            return search(array, value, mid + 1, right);
        }
        // 找到值，返回下标
        else {
            return mid;
        }
    }
}
