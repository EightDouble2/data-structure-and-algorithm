package com.johnny.datastructure.recursion;

/**
 * @author johnnyhao
 *
 * 迷宫问题
 */
public class Maze {

    /**
     * 地图
     */
    private static final int[][] MAP = new int[8][7];

    public static void main(String[] args) {

        // 初始化地图
        System.out.println("初始化地图");
        initMap();
        show();

        // 从[1][1]开始
        if (walk(1, 1)) {
            System.out.println("成功");
        }
        else {
            System.out.println("失败");
        }

        // 显示结果
        show();
    }

    /**
     * 初始化地图
     * * * * * * * *
     * * s         *
     * *           *
     * * * * *     *
     * *           *
     * *           *
     * *         e *
     * * * * * * * *
     */
    public static void initMap() {

        // 设置墙
        for (int i = 0; i < 7; i++) {
            MAP[0][i] = -1;
            MAP[7][i] = -1;
        }
        for (int i = 0; i < 8; i++) {
            MAP[i][0] = -1;
            MAP[i][6] = -1;
        }

        // 设置挡板
        MAP[3][1] = -1;
        MAP[3][2] = -1;
        MAP[3][3] = -1;
    }

    /**
     * 显示地图
     */
    public static void show() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(MAP[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 走迷宫
     * @param i 行
     * @param j 列
     * @return 是否到达终点
     */
    public static boolean walk(int i, int j) {
        // 判断是否成功
        if (MAP[6][5] == 1) {
            return true;
        }
        // 判断当前点是否走过
        else if (MAP[i][j] == 0) {
            // 假定当前点是通路
            MAP[i][j] = 1;

            // 按照策略 下->右->上->左
            // if (walk(i + 1, j) || walk(i, j + 1) || walk(i - 1, j) || walk(i, j - 1)) {
            //     return true;
            // }
            // 按照策略 上->左->下->右
            if (walk(i - 1, j) || walk(i, j - 1) || walk(i + 1, j) || walk(i, j + 1)) {
                return true;
            }
            // 走不通则失败
            else {
                MAP[i][j] = 2;
                return false;
            }
        }
        // 直接失败
        else {
            return false;
        }
    }
}
