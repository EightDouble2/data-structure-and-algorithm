package com.johnny.datastructure.tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author johnnyhao
 *
 * 线索二叉树
 *
 * - n个节点的二叉链表中含有n+1[公式 2n-(n-1)=n+1]个空指针域。利用二叉链表中的空指针域，存放指向该节点在某种遍历次序下的前驱和后继节点的指针(这种附加的指针称为"线索")
 *   这种加上了线索的二叉链表称为线索链表，相应的二叉树称为线索二叉树(Threaded BinaryTree)
 * - 根据线索性质的不同，线索二叉树可分为前序线索二叉树、中序线索二叉树和后序线索二叉树三种
 * - 一个节点的前一个节点，称为前驱节点，一个节点的后一个节点，称为后继节点
 *
 * 因为线索化后，各个结点指向有变化，因此原来的遍历方式不能使用，这时需要使用新的方式遍历线索化二叉树，各个节点可以通过线型方式遍历，因此无需使用递归方式，这样也提高了遍历的效率
 * 遍历的次序应当和线索化次序保持一致
 */
public class ThreadedBinaryTree {

    public static void main(String[] args) {
        // 初始化线索二叉树
        ThreadedBinaryTreeImpl threadedBinaryTree = new ThreadedBinaryTreeImpl();

        // 创建节点
        Node root = new Node(1);
        Node node2 = new Node(3);
        Node node3 = new Node(6);
        Node node4 = new Node(8);
        Node node5 = new Node(10);
        Node node6 = new Node(14);

        // 手动创建线索二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        // 将根节点加入线索二叉树
        threadedBinaryTree.setRoot(root);

        // 中序线索化二叉树
        threadedBinaryTree.infixThreaded();

        //测试: 以10号节点测试]
        System.out.println("10号节点的前驱节点是="  + node5.getLeft());
        System.out.println("10号节点的后继节点是="  + node5.getRight());

        // 中序遍历线索二叉树
        System.out.println("中序遍历线索化二叉树");
        threadedBinaryTree.infixOrder();
    }
}

/**
 * 节点
 */
@ToString(exclude = {"left", "right", "leftType", "rightType"})
@Getter
@Setter
@RequiredArgsConstructor
class Node {
    /**
     * ID
     */
    @NonNull
    private int id;

    /**
     * 左叶子节点
     */
    private Node left;

    /**
     * 右叶子节点
     */
    private Node right;

    /**
     * 左叶子节点类型
     * false为左子树
     * true为前驱节点
     */
    private boolean leftType;

    /**
     * 右叶子节点
     * false为右子树
     * true为后继节点
     */
    private boolean rightType;
}

/**
 * 线索二叉树
 */
@Setter
class ThreadedBinaryTreeImpl {
    /**
     * 根节点
     */
    private Node root;

    /**
     * 前驱节点
     * 为了实现线索化，pre总是指向前一个节点
     */
    private Node pre;

    /**
     * 中序线索化二叉树
     * @param node 节点
     */
    public void infixThreaded(Node node) {
        // 如果当前节点为空, 不能线索化
        if(node == null) {
            return;
        }

        // 线索化左子树
        infixThreaded(node.getLeft());

        // 线索化当前节点的前驱节点
        // 当前节点的左节点为空，将当前节点的左指针指向前驱节点
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(true);
        }

        // 线索化当前节点的后继节点
        // 处理后继节点需在下一次递归时操作
        // 前驱节点的右节点为空，将前驱节点的右指针指向当前节点
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(true);
        }

        // 前驱节点后移
        pre = node;

        // 线索化右子树
        infixThreaded(node.getRight());
    }

    /**
     * 中序线索化二叉树根节点默认调用
     */
    public void infixThreaded() {
        infixThreaded(root);
    }

    /**
     * 中序遍历线索二叉树
     */
    public void infixOrder() {
        // 定义一个辅助指针，从root开始遍历
        Node node = root;

        // 辅助指针为空时，表示遍历完成
        while (node != null) {
            // 向左侧找到线索化处理后的有效节点
            while (!node.isLeftType()) {
                node = node.getLeft();
            }

            // 打印当前节点
            System.out.println(node);

            // 如果当前结点的右指针指向的是后继结点，就一直输出
            while (node.isRightType()) {
                node = node.getRight();
                System.out.println(node);
            }

            // 辅助指针后移
            node = node.getRight();
        }
    }
}
