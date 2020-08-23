package com.johnny.algorithm.divideandconquer;

/**
 * @author johnnyhao
 *
 * 分治算法
 *
 * - 分解：将原问题分解为若干个规模较小，相互独立，与原问题形式相同的子问题
 * - 解决：若子问题规模较小而容易被解决则直接解，否则递归地解各个子问题
 * - 合并：将各个子问题的解合并为原问题的解
 */
public class DivideAndConquer {

    public static void main(String[] args) {
        towerOfHanoi(5, 'A', 'B', 'C');
    }

    /**
     * 汉诺塔
     * @param num 盘子总数
     * @param a A柱(起始柱)
     * @param b B柱(辅助柱)
     * @param c C柱(目标柱)
     */
    public static void towerOfHanoi(int num, char a, char b, char c) {
        // 只有一个盘
        if (num == 1) {
            System.out.printf("第%d个盘 %s -> %s\n", 1, a, c);
        }
        // 不止一个盘
        // 将所有盘分解成两个部分，最下面的一个盘和上面的所有盘
        else {
            // 上面的所有盘从A柱到B柱，使用C柱作为辅助
            towerOfHanoi(num - 1, a, c, b);
            // 最下边的盘从A柱到C柱
            System.out.printf("第%d个盘 %s -> %s\n", num, a, c);
            // B柱所有盘从B柱到C柱，使用A柱作为辅助
            towerOfHanoi(num - 1, b, a, c);
        }
    }
}
