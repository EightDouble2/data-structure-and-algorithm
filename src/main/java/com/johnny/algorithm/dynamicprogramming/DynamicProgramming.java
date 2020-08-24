package com.johnny.algorithm.dynamicprogramming;

/**
 * @author johnnyhao
 *
 * 动态规划算法
 *
 * - 动态规划算法的核心思想是：将大问题划分为小问题进行解决，从而一步步获取最优解的处理算法
 * - 动态规划算法与分治算法类似，其基本思想也是将待求解问题分解成若干个子问题，先求解子问题，然后从这些子问题的解得到原问题的解
 * - 与分治法不同的是，适合于用动态规划求解的问题，经分解得到子问题往往不是互相独立的(即下一个子阶段的求解是建立在上一个子阶段的解的基础上，进行进一步的求解)
 * - 动态规划可以通过填表的方式来逐步推进，得到最优解
 */
public class DynamicProgramming {

    public static void main(String[] args) {
        // 物品重量
        int[] weight = {1, 4, 3};
        // 物品价值
        int[] value = {1500, 3000, 2000};
        // 背包容量
        int volume = 4;

        knapsackProblem(weight, value, volume);
    }

    /**
     * 背包问题
     * 当前问题属于01背包，即每个物品最多放一个；而无限背包可以转化为01背包
     * - 每次遍历到的第i个物品，根据w[i]和v[i]来确定是否需要将该物品放入背包中
     *   即对于给定的n个物品，设v[i]、w[i]分别为第i个物品的价值和重量，C为背包的容量
     *   再令v[i][j]表示在前i个物品中能够装入容量为j的背包中的最大价值
     * @param weight 物品重量
     * @param value 物品价值
     * @param volume 背包容量
     */
    public static void knapsackProblem(int[] weight, int[] value, int volume) {
        // 创建二维数组表示之前i个物品中能装入容量j的背包中的最大价值(首行首列价值重量为0)
        int[][] total = new int[value.length + 1][volume + 1];
        // 创建二维数组表示是否放入当前物品
        int[][] goods = new int[value.length + 1][volume + 1];

        // 不处理第一行第一列，动态规划处理填表
        for (int i = 1; i < total.length; i++) {
            for (int j = 1; j < total[0].length; j++) {
                // 当前背包容量小于当前物品的重量，则直接使用上个单元格装入的策略
                if (j < weight[i - 1]) {
                    total[i][j] = total[i - 1][j];
                }
                // 当前背包容量不小于当前物品的重量，比较上个单元格装入的价值和放入当前物品后装入的最大价值
                // total[i - 1][j]：上个单元格装入的价值
                // value[i - 1]：当前物品价值
                // total[i - 1][j - weight[i - 1]：放入当前物品后剩余空间装入的最大价值
                else {
                    // 上个单元格装入的价值小于放入当前物品后装入的最大价值，则放入当前物品
                    if (total[i - 1][j] < value[i - 1] + total[i - 1][j - weight[i - 1]]) {
                        total[i][j] = value[i - 1] + total[i - 1][j - weight[i - 1]];
                        // 记录当前放入物品的情况
                        goods[i][j] = 1;
                    }
                    // 上个单元格装入的价值不小于放入当前物品后装入的最大价值，则直接使用上个单元格装入的策略
                    else {
                        total[i][j] = total[i - 1][j];
                    }
                }
            }
        }

        System.out.println("动态规划表");
        for (int[] t : total) {
            for (int i : t) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        System.out.println("放入当前物品表");
        for (int[] t : goods) {
            for (int i : t) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        System.out.println("从最后遍历放入背包的物品");
        for (int i = goods.length - 1, j = goods[0].length - 1; i > 0 && j > 0 ; i--) {
            if(goods[i][j] == 1) {
                System.out.printf("第%d个物品放入到背包\n", i);
                j -= weight[i-1];
            }
        }
    }
}
