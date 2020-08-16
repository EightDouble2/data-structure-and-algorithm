package com.johnny.datastructure.sort;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 基数排序
 *
 * 基数排序(radix sort)属于"分配式排序"(distribution sort)，又称"桶子法"(bucket sort)或bin sort
 * 顾名思义，它是通过键值的各个位的值，将要排序的元素分配至某些"桶"中，达到排序的作用
 *
 * 基数排序遇到负数会报错，所以一般只用于正数排序
 * 要使基数排序支持负数
 * - 将待排序序列分解成正负两个数组，分别进行基数排序，最后合并
 * - 将所有数据加上一个数转化为正数，最后再减去这个数还原数据
 */
public class RadixSort {

    public static void main(String[] args) {
        // 排序前
        int[] array = {3, 9, -1, 10, -2};
        System.out.println("排序前 " + Arrays.toString(array));

        // 排序后
        sortImprove1(array);
        System.out.println("排序后 " + Arrays.toString(array));
    }

    /**
     * 基数排序
     * @param array 数组
     */
    public static void sort(int[] array) {
        // 数组长度大于1才需要排序
        if (array.length <= 1) {
            return;
        }

        // 得到数组中最大数
        int maxValue = array[0];
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            }
        }

        // 得到数组中最大位数
        int maxDigit = String.valueOf(maxValue).length();

        // 定义一个二维数组，表示十个桶，每个桶就是一个一维数组
        int[][] bucket = new int[10][array.length];

        // 第一个循环控制判断的位数
        for (int i = 0, n = 1; i < maxDigit; i++, n *= 10) {
            // 定义一个一维数组，记录每个桶数据的个数
            int[] bucketIndex = new int[10];

            // 将元素后放入对应桶中
            for (int value : array) {
                // 取出每个元素对应位上的值
                int digitValue = (value) / n % 10;
                // 将此元素放入对应的桶中，并将对应计数器累加
                bucket[digitValue][bucketIndex[digitValue]++] = value;
            }

            // 遍历每一个桶，将桶中的数据放入原数组
            for (int j = 0, index = 0; j < bucketIndex.length; j++) {
                // 将桶中的数据放入原数组
                for (int l = 0; l < bucketIndex[j]; l++) {
                    array[index++] = bucket[j][l];
                }
            }
        }
    }

    /**
     * 基数排序
     * 方法1改进
     * @param array 数组
     */
    public static void sortImprove1(int[] array) {
        // 定义两个数组，分别存放正数和负数
        int[] positive = new int[array.length];
        int[] minus = new int[array.length];

        // 定义两个指针，记录每个数组数据的个数
        int positiveIndex = 0;
        int minusIndex = 0;

        // 分解数组
        for (int value : array) {
            // 正数放入正数数组
            if (value >= 0) {
                positive[positiveIndex++] = value;
            }
            // 负数取绝对值放入负数数组
            else {
                minus[minusIndex++] = Math.abs(value);
            }
        }

        // 去除正负数组中多余的值
        positive = Arrays.copyOfRange(positive, 0, positiveIndex);
        minus = Arrays.copyOfRange(minus, 0, minusIndex);

        // 分别对正负数组排序
        sort(positive);
        sort(minus);

        // 还原序列
        // 负数数组倒序还原成负数
        for (int i = 0; i < minusIndex; i++) {
            array[minusIndex - i - 1] = - minus[i];
        }
        // 正数数组直接还原
        System.arraycopy(positive, 0, array, minusIndex, array.length - minusIndex);
    }

    /**
     * 基数排序
     * 方法2改进
     * @param array 数组
     */
    public static void sortImprove2(int[] array) {
        // 得到数组中最大数和最小数
        int maxValue = array[0];
        int minValue = array[0];
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            }
            if (minValue > value) {
                minValue = value;
            }
        }

        // 定义增量，如果最小数小于0，增量就为最小数的绝对值
        int increment = minValue < 0 ? Math.abs(minValue) : 0;

        // 得到数组中最大位数
        int maxDigit = String.valueOf(maxValue + increment).length();

        // 定义一个二维数组，表示十个桶，每个桶就是一个一维数组
        int[][] bucket = new int[10][array.length];

        // 第一个循环控制判断的位数
        for (int i = 0, n = 1; i < maxDigit; i++, n *= 10) {
            // 定义一个一维数组，记录每个桶数据的个数
            int[] bucketIndex = new int[10];

            // 将元素增量后放入对应桶中
            for (int value : array) {
                // 取出每个元素对应位上的值
                int digitValue = (value + increment) / n % 10;
                // 将此元素放入对应的桶中，并将对应计数器累加
                bucket[digitValue][bucketIndex[digitValue]++] = value + increment;
            }

            // 遍历每一个桶，将桶中的数据放入原数组，并回退增量
            for (int j = 0, index = 0; j < bucketIndex.length; j++) {
                // 将桶中的数据放入原数组，并回退增量
                for (int l = 0; l < bucketIndex[j]; l++) {
                    array[index++] = bucket[j][l] - increment;
                }
            }
        }
    }
}
