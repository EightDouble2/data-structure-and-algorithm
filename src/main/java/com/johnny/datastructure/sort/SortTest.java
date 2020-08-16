package com.johnny.datastructure.sort;

import java.util.Random;

/**
 * @author johnnyhao
 *
 * 排序测试
 */
public class SortTest {

    public static void main(String[] args) {
        int maxSize = 100000;

        int[] array = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            array[i] = new Random().nextInt(maxSize);
        }

        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        BubbleSort.sort(array.clone());
        endTime = System.currentTimeMillis();
        System.out.printf("- 冒泡排序\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        BubbleSort.sortImprove(array.clone());
        endTime = System.currentTimeMillis();
        System.out.printf("  冒泡排序改进\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        SelectionSort.sort(array.clone());
        endTime = System.currentTimeMillis();
        System.out.printf("- 选择排序\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        InsertionSort.sort(array.clone());
        endTime = System.currentTimeMillis();
        System.out.printf("- 插入排序\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        ShellSort.sort(array.clone());
        endTime = System.currentTimeMillis();
        System.out.printf("- 希尔排序\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        QuickSort.sort(array.clone(), 0, array.length - 1);
        endTime = System.currentTimeMillis();
        System.out.printf("- 快速排序\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        MergeSort.sort(array.clone(), new int[array.length], 0, array.length - 1);
        endTime = System.currentTimeMillis();
        System.out.printf("- 归并排序\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        RadixSort.sort(array.clone());
        endTime = System.currentTimeMillis();
        System.out.printf("- 基数排序\t%d\n", endTime - startTime);

        startTime = System.currentTimeMillis();
        RadixSort.sortImprove1(array.clone());
        endTime = System.currentTimeMillis();
        System.out.printf("  基数排序改进\t%d\n", endTime - startTime);
    }
}
