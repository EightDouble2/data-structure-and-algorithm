package com.johnny.algorithm.kruskal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author johnnyhao
 *
 * 克鲁斯卡尔算法
 *
 * - 基本思想：按照权值从小到大的顺序选择n-1条边，并保证这n-1条边不构成回路
 * - 具体做法：首先构造一个只含n个顶点的森林，然后依权值从小到大从连通网中选择边加入到森林中，并使森林中不产生回路，直至森林变成一棵树为止
 */
public class Kruskal {

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
        kruskal(graph);
    }

    /**
     * 克鲁斯卡尔算法
     * @param graph 生成地图
     */
    public static void kruskal(Graph graph) {
        // 图对应边的集合
        List<Side> sideData = graph.getSideData();
        // 用于保存已生成最小生成树中的每个顶点在最小生成树中的终点
        int[] vertexEnds = new int[sideData.size()];

        // 用于保存结果
        List<Side> result = new ArrayList<>();
        
        // 将边的集合从小到大排序
        Collections.sort(sideData);

        for (Side side : sideData) {
            // 获取当前边的两个顶点
            int vertex1 = side.getVertex1();
            int vertex2 = side.getVertex2();

            // 获取顶点在当前生成最小生成树中的终点
            int vertexEnd1 = vertexEnd(vertex1, vertexEnds);
            int vertexEnd2 = vertexEnd(vertex2, vertexEnds);

            // 两个顶点的终点不相同，则表示不构成回路
            if (vertexEnd1 != vertexEnd2) {
                // 加入当前生成最小生成树中
                vertexEnds[vertexEnd1] = vertexEnd2;
                // 将当前边加入结果集
                result.add(side);
            }
        }

        // 输出结果
        result.forEach(System.out::println);
    }

    /**
     * 获取顶点在当前生成最小生成树中的终点
     * @param vertex 顶点
     * @param vertexEnds 当前生成最小生成树中每个顶点的终点集合
     * @return 顶点在当前生成最小生成树中的终点
     */
    private static int vertexEnd(int vertex, int[] vertexEnds) {
        while(vertexEnds[vertex] != 0) {
            vertex = vertexEnds[vertex];
        }
        return vertex;
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
     * 边的集合
     */
    private final List<Side> sideData;

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

        // 循环添加边的关系(每条边只统计一次)
        sideData = new ArrayList<>();
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i; j < vertexs.length; j++) {
                if (this.sides[i][j] != 0) {
                    sideData.add(new Side(i, j, this.sides[i][j]));
                }
            }
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

/**
 * 边
 * 因为需要排序，所以实现Comparable接口
 */
@Data
@AllArgsConstructor
class Side implements Comparable<Side> {
    /**
     * 顶点1
     */
    private int vertex1;
    /**
     * 顶点2
     */
    private int vertex2;
    /**
     * 距离
     */
    private int distance;

    @Override
    public int compareTo(Side side) {
        return this.distance - side.distance;
    }
}