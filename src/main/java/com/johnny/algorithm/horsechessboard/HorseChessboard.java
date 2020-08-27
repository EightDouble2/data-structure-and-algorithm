package com.johnny.algorithm.horsechessboard;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author johnnyhao
 *
 * 马踏棋盘算法
 *
 * 马踏棋盘问题(骑士周游问题)实际上是图的深度优先搜索(DFS)的应用
 * 可以使用贪心算法，每次选择最优的步骤，减少回溯的次数，达到优化的效果
 */
public class HorseChessboard {
    /**
     * 标记是否成功
     */
    private static boolean finished;

    public static void main(String[] args) {
        // 初始化棋盘
        int[][] chessboard = new int[8][8];

        // 初始化标记
        finished = false;

        // 马踏棋盘算法
        horseChessboard(chessboard, new Point(0, 0), 1);

        // 输出结果
        for (int[] rows : chessboard) {
            for (int s : rows) {
                System.out.printf("%d\t", s);
            }
            System.out.println();
        }
    }

    /**
     * 马踏棋盘算法
     * @param chessboard 棋盘
     * @param curPoint 当前位置
     * @param step 当前步数
     */
    private static void horseChessboard(int[][] chessboard, Point curPoint, int step) {
        // 设置当前位置步数
        chessboard[curPoint.x][curPoint.y] = step;

        // 获取下一步可以走的位置集合
        List<Point> nextPoints = nextPoint(chessboard, curPoint);

        // 贪心算法优化
        nextPoints.sort(Comparator.comparingInt(o -> nextPoint(chessboard, o).size()));

        // 遍历每一步
        nextPoints.forEach(nextPoint -> {
            // 如果没有走过则继续继续递归
            if (chessboard[nextPoint.x][nextPoint.y] == 0) {
                horseChessboard(chessboard, nextPoint, step + 1);
            }
        });

        // 所有位置走到则成功，否则回溯
        if (step < chessboard.length * chessboard[0].length && !finished) {
            chessboard[curPoint.x][curPoint.y] = 0;
        }
        else {
            finished = true;
        }
    }

    /**
     * 获取下一步可以走的位置集合
     * @param chessboard 棋盘
     * @param curPoint 当前位置
     * @return 下一步可以走的位置集合
     */
    private static List<Point> nextPoint(int[][] chessboard, Point curPoint) {
        List<Point> nextPoints = new ArrayList<>();

        int[] x = new int[]{curPoint.x - 2, curPoint.x - 1, curPoint.x + 1, curPoint.x + 2};
        int[] y = new int[]{curPoint.y - 2, curPoint.y - 1, curPoint.y + 1, curPoint.y + 2};

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y.length; j++) {
                // 判断下一步可以走并且没有走过的位置
                boolean flag = (((i == 0 || i == 3) && (j == 1 || j == 2)) || ((i == 1 || i == 2) && (j == 0 || j == 3)))
                        && x[i] >= 0 && x[i] < chessboard.length && y[j] >= 0 && y[j] < chessboard[i].length;
                if (flag) {
                    nextPoints.add(new Point(x[i], y[j]));
                }
            }
        }

        return nextPoints;
    }
}
