package com.johnny.datastructure.tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author johnnyhao
 *
 * 哈夫曼树
 */
public class HuffmanTree {

    public static void main(String[] args) {
        int[] array = { 13, 7, 8, 3, 29, 6, 1 };

        // 构建哈夫曼树
        HuffmanTreeNode huffmanTreeNode =buildHuffmanTree(array);

        // 前序遍历哈夫曼树
        prefixOrder(huffmanTreeNode);
    }

    /**
     * 构建哈夫曼树
     * @param array 数组
     * @return 哈夫曼树根节点
     */
    public static HuffmanTreeNode buildHuffmanTree(int[] array) {
        // 初始化哈夫曼节点，并添加到集合中
        ArrayList<HuffmanTreeNode> huffmanTreeNodes = new ArrayList<>();
        for (int value : array) {
            huffmanTreeNodes.add(new HuffmanTreeNode(value));
        }

        // 循环处理到只剩最后一个节点，即根节点
        while (huffmanTreeNodes.size() > 1) {
            // 排序
            Collections.sort(huffmanTreeNodes);

            // 取出权值最小的两个节点
            HuffmanTreeNode left = huffmanTreeNodes.get(0);
            HuffmanTreeNode right = huffmanTreeNodes.get(1);

            // 生成父节点，构建成新的二叉树
            HuffmanTreeNode parent = new HuffmanTreeNode(left.getId() + right.getId());
            parent.setLeft(left);
            parent.setRight(right);

            // 将节点从数组移除
            huffmanTreeNodes.remove(left);
            huffmanTreeNodes.remove(right);

            // 将新二叉树加入到集合中
            huffmanTreeNodes.add(parent);
        }

        // 返回根节点
        return huffmanTreeNodes.get(0);
    }

    /**
     * 前序遍历
     * 先输出父节点，再遍历左子树和右子树
     * @param huffmanTreeNode 节点
     */
    public static void prefixOrder(HuffmanTreeNode huffmanTreeNode) {
        if (huffmanTreeNode != null) {
            System.out.println(huffmanTreeNode);
            prefixOrder(huffmanTreeNode.getLeft());
            prefixOrder(huffmanTreeNode.getRight());
        }
    }
}

/**
 * 节点
 * 为了实现对节点的排序，实现Comparable接口，使用Collections集合排序
 */
@ToString(exclude = {"left", "right"})
@Getter
@Setter
@RequiredArgsConstructor
class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    /**
     * ID
     */
    @NonNull
    private int id;

    /**
     * 左叶子节点
     */
    private HuffmanTreeNode left;

    /**
     * 右叶子节点
     */
    private HuffmanTreeNode right;

    /**
     * 实现compareTo方法，从小到大排列
     * @param huffmanTreeNode 节点
     * @return int
     */
    @Override
    public int compareTo(HuffmanTreeNode huffmanTreeNode) {
        return this.getId() - huffmanTreeNode.getId();
    }
}