package com.johnny.datastructure.recursion;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * 八皇后问题
 */
public class EightQueen {

    /**
     * 棋盘大小
     */
    private static final int MAX_SIZE = 8;

    /**
     * 结果计数器
     */
    private static int resultCount = 0;

    /**
     * 初始化棋盘
     */
    private static final int[] CHESS_BOARD = new int[MAX_SIZE];

    public static void main(String[] args) {
        // 从第一个位置开始放置皇后
        place(0);
    }

    /**
     * 放置皇后
     * 此处递归内有循环，当判断冲突失败会继续循环，形成回溯
     * @param n 放置第n行皇后
     */
    public static void place(int n) {
        // 判断皇后是否已经放完
        if (n == MAX_SIZE) {
            // 皇后已经放置完，则表示成功，显示结果
            System.out.printf("第%d种结果\n", ++resultCount);
            show();
        }
        // 如果皇后没有放完则继续放置皇后
        else {
            // 循环每一列放置皇后，并判断与之前的皇后是否冲突
            for (int i = 0; i < MAX_SIZE; i++) {
                CHESS_BOARD[n] = i;
                // 如果不冲突则继续递归放置下一个皇后
                if (verify(n)) {
                    place(n + 1);
                }
                // 如果冲突则继续放置皇后到下一列，此处形成回溯
            }
        }
    }

    /**
     * 校验当前皇后是否与前面的皇后冲突
     * @param n 第n个皇后
     * @return 返回true成功
     */
    public static boolean verify(int n) {
        // 遍历当前皇后之前的皇后
        for (int i = 0; i < n; i++) {
            // 如果存在同一列或者同一斜线的皇后则返回失败
            // 因为每行只放置一个皇后，所以不用判断行是否冲突
            if (CHESS_BOARD[n] == CHESS_BOARD[i] || Math.abs(n - i) == Math.abs(CHESS_BOARD[n] - CHESS_BOARD[i])) {
                return false;
            }
        }
        // 跳出循环则成功
        return true;
    }

    /**
     * 显示棋盘
     */
    public static void show() {
        System.out.println(Arrays.toString(CHESS_BOARD));
        for (int i = MAX_SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < MAX_SIZE; j++) {
                if (j == CHESS_BOARD[i]) {
                    System.out.print("*\t");
                }
                else {
                    System.out.print("-\t");
                }
            }
            System.out.print("\n");
        }
    }
}
