package com.johnny.datastructure.sparsearray;

/**
 * @author johnnyhao
 *
 * 稀疏数组
 *
 * 当一个数组大部分元素为0或者为同一个值时，可以转换稀疏数组来保存数组，从而节省空间
 * 稀疏数组的处理方法是：
 * - 记录数组一共有几行几列，有多少个不同的值
 * - 把具有不同值的元素的行列以及值记录在一个小规模的数组中
 */
public class SparseArray {

    public static void main(String[] args) {

        // 保存棋盘问题
        // 创建一个原始的二维数组表示棋盘
        // 0：表示没有棋子，1表示白子，2表示黑子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;

        System.out.println("原始二维数组：");
        for (int [] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // 将二维数组转稀疏数组
        // 遍历二维数组取得非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }

        // 创建稀疏数组
        int[][] sparseArr = new int[sum + 1][3];

        // 给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值存入稀疏数组
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }

        // 输出稀疏数组
        System.out.println("稀疏数组：");
        for (int [] row : sparseArr) {
            System.out.printf("%d\t%d\t%d\t\n", row[0], row[1], row[2]);
        }

        // 稀疏数组转二维数组
        // 读取稀疏数组第一行，创建原始的二维数组
        int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];

        // 遍历稀疏数组，赋值给原始二维数组
        for (int i = 1; i < sparseArr.length; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        // 输出恢复后的二维数组
        System.out.println("恢复后的二维数组：");
        for (int [] row : chessArr2) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
