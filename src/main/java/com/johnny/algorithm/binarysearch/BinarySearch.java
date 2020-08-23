package com.johnny.algorithm.binarysearch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author johnnyhao
 *
 * 二分查找
 *
 * 二分查找的数组必须是顺序的
 * 与数组中间值进行比较，如果小则向左侧继续进行二分查找，如果大则向右侧继续进行二分查找，如果相等，则查找成功
 */
public class BinarySearch {

    public static void main(String[] args) {
        int maxSize = 100;
        int[] array = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            array[i] = i;
        }
        array[33] = 34;
        array[35] = 34;
        array[36] = 34;

        int index = searchWithNoRecursion(array, 34);
        System.out.printf("二分查找(非递归方式)下标为%d\n", index);

        index = searchWithRecursion(array, 34, 0, array.length - 1);
        System.out.printf("二分查找(递归方式)下标为%d\n", index);

        List<Integer> indexImprove = searchWithRecursionImprove(array, 34, 0, array.length - 1);
        System.out.printf("二分查找(递归方式)所有下标为%s\n", indexImprove);
    }

    /**
     * 二分查找(非递归方式)
     * @param array 数组
     * @param value 值
     * @return 下标
     */
    public static int searchWithNoRecursion(int[] array, int value) {
        // 定义左侧索引和右侧索引
        int left = 0;
        int right = array.length - 1;

        // 利用循环查找
        while (left <= right) {
            // 中间索引
            int mid = (left + right) / 2;
            // 值小于中间值，则向左侧继续进行二分查找
            if (value < array[mid]) {
                right = mid - 1;
            }
            // 值大于中间值，则向右侧继续进行二分查找
            else if (value > array[mid]) {
                left = mid + 1;
            }
            // 找到值，返回下标
            else  {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 二分查找(递归方式)
     * @param array 数组
     * @param value 值
     * @param left 左侧索引
     * @param right 右侧索引
     * @return 下标
     */
    public static int searchWithRecursion(int[] array, int value, int left, int right) {
        // 左侧索引大于右侧索引则表示没有找到值
        if (left > right) {
            return -1;
        }

        // 中间索引和值
        int mid = (left + right) / 2;
        int midValue = array[mid];

        // 值小于中间值，则向左侧继续进行二分查找
        if (value < midValue) {
            return searchWithRecursion(array, value, left, mid - 1);
        }
        // 值大于中间值，则向右侧继续进行二分查找
        else if (value > midValue) {
            return searchWithRecursion(array, value, mid + 1, right);
        }
        // 找到值，返回下标
        else {
            return mid;
        }
    }

    /**
     * 二分查找(递归方式)
     * 找到所有下标
     * @param array 数组
     * @param value 值
     * @param left 左侧索引
     * @param right 右侧索引
     * @return 所有下标
     */
    public static List<Integer> searchWithRecursionImprove(int[] array, int value, int left, int right) {
        // 递归方法与普通二分查找相同
        if (left > right) {
            return null;
        }

        int mid = (left + right) / 2;
        int midValue = array[mid];

        if (value < midValue) {
            return searchWithRecursionImprove(array, value, left, mid - 1);
        }
        else if (value > midValue) {
            return searchWithRecursionImprove(array, value, mid + 1, right);
        }
        // 找到值，不马上返回下标，将下标存入集合中，继续查找
        else {
            List<Integer> index = new ArrayList<>();
            index.add(mid);

            // 向左查找，直到找到不相等的值
            for (int i = mid - 1; i > left; i--) {
                if (array[i] != value) {
                    break;
                }
                index.add(i);
            }

            // 向右查找，直到找到不相等的值
            for (int i = mid + 1; i < right; i++) {
                if (array[i] != value) {
                    break;
                }
                index.add(i);
            }

            return index;
        }
    }
}
