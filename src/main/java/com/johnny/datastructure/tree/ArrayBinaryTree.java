package com.johnny.datastructure.tree;

import lombok.AllArgsConstructor;

/**
 * @author johnnyhao
 *
 * 顺序存储二叉树
 *
 * 从数据存储来看，数组存储方式和树的存储方式可以相互转换
 *
 * - 顺序二叉树通常只考虑完全二叉树
 * - n表示二叉树中的第几个元素(根节点为0)，第n个元素的左子节点为2*n+1，第n个元素的右子节点为2*n+2，第n个元素的父节点为(n-1)/2
 */
public class ArrayBinaryTree {

    public static void main(String[] args) {
        // 创建数组
        int[] array = {1, 2, 3, 4, 5, 6, 7};

        // 初始化顺序存储二叉树
        ArrayBinaryTreeImpl arrayBinaryTree = new ArrayBinaryTreeImpl(array);

        // 前序遍历顺序存储二叉树
        arrayBinaryTree.prefixOrder();
    }
}

/**
 * 顺序存储二叉树
 */
@AllArgsConstructor
class ArrayBinaryTreeImpl {
    private final int[] array;

    /**
     * 前序遍历
     * @param index 数组下标
     */
    public void prefixOrder(int index) {
        // 输出当前节点
        System.out.println(array[index]);

        // 定义左右子节点数组下标
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        // 递归遍历左子树
        if (left < array.length) {
            prefixOrder(left);
        }

        // 递归遍历右子树
        if (right < array.length) {
            prefixOrder(right);
        }
    }

    /**
     * 前序遍历根节点默认调用
     */
    public void prefixOrder() {
        if (array != null && array.length > 0) {
            prefixOrder(0);
        }
    }
}