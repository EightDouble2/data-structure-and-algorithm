package com.johnny.datastructure.tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author johnnyhao
 *
 * 平衡二叉树
 *
 * 二叉排序树如果一个子树高度明显比另一个子树高，插入速度没有影响，查询速度明显降低，不能发挥二叉排序树的优势
 *
 * 平衡二叉树可以保证查询效率较高。
 * - 它是一棵空树或它的左右两个子树的高度差的绝对值不超过一，并且左右两个子树都是一棵平衡二叉树
 *
 * 如果二叉树的节点很多
 * - 在构建二叉树时，需要多次进行i/o操作(海量数据存在数据库或文件中)，节点海量，构建二叉树时，速度有影响
 * - 节点海量，也会造成二叉树的高度很大，会降低操作速度
 *
 * 多叉树
 *
 * 允许每个节点可以有更多的数据项和更多的子节点
 *
 * 2-3树
 * - 2-3树的所有叶子节点都在同一层(只要是B树都满足这个条件)
 * - 有两个子节点的节点叫二节点，二节点要么没有子节点，要么有两个子节点
 * - 有三个子节点的节点叫三节点，三节点要么没有子节点，要么有三个子节点
 * - 当按照规则插入一个数到某个节点时，不能满足上面三个要求，就需要拆，先向上拆，如果上层满，则拆本层，拆后仍然需要满足上面3个条件
 * - 对于三节点的子树的值大小仍然遵守(BST 二叉排序树)的规则
 *
 * B树
 * - B树的阶：节点的最多子节点个数。比如2-3树的阶是3，2-3-4树的阶是4
 * - B-树的搜索，从根结点开始，对结点内的关键字(有序)序列进行二分查找，如果命中则结束，否则进入查询关键字所属范围的儿子结点；重复，直到所对应的儿子指针为空，或已经是叶子结点
 * - 关键字集合分布在整颗树中，即叶子节点和非叶子节点都存放数据
 * - 搜索有可能在非叶子结点结束
 * - 其搜索性能等价于在关键字全集内做一次二分查找
 *
 * B+树
 * - B+树的搜索与B树也基本相同，区别是B+树只有达到叶子结点才命中(B树可以在非叶子结点命中)，其性能也等价于在关键字全集做一次二分查找
 * - 所有关键字都出现在叶子结点的链表中(即数据只能在叶子节点[也叫稠密索引])，且链表中的关键字(数据)恰好是有序的。
 * - 不可能在非叶子结点命中
 * - 非叶子结点相当于是叶子结点的索引(稀疏索引)，叶子结点相当于是存储(关键字)数据的数据层
 * - 更适合文件索引系统
 * - B树和B+树各有自己的应用场景，不能说B+树完全比B树好，反之亦然
 *
 * B*树
 * B*树是B+树的变体，在B+树的非根和非叶子结点再增加指向兄弟的指针
 * - B*树定义了非叶子结点关键字个数至少为(2/3)*M，即块的最低使用率为2/3，而B+树的块的最低使用率为的1/2
 * - 从第1个特点我们可以看出，B*树分配新结点的概率比B+树要低，空间使用率更高
 */
public class BinaryBalanceTree {

    public static void main(String[] args) {
        // 数组
        int[] array = { 10, 11, 7, 6, 8, 9 };

        // 初始化平衡二叉树
        BinaryBalanceTreeImpl binaryBalanceTree = new BinaryBalanceTreeImpl();

        // 构建平衡二叉树
        binaryBalanceTree.build(array);

        // 中序遍历平衡二叉树
        binaryBalanceTree.infixOrder();

        System.out.println(binaryBalanceTree.heightDifference(binaryBalanceTree.getRoot()));
    }
}

/**
 * 节点
 */
@ToString(exclude = {"left", "right"})
@Getter
@Setter
@RequiredArgsConstructor
class BinaryBalanceNode {
    /**
     * ID
     */
    @NonNull
    private int id;

    /**
     * 左叶子节点
     */
    private BinaryBalanceNode left;

    /**
     * 右叶子节点
     */
    private BinaryBalanceNode right;
}

/**
 * 平衡二叉树
 */
@Getter
class BinaryBalanceTreeImpl {
    /**
     * 根节点
     */
    private BinaryBalanceNode root;

    /**
     * 构建平衡二叉树
     * @param node 当前节点
     * @param newNode 新节点
     */
    private void build(BinaryBalanceNode node, BinaryBalanceNode newNode) {
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

        // 当添加完一个节点后，如果左子树的高度比右子树的高度小于1，左旋转
        if (heightDifference(root) < 1) {
            // 如果它的右子树的左子树的高度大于它的右子树的右子树的高度
            if (root.getRight() != null && heightDifference(root.getRight()) > 0) {
                // 先对右子节点进行右旋转
                rightRotate(root.getRight());
            }
            // 然后在对当前节点进行左旋转
            leftRotate(root);
        }

        // 当添加完一个节点后，如果 (左子树的高度 - 右子树的高度) > 1, 右旋转
        if (heightDifference(root) > 1) {
            // 如果它的左子树的右子树高度大于它的左子树的高度
            if (root.getLeft() != null && heightDifference(root.getLeft()) < 0) {
                // 先对左子节点进行左旋转
                leftRotate(root.getLeft());
            }
            // 然后在对当前节点进行右旋转
            rightRotate(root);
        }
    }

    /**
     * 构建平衡二叉树默认调用
     * @param array 数组
     */
    public void build(int[] array) {
        // 根节点为空则加入根节点
        if (root == null || array.length > 1) {
            root = new BinaryBalanceNode(array[0]);
        }

        // 将剩余节点加入到平衡二叉树
        for (int i = 1; i < array.length; i++) {
            build(root, new BinaryBalanceNode(array[i]));
        }
    }

    /**
     * 计算高度差
     * @param node 节点
     * @return 高度差，左子树减右子树
     */
    public int heightDifference(BinaryBalanceNode node) {
        // 定义左右子树高度
        int leftHeight = node.getLeft() == null ? 0 : treeHeight(node.getLeft());
        int rightHeight = node.getRight() == null ? 0 : treeHeight(node.getRight());

        return leftHeight - rightHeight;
    }

    /**
     * 计算子树高度
     * @param node 节点
     * @return 子树高度
     */
    public int treeHeight(BinaryBalanceNode node) {
        return Math.max(node.getLeft() == null ? 0 : treeHeight(node.getLeft()), node.getRight() == null ? 0 : treeHeight(node.getRight())) + 1;
    }

    /**
     * 左旋转方法
     * @param node 节点
     */
    public void leftRotate(BinaryBalanceNode node) {
        // 创建新的节点，以当前根节点的值
        BinaryBalanceNode newNode = new BinaryBalanceNode(node.getId());
        // 把新的节点的左子树设置成当前节点的左子树
        newNode.setLeft(node.getLeft());
        // 把新的节点的右子树设置成当前节点的右子树的左子树
        newNode.setRight(node.getRight().getLeft());
        // 把当前节点的值替换成右子节点的值
        node.setId(node.getRight().getId());
        // 把当前节点的右子树设置成当前节点右子树的右子树
        node.setRight(node.getRight().getRight());
        //把当前节点的左子树(左子节点)设置成新的节点
        node.setLeft(newNode);
    }

    /**
     * 右旋转方法
     * @param node 节点
     */
    public void rightRotate(BinaryBalanceNode node) {
        // 创建新的节点，以当前根节点的值
        BinaryBalanceNode newNode = new BinaryBalanceNode(node.getId());
        // 把新的节点的右子树设置成当前节点的右子树
        newNode.setRight(node.getRight());
        // 把新的节点的左子树设置成当前节点的左子树的右子树
        newNode.setLeft(node.getLeft().getRight());
        // 把当前节点的值替换成左子节点的值
        node.setId(node.getLeft().getId());
        // 把当前节点的左子树设置成当前节点左子树的左子树
        node.setLeft(node.getLeft().getLeft());
        //把当前节点的右子树(右子节点)设置成新的节点
        node.setRight(newNode);
    }

    /**
     * 中序遍历
     * 先遍历左子树，再输出父节点，再遍历右子树
     * @param node 节点
     */
    private void infixOrder(BinaryBalanceNode node) {
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
}