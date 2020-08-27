package com.johnny.algorithm.floyd;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author johnnyhao
 *
 * 弗洛伊德算法
 *
 * - 弗洛伊德算法(Floyd)用于计算各个顶点之间的最短路径
 *   迪杰斯特拉算法用于计算某一个顶点到其他顶点的最短路径
 * - 迪杰斯特拉算法通过选定的被访问顶点，求出从出发访问顶点到其他顶点的最短路径
 *   弗洛伊德算法中每一个顶点都是出发访问点，所以需要将每一个顶点看做被访问顶点，求出从每一个顶点到其他顶点的最短路径
 */
public class Floyd {

    public static void main(String[] args) {
        // 顶点
        String[] vertexs = {"A", "B", "C", "D", "E", "F", "G"};
        // 边关系
        int[][] sides = {{0, 1, 5}, {0, 2, 7}, {0, 6, 2}, {1, 3, 9}, {1, 6, 3}, {2, 4, 8}, {3, 5, 4}, {4, 5, 5}, {4, 6, 4}, {5, 6, 6}};

        // 初始化图
        Graph graph = new Graph(vertexs, sides);

        // 显示图邻接矩阵
        graph.show();

        // 找到最短路径
        floyd(graph);
    }

    /**
     * 弗洛伊德算法
     * @param graph 生成地图
     */
    private static void floyd(Graph graph) {
        // 顶点集合
        List<String> vertexList = graph.getVertexList();

        // 记录各个顶点到其他顶点的距离，初始化为邻接矩阵
        int[][] distance = graph.getSides();
        // 记录前一顶点的下标
        int[][] preVertexs = new int[vertexList.size()][vertexList.size()];
        // 初始化每一个顶点的前一顶点为当前顶点自己
        for (int i = 0; i < preVertexs.length; i++) {
            Arrays.fill(preVertexs[i], i);
        }

        // 循环每一个顶点作为中间顶点
        for (int i = 0; i < distance.length; i++) {
            // 循环每一个顶点作为起始顶点
            for (int j = 0; j < distance.length; j++) {
                // 循环每一个顶点作为目标顶点
                for (int k = 0; k < distance.length; k++) {
                    // 排除起始顶点与目标顶点是同一顶点，中间顶点与起始顶点和目标顶点不能联通的情况
                    if (j != k && distance[i][j] != 0 && distance[i][k] != 0) {
                        // 如果起始顶点经过中间顶点到目标顶点的距离比起始顶点直接到目标顶点的距离短，或起始顶点与目标顶点不能联通
                        if (distance[i][j] + distance[i][k] < distance[j][k] || distance[j][k] == 0) {
                            // 更新起始顶点到目标顶点的距离
                            distance[j][k] = distance[i][j] + distance[i][k];
                            // 更新目标顶点的前一顶点为中间顶点
                            preVertexs[j][k] = preVertexs[i][k];
                        }
                    }
                }
            }
        }

        // 输出结果
        // 循环每一个顶点
        for (int i = 0; i < distance.length; i++) {
            // 输出目标顶点的前一顶点
            for (int j = 0; j < distance.length; j++) {
                System.out.print(vertexList.get(preVertexs[i][j]));
            }
            System.out.println();
            // 输出起始顶点到目标顶点最短距离
            for (int j = 0; j < distance.length; j++) {
                System.out.printf("%s到%s最短距离为%d ", vertexList.get(i), vertexList.get(j), distance[i][j]);
            }
            System.out.println();
        }
    }
}

/**
 * 图
 */
@Getter
class Graph {
    /**
     * 顶点集合
     */
    private final List<String> vertexList;

    /**
     * 图对应邻接矩阵
     */
    private final int[][] sides;

    /**
     * 构造器
     * @param vertexs 顶点
     * @param sides 边关系
     */
    public Graph(String[] vertexs, int[][] sides) {
        // 顶点集合和对应邻接矩阵
        this.sides = new int[vertexs.length][vertexs.length];
        vertexList = Arrays.asList(vertexs);
        // 循环添加边
        for (int[] side : sides) {
            this.sides[side[0]][side[1]] = side[2];
            this.sides[side[1]][side[0]] = side[2];
        }
    }

    /**
     * 显示邻接矩阵
     */
    public void show() {
        for (int[] side : sides) {
            System.out.println(Arrays.toString(side));
        }
    }
}
