package com.johnny.datastructure.tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author johnnyhao
 *
 * 二叉树
 *
 * - 数组
 *   优点：通过下标方式访问元素，速度快。对于有序数组，还可使用二分查找提高检索速度
 *   缺点：如果要检索具体某个值，或者插入值(按一定顺序)会整体移动，效率较低
 * - 链表
 *   优点：在一定程度上对数组存储方式有优化(比如：插入一个数值节点，只需要将插入节点，链接到链表中即可，删除效率也很好)
 *   缺点：在进行检索时，效率仍然较低，比如(检索某个值，需要从头节点开始遍历)
 * - 树存
 *   能提高数据存储，读取的效率，比如利用二叉排序树(Binary Sort Tree)，既可以保证数据的检索速度，同时也可以保证数据的插入，删除，修改的速度
 */
public class BinaryTree {

    public static void main(String[] args) {
        // 初始化二叉树
        BinaryTreeImpl binaryTree = new BinaryTreeImpl();

        // 创建节点
        Person root = new Person(1, "宋江");
        Person person2 = new Person(2, "吴用");
        Person person3 = new Person(3, "卢俊义");
        Person person4 = new Person(4, "林冲");
        Person person5 = new Person(5, "关胜");

        // 手动创建二叉树
        root.setLeft(person2);
        root.setRight(person3);
        person3.setRight(person4);
        person3.setLeft(person5);

        // 将根节点加入二叉树
        binaryTree.setRoot(root);

        // 前序遍历
        System.out.println("前序遍历");
        binaryTree.prefixOrder();
        // 中序遍历
        System.out.println("中序遍历");
        binaryTree.infixOrder();
        // 后序遍历
        System.out.println("后序遍历");
        binaryTree.postfixOrder();

        // 前序查找
        System.out.println("前序查找");
        System.out.println(binaryTree.prefixSearch(5));
        // 中序查找
        System.out.println("中序查找");
        System.out.println(binaryTree.infixSearch(5));
        // 后序查找
        System.out.println("后序查找");
        System.out.println(binaryTree.postfixSearch(5));

        // 递归删除节点
        System.out.println("递归删除");
        binaryTree.delete(3);
        binaryTree.prefixOrder();
    }
}

/**
 * Person对象
 */
@ToString(exclude = {"left", "right"})
@Getter
@Setter
@RequiredArgsConstructor
class Person {
    /**
     * ID
     */
    @NonNull
    private int id;

    /**
     * 姓名
     */
    @NonNull
    private String name;

    /**
     * 左叶子节点
     */
    private Person left;

    /**
     * 右叶子节点
     */
    private Person right;
}

/**
 * 二叉树
 */
@Setter
class BinaryTreeImpl {
    /**
     * 根节点
     */
    private Person root;

    /**
     * 前序遍历
     * 先输出父节点，再遍历左子树和右子树
     * @param person 节点
     */
    public void prefixOrder(Person person) {
        if (person != null) {
            System.out.println(person);
            prefixOrder(person.getLeft());
            prefixOrder(person.getRight());
        }
    }

    /**
     * 前序遍历根节点默认调用
     */
    public void prefixOrder() {
        prefixOrder(root);
    }

    /**
     * 中序遍历
     * 先遍历左子树，再输出父节点，再遍历右子树
     * @param person 节点
     */
    public void infixOrder(Person person) {
        if (person != null) {
            infixOrder(person.getLeft());
            System.out.println(person);
            infixOrder(person.getRight());
        }
    }

    /**
     * 中序遍历根节点默认调用
     */
    public void infixOrder() {
        infixOrder(root);
    }

    /**
     * 后序遍历
     * 先遍历左子树，再遍历右子树，最后输出父节点
     * @param person 节点
     */
    public void postfixOrder(Person person) {
        if (person != null) {
            postfixOrder(person.getLeft());
            postfixOrder(person.getRight());
            System.out.println(person);
        }
    }

    /**
     * 后序遍历根节点默认调用
     */
    public void postfixOrder() {
        postfixOrder(root);
    }

    /**
     * 前序查找
     * 先判断父节点，再递归判断左子树和右子树
     * @param person 节点
     * @param id id
     * @return 返回结果
     */
    public Person prefixSearch(Person person, int id) {
        // 定义返回结果
        Person resultPerson = null;

        // 先判断父节点
        if (person.getId() == id) {
            resultPerson = person;
        }

        // 父节点不匹配，递归判断左子树
        if (resultPerson == null && person.getLeft() != null) {
            resultPerson = prefixSearch(person.getLeft(), id);
        }

        // 父节点和左子树不匹配，递归判断右子树
        if (resultPerson == null && person.getRight() != null) {
            resultPerson = prefixSearch(person.getRight(), id);
        }

        // 返回结果
        return resultPerson;
    }

    /**
     * 前序查找根节点默认调用
     * @param id id
     * @return 返回结果
     */
    public Person prefixSearch(int id) {
        return prefixSearch(root, id);
    }

    /**
     * 中序查找
     * 先递归判断左子树，再判断父节点，最后递归判断右子树
     * @param person 节点
     * @param id id
     * @return 返回结果
     */
    public Person infixSearch(Person person, int id) {
        // 定义返回结果
        Person resultPerson = null;

        // 先递归判断左子树
        if (person.getLeft() != null) {
            resultPerson = infixSearch(person.getLeft(), id);
        }

        // 左子树不匹配，判断父节点
        if (resultPerson == null && person.getId() == id) {
            resultPerson = person;
        }

        // 左子树和父节点不匹配，递归判断右子树
        if (resultPerson == null && person.getRight() != null) {
            resultPerson = infixSearch(person.getRight(), id);
        }

        // 返回结果
        return resultPerson;
    }

    /**
     * 中序查找根节点默认调用
     * @param id id
     * @return 返回结果
     */
    public Person infixSearch(int id) {
        return infixSearch(root, id);
    }

    /**
     * 后序查找
     * 先递归判断左子树，再递归判断右子树，最后判断父节点
     * @param person 节点
     * @param id id
     * @return 返回结果
     */
    public Person postfixSearch(Person person, int id) {
        // 定义返回结果
        Person resultPerson = null;

        // 先递归判断左子树
        if (person.getLeft() != null) {
            resultPerson = infixSearch(person.getLeft(), id);
        }

        // 左子树不匹配，递归判断右子树
        if (resultPerson == null && person.getRight() != null) {
            resultPerson = infixSearch(person.getRight(), id);
        }

        // 左子树和右子树不匹配，判断父节点
        if (resultPerson == null && person.getId() == id) {
            resultPerson = person;
        }

        // 返回结果
        return resultPerson;
    }

    /**
     * 后序查找根节点默认调用
     * @param id id
     * @return 返回结果
     */
    public Person postfixSearch(int id) {
        return postfixSearch(root, id);
    }

    /**
     * 递归删除节点
     * - 如果删除的节点是叶子节点，则删除该节点
     * - 如果删除的节点是非叶子节点，则删除该子树
     * @param person 节点
     * @param id id
     */
    public void delete(Person person, int id) {
        // 判断左子树
        if (person.getLeft() != null && person.getLeft().getId() == id) {
            person.setLeft(null);
            return;
        }

        // 判断右子树
        if (person.getRight() != null && person.getRight().getId() == id) {
            person.setRight(null);
            return;
        }

        // 未找到则递归遍历
        // 递归左子树
        if (person.getRight() != null) {
            delete(person.getLeft(), id);
        }

        // 递归右子树
        if (person.getRight() != null) {
            delete(person.getRight(), id);
        }
    }

    /**
     * 递归删除节点默认调用
     * @param id id
     */
    public void delete(int id) {
        if (root != null) {
            // 如果根节点是目标节点，直接删除根节点
            if (root.getId() == id) {
                root = null;
            }
            // 如果不是则递归删除
            else {
                delete(root, id);
            }
        }
    }
}