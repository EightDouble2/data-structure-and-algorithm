package com.johnny.algorithm.prim;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * @author johnnyhao
 *
 * 普里姆算法
 *
 * 普利姆(Prim)算法求最小生成树，也就是在包含n个顶点的连通图中，找出只有(n-1)条边包含所有n个顶点的连通子图，也就是所谓的极小连通子图
 *
 * - 设G=(V,E)是连通网，T=(U,D)是最小生成树，V,U是顶点集合，E,D是边的集合
 * - 若从顶点u开始构造最小生成树，则从集合V中取出顶点u放入集合U中，标记顶点v的visited[u]=true
 * - 若集合U中顶点ui与集合V-U中的顶点vj之间存在边，则寻找这些边中权值最小的边，但不能构成回路，将顶点vj加入集合U中，将边(ui,vj)加入集合D中，标记visited[vj]=1
 * - 重复步骤2，直到U与V相等，即所有顶点都被标记为访问过，此时D中有n-1条边
 */
public class Prim {

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
        prim(graph, 0);
    }

    /**
     * 普里姆算法
     * @param graph 生成地图
     * @param startVertex 起始顶点
     */
    public static void prim(Graph graph, int startVertex) {
        // 顶点集合
        List<String> vertexList = graph.getVertexList();
        // 图对应邻接矩阵
        int[][] sides = graph.getSides();

        // 记录节点是否已访问
        boolean[] isVisited = new boolean[vertexList.size()];
        // 将当前节点标记为已访问
        isVisited[startVertex] = true;

        // 从下一个节点开始，遍历所有节点
        for (int i = 1; i < vertexList.size(); i++) {
            // 记录找到最短路径的起点与终点节点
            int vertex1 = 0;
            int vertex2 = 0;
            // 记录当前遍历的最短距离
            int minDistance = 0;

            // 遍历已经访问的节点
            for (int j = 0; j < vertexList.size(); j++) {
                // 遍历没有访问的节点
                for (int k = 0; k < vertexList.size(); k++) {
                    // 过滤j为已经访问的节点，k为没有访问的节点
                    // 当前找到的路径小于找到的最短路径，将最短路径记录为当前路径
                    boolean flag = isVisited[j] && !isVisited[k] && sides[j][k] > 0 && (minDistance == 0 || sides[j][k] < minDistance);
                    if (flag) {
                        minDistance = sides[j][k];
                        vertex1 = j;
                        vertex2 = k;
                    }
                }
            }

            // 找到一条最小的边，将找到的节点标记为已访问
            isVisited[vertex2] = true;
            System.out.printf("边<%s, %s> 距离:%d\n", vertexList.get(vertex1), vertexList.get(vertex2), minDistance);
        }
    }
}

/**
 * 图
 */
@Getter
@Setter
class Graph {
    /**
     * 顶点集合
     */
    private List<String> vertexList;

    /**
     * 图对应邻接矩阵
     */
    private int[][] sides;

    /**
     * 构造器
     * @param vertexs 顶点
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
