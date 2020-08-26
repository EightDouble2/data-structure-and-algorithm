package com.johnny.algorithm.dijkstra;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author johnnyhao
 *
 * 迪杰斯特拉算法
 *
 * 迪杰斯特拉(Dijkstra)算法是典型最短路径算法，用于计算一个结点到其他结点的最短路径
 * 它的主要特点是以起始点为中心向外层层扩展(广度优先搜索思想)，直到扩展到终点为止
 */
public class Dijkstra {

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
        dijkstra(graph, 0);
        dijkstra(graph, 6);
    }

    /**
     * 迪杰斯特拉算法
     * @param graph 生成地图
     * @param startVertex 起始顶点
     */
    private static void dijkstra(Graph graph, int startVertex) {
        // 顶点集合
        List<String> vertexList = graph.getVertexList();
        // 图对应邻接矩阵
        int[][] sides = graph.getSides();

        // 记录节点是否已访问
        boolean[] isVisited = new boolean[vertexList.size()];
        // 记录前一节点的下标
        int[] preVertexs = new int[vertexList.size()];
        // 记录出发顶点到其他顶点的距离
        int[] distance = new int[vertexList.size()];

        // 起始顶点标记为已访问
        isVisited[startVertex] = true;
        // 起始顶点的距离为零
        distance[startVertex] = 0;

        // 当前访问顶点
        int index = startVertex;

        // 遍历每一个顶点
        for (int i = 0; i < vertexList.size(); i++) {
            // 遍历邻接矩阵的当前访问顶点行，获取当前访问顶点到其余顶点的距离
            for (int j = 0; j < sides[index].length; j++) {
                // 判断遍历到的顶点是否已经访问，当前访问顶点是否能与当前遍历顶点联通
                if (!isVisited[j] && sides[index][j] != 0) {
                    // 当前遍历顶点还未联通
                    // 或者当前访问顶点到起始顶点的距离与当前访问顶点到当前遍历顶点的距离之和比当前遍历顶点到起始顶点的距离小
                    if (distance[index] + sides[index][j] < distance[j] || distance[j] == 0) {
                        // 更新当前遍历顶点的前一个顶点为当前访问顶点
                        preVertexs[j] = index;
                        // 更新起始顶点与当前遍历顶点的距离
                        distance[j] = distance[index] + sides[index][j];
                    }
                }
            }

            // 找到能与起始点联通且未访问的顶点中与起始顶点距离最短的顶点
            int min = 0;
            for(int j = 0; j < isVisited.length; j++) {
                // 能与起始点联通且未访问的顶点
                if (!isVisited[j] && distance[j] != 0) {
                    // 判断当前遍历的顶点是否为与起始顶点距离最短的顶点
                    if(distance[j] < min || min == 0) {
                        // 记录下遍历中的最短距离
                        min = distance[j];
                        // 当前访问顶点设置为找到的顶点
                        index = j;
                    }
                }
            }

            // 当前访问顶点标记为已访问
            isVisited[index] = true;
        }

        // 打印结果
        for (int i = 0; i < distance.length; i++) {
            System.out.print(vertexList.get(i) + "("+distance[i]+") ");
        }

        System.out.println();

        for (int i = 0; i < distance.length; i++) {
            index = i;
            while (index != startVertex) {
                System.out.print(vertexList.get(index) + "<-");
                index = preVertexs[index];
            }
            System.out.println(vertexList.get(index));
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
