package com.johnny.datastructure.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author johnnyhao
 *
 * 图
 *
 * 图是一种数据结构，其中节点可以具有零个或多个相邻元素，两个节点之间的连接称为边
 *
 * 图的表示方式有两种
 * - 邻接矩阵是表示图形中顶点之间相邻关系的矩阵，对于n个顶点的图而言，矩阵是的行和列表示的是1...n个顶点
 *   邻接矩阵需要为每个顶点都分配n个边的空间，其实有很多边都是不存在,会造成空间的一定损失
 * - 邻接表的实现只关心存在的边，不关心不存在的边。因此没有空间浪费，邻接表由数组和链表组成
 * 
 * 深度优先搜索
 * - 从初始访问节点出发，初始访问节点可能有多个邻接节点，深度优先遍历的策略就是首先访问第一个邻接节点，然后再以这个被访问的邻接节点作为初始节点，访问它的第一个邻接节点，可以这样理解：
 *   每次都在访问完当前节点后首先访问当前节点的第一个邻接节点
 * - 我们可以看到，这样的访问策略是优先往纵向挖掘深入，而不是对一个节点的所有邻接节点进行横向访问
 *
 * 广度优先搜索
 * - 类似于一个分层搜索的过程，广度优先遍历需要使用一个队列以保持访问过的结点的顺序，以便按这个顺序来访问这些结点的邻接结点
 */
public class Graph {

    public static void main(String[] args) {
        // 顶点
        String[] vertexs = {"A", "B", "C", "D", "E", "F", "G", "H"};
        // 边关系 A-B A-C B-C B-D B-E
        int[][] sides = {{0, 1, 1}, {0, 2, 1}, {1, 3, 1}, {1, 4, 1}, {3, 7, 1}, {4, 7, 1}, {2, 5, 1}, {2, 6, 1}, {5, 6, 1}};

        // 初始化图
        GraphImpl graph = new GraphImpl(vertexs, sides);

        // 显示图邻接矩阵
        graph.show();

        // 深度优先搜索
        graph.depthFirstSearch();

        System.out.println();

        // 广度优先搜索
        graph.breadthFirstSearch();
    }
}

/**
 * 图
 */
class GraphImpl {
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
     */
    public GraphImpl(String[] vertexs, int[][] sides) {
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

    /**
     * 找到下一个邻接节点
     * @param i 下标
     * @param j 下标
     * @return 下标，未找到返回-1
     */
    private int neighbor(int i, int j) {
        while (j < vertexList.size()) {
            if (sides[i][j] > 0) {
                return j;
            }
            j++;
        }
        return -1;
    }

    /**
     * 深度优先搜索
     */
    public void depthFirstSearch() {
        // 节点是否被访问
        boolean[] isVisited = new boolean[vertexList.size()];

        // 遍历所有的节点，进行深度优先搜索
        for(int i = 0; i < vertexList.size(); i++) {
            if(!isVisited[i]) {
                depthFirstSearch(isVisited, i);
            }
        }
    }

    /**
     * 深度优先搜索
     * @param isVisited 节点是否被访问
     * @param i 当前节点下标
     */
    private void depthFirstSearch(boolean[] isVisited, int i) {
        // 输出当前节点
        System.out.printf("%s ", vertexList.get(i));
        // 将节点设置为已经访问
        isVisited[i] = true;
        // 查找当前节点的第一个邻接节点w
        int w = neighbor(i, 0);
        // 找到第一个邻接节点
        while (w != -1) {
            // 如果w节点没有被访问过
            if (!isVisited[w]) {
                depthFirstSearch(isVisited, w);
            }
            // 获取下一个邻接节点
            w = neighbor(i, w + 1);
        }
    }

    /**
     * 广度优先搜索
     */
    public void breadthFirstSearch() {
        // 节点是否被访问
        boolean[] isVisited = new boolean[vertexList.size()];

        // 遍历所有的节点，进行深度优先搜索
        for(int i = 0; i < vertexList.size(); i++) {
            if(!isVisited[i]) {
                breadthFirstSearch(isVisited, i);
            }
        }
    }

    /**
     * 广度优先搜索
     * @param isVisited 节点是否被访问
     * @param i 当前节点下标
     */
    private void breadthFirstSearch(boolean[] isVisited, int i) {
        // 初始化队列
        LinkedList<Integer> queue = new LinkedList<>();
        // 输出当前节点
        System.out.printf("%s ", vertexList.get(i));
        // 将节点设置为已经访问
        isVisited[i] = true;
        // 记录下当前节点的下标
        queue.addLast(i);
        // 遍历队列
        while (!queue.isEmpty()) {
            // 取出队列头节点存放的下标
            int h = queue.removeFirst();
            // 查找当前节点的第一个邻接节点w
            int w = neighbor(i, 0);
            // 找到第一个邻接节点
            while (w != -1) {
                // 如果w节点没有被访问过
                if (!isVisited[w]) {
                    // 输出当前邻接节点
                    System.out.printf("%s ", vertexList.get(w));
                    // 将节点设置为已经访问
                    isVisited[w] = true;
                    // 记录下当前节点的下标
                    queue.addLast(w);
                }
                // 以队列头节点存放的下标，获取下一个邻接节点
                w = neighbor(h, w + 1);
            }
        }
    }

}