package com.johnny.datastructure.tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author johnnyhao
 *
 * 二叉排序树
 * BST树
 *
 * - 对于二叉排序树的任何一个非叶子节点，要求左子节点的值比当前节点的值小，右子节点的值比当前节点的值大。
 * - 如果有相同的值，可以将该节点放在左子节点或右子节点
 */
public class BinarySortTree {

    public static void main(String[] args) {
        // 数组
        int[] array = {7, 3, 10, 12, 5, 1, 9, 2};

        // 初始化二叉排序树
        BinarySortTreeImpl binarySortTree = new BinarySortTreeImpl();

        // 构建二叉排序树
        binarySortTree.build(array);

        // 中序遍历二叉排序树
        binarySortTree.infixOrder();

        // 删除节点
        binarySortTree.remove(10);
        binarySortTree.infixOrder();
    }
}

/**
 * 节点
 */
@ToString(exclude = {"left", "right"})
@Getter
@Setter
@RequiredArgsConstructor
class SortNode {
    /**
     * ID
     */
    @NonNull
    private int id;

    /**
     * 左叶子节点
     */
    private SortNode left;

    /**
     * 右叶子节点
     */
    private SortNode right;
}

/**
 * 二叉排序树
 */
@Setter
class BinarySortTreeImpl {
    /**
     * 根节点
     */
    private SortNode root;

    /**
     * 构建二叉排序树
     * @param node 当前节点
     * @param newNode 新节点
     */
    private void build(SortNode node, SortNode newNode) {
        // 节点为空不处理
        if (node == null || newNode == null) {
            return;
        }

        // 判断节点值
        // 新节点的值小于当前节点
        if (newNode.getId() < node.getId()) {
            // 当前节点左节点的值为空，则添加到左节点
            if (node.getLeft() == null) {
                node.setLeft(newNode);
            }
            // 当前节点左节点的值不为空，则继续向左子树递归
            else {
                build(node.getLeft(), newNode);
            }
        }
        // 新节点的值不小于当前节点
        else {
            // 当前节点右节点的值为空，则添加到右节点
            if (node.getRight() == null) {
                node.setRight(newNode);
            }
            // 当前节点右节点的值不为空，则继续向右子树递归
            else {
                build(node.getRight(), newNode);
            }
        }
    }

    /**
     * 构建二叉排序树默认调用
     * @param array 数组
     */
    public void build(int[] array) {
        // 根节点为空则加入根节点
        if (root == null || array.length > 1) {
            root = new SortNode(array[0]);
        }

        // 将剩余节点加入到二叉排序树
        for (int i = 1; i < array.length; i++) {
            build(root, new SortNode(array[i]));
        }
    }

    /**
     * 中序遍历
     * 先遍历左子树，再输出父节点，再遍历右子树
     * @param node 节点
     */
    private void infixOrder(SortNode node) {
        if (node != null) {
            infixOrder(node.getLeft());
            System.out.println(node);
            infixOrder(node.getRight());
        }
    }

    /**
     * 中序遍历根节点默认调用
     */
    public void infixOrder() {
        infixOrder(root);
    }

    /**
     * 删除节点
     * @param value 目标值
     */
    public void remove(int value) {
        // 根节点为空不处理
        if (root == null) {
            return;
        }

        // 定义两个指针用于表示找到的当前节点和父节点
        SortNode currentNode = root;
        SortNode parentNode = null;

        // 遍历找到目标节点
        while (currentNode != null) {
            // 找到则退出循环
            if (currentNode.getId() == value) {
                break;
            }

            // 没找到则继续遍历，父指针指向当前节点
            parentNode = currentNode;

            // 目标值比当前节点小，则向左子树查找
            if (currentNode.getId() > value) {
                currentNode = currentNode.getLeft();
            }
            // 目标值不比当前节点小，则向右子树查找
            else {
                currentNode = currentNode.getRight();
            }
        }

        // 未找到则不处理
        if (currentNode == null) {
            return;
        }

        // 如果目标节点为叶子节点
        if (currentNode.getLeft() == null && currentNode.getRight() == null) {
            // 父节点为空，则目标节点为根节点，直接删除根节点
            if (parentNode == null) {
                root = null;
            }
            // 父节点不为空
            else {
                // 判断目标节点是父节点的哪一个节点
                // 左子节点
                if (parentNode.getLeft() != null && parentNode.getLeft().getId() == value) {
                    parentNode.setLeft(null);
                }
                // 右子节点
                else if (parentNode.getRight() != null && parentNode.getRight().getId() == value) {
                    parentNode.setRight(null);
                }
            }
        }
        // 如果目标节点有两个子树
        else if (currentNode.getLeft() != null && currentNode.getRight() != null) {
            // 向当前节点的右子树找到最小节点和最小节点的父节点
            SortNode tempCurrentNode = currentNode.getRight();
            SortNode tempParentNode = currentNode;
            while (tempCurrentNode.getLeft() != null) {
                tempParentNode = tempCurrentNode;
                tempCurrentNode = tempCurrentNode.getLeft();
            }
            // 将最小节点的值赋值给当前节点
            currentNode.setId(tempCurrentNode.getId());
            // 删除掉最小节点，最小节点一定是
            // 判断目标节点是父节点的哪一个节点
            // 左子节点
            if (tempParentNode.getLeft() != null && tempParentNode.getLeft().getId() == tempCurrentNode.getId()) {
                tempParentNode.setLeft(null);
            }
            // 右子节点
            else if (tempParentNode.getRight() != null && tempParentNode.getRight().getId() == tempCurrentNode.getId()) {
                tempParentNode.setRight(null);
            }
        }
        // 如果目标节点只有一个子树
        else {
            // 如果目标节点有左子节点
            if (currentNode.getLeft() != null) {
                // 父节点为空，则目标节点为根节点，直接将根节点指向左子节点
                if (parentNode == null) {
                    root = currentNode.getLeft();
                }
                else {
                    // 判断目标节点是父节点的哪一个节点
                    // 左子节点
                    if (parentNode.getLeft().getId() == value) {
                        parentNode.setLeft(currentNode.getLeft());
                    }
                    // 右子节点
                    else if (parentNode.getRight().getId() == value) {
                        parentNode.setRight(currentNode.getLeft());
                    }
                }
            }
            // 如果目标节点有右子节点
            else if (currentNode.getRight() != null) {
                // 父节点为空，则目标节点为根节点，直接将根节点指向右子节点
                if (parentNode == null) {
                    root = currentNode.getRight();
                }
                else {
                    // 判断目标节点是父节点的哪一个节点
                    // 左子节点
                    if (parentNode.getLeft().getId() == value) {
                        parentNode.setLeft(currentNode.getRight());
                    }
                    // 右子节点
                    else if (parentNode.getRight().getId() == value) {
                        parentNode.setRight(currentNode.getRight());
                    }
                }
            }
        }
    }
}