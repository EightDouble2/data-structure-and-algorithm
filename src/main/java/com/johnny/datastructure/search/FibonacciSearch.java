package com.johnny.datastructure.search;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 斐波纳契查找
 *
 * 斐波那契数列{1, 1, 2, 3, 5, 8, 13, 21, 34, 55}发现斐波那契数列的两个相邻数的比例，无限接近黄金分割值0.618
 *
 * - 由斐波那契数列F[k]=F[k-1]+F[k-2]的性质，可以得到(F[k]-1)=(F[k-1]-1)+(F[k-2]-1)+1
 *   该式说明：
 *   只要顺序表的长度为F[k]-1，则可以将该表分成长度为F[k-1]-1和F[k-2]-1的两段，从而中间位置为mid=low+F(k-1)-1
 * - 对每个子段也可以用相同的方式分割
 * - 但顺序表长度n不一定刚好等于F[k]-1，所以需要将原来的顺序表长度n增加至F[k]-1
 *   这里的k值只要能使得F[k]-1恰好大于或等于n即可，新增的位置(从n+1到F[k]-1位置)，都赋为n位置的值即可
 */
public class FibonacciSearch {

    public static void main(String[] args) {
        int maxSize = 100;
        int[] array = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            array[i] = i;
        }

        int index = search(array, 34);
        System.out.printf("下标为%d\n", index);
    }

    /**
     * 生成斐波那契数列
     * @return 斐波那契数列
     */
    public static int[] getFibonacci(int[] array) {
        int[] fibonacci = new int[array.length];
        fibonacci[0] = 1;
        fibonacci[1] = 1;
        int i = 2;
        while(array.length - 1 > fibonacci[i - 1] - 1) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
            i++;
        }

        return Arrays.copyOfRange(fibonacci, 0, i);
    }

    /**
     * 斐波纳契查找
     * @param array 数组
     * @param value 值
     * @return 下标
     */
    public static int search(int[] array, int value) {
        // 初始化左右侧索引
        int left = 0;
        int right = array.length - 1;

        // 初始化斐波拉契数列和索引
        int[] fibonacci = getFibonacci(array);
        int f = fibonacci.length - 1;

        // 因为fibonacci[f]值可能大于array的长度，因此我们需要使用Arrays类，构造一个新的数组，并指向temp[]
        // 不足的部分会使用0填充
        int[] temp = Arrays.copyOf(array, fibonacci[f]);

        // 将新的数组不足部分用数组最后元素的值填充
        for(int i = right + 1; i < temp.length; i++) {
            temp[i] = array[right];
        }

        // 左侧索引大于右侧索引则表示没有找到值
        while (left <= right) {
            // 中间索引和值
            int mid = left + fibonacci[f - 1] - 1;
            int midValue = temp[mid];

            // 值小于中间值，则向左侧继续进行查找
            // 左侧有fibonacci[f - 1]个元素，可以继续分解成fibonacci[f - 1] = fibonacci[f - 2] + fibonacci[f - 3]
            // 所以下次查找(f-1)
            if (value < midValue) {
                right = mid - 1;
                f--;
            }
            // 值大于中间值，则向右侧继续进行查找
            // 左侧有fibonacci[f - 2]个元素，可以继续分解成fibonacci[f - 2] = fibonacci[f - 3] + fibonacci[f - 4]
            // 所以下次查找(f-2)
            else if (value > midValue) {
                left = mid + 1;
                f -= 2;
            }
            // 找到值，返回下标
            else {
                // 判断找到的下标是否是之前填充的元素，是则返回右侧索引，否则返回找到的下标
                return Math.min(mid, right);
            }
        }

        // 未找到则返回-1
        return -1;
    }
}
