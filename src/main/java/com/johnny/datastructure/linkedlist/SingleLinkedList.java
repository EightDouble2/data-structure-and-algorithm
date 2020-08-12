package com.johnny.datastructure.linkedlist;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Stack;

/**
 * @author johnnyhao
 *
 * 链表
 *
 * 链表是有序的列表，但是它在内存中的存储是分散的
 * - 链表是以节点的方式来存储，是链式存储
 * - 每个节点包含data域，next域：指向下一个节点
 * - 链表的各个节点不一定是连续存储
 * - 链表分带头节点的链表和没有头节点的链表，根据实际的需求来确定
 *
 * 带头结点的单链表
 */
public class SingleLinkedList {

    public static void main(String[] args) {
        //先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        System.out.println("新的节点插入链表尾部");

        SingleLinkedListImpl singleLinkedList = new SingleLinkedListImpl();
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);

        singleLinkedList.list();

        System.out.println("新的节点按编号插入链表");

        singleLinkedList = new SingleLinkedListImpl();
        singleLinkedList.addByNo(hero1);
        singleLinkedList.addByNo(hero4);
        singleLinkedList.addByNo(hero3);
        singleLinkedList.addByNo(hero2);

        singleLinkedList.list();

        System.out.println("按编号修改链表节点");

        singleLinkedList.update(new HeroNode(2, "new卢俊义", "new玉麒麟"));

        singleLinkedList.update(new HeroNode(5, "new卢俊义", "new玉麒麟"));

        singleLinkedList.list();

        System.out.println("按编号删除链表节点");

        singleLinkedList.delete(2);

        singleLinkedList.delete(5);

        singleLinkedList.list();

        System.out.println("求单链表有效节点的个数");

        System.out.println(getLength(singleLinkedList));

        System.out.println("查找单链表中的倒数第index个结点");

        System.out.println(findLastIndexNode(singleLinkedList, 2));

        System.out.println("单链表反转");

        System.out.println("原链表");

        singleLinkedList.list();

        System.out.println("反转后的链表");

        reverse(singleLinkedList);

        singleLinkedList.list();

        System.out.println("逆序打印链表");

        reversePrint(singleLinkedList);
    }

    /**
     * 求单链表有效节点的个数
     * @param singleLinkedList 目标链表
     * @return 有效节点个数
     */
    public static int getLength(SingleLinkedListImpl singleLinkedList) {
        // 获取链表头节点
        HeroNode head = singleLinkedList.getHead();

        // 有效节点计数器
        int length = 0;

        // 创建一个temp辅助遍历链表
        HeroNode temp = head.getNext();

        // 遍历到链表尾部
        while(temp != null) {
            length++;
            temp = temp.getNext();
        }
        return length;
    }

    /**
     * 查找单链表中的倒数第index个结点
     * @param singleLinkedList 目标链表
     * @param index 倒数节点地址
     * @return 返回节点
     */
    public static HeroNode findLastIndexNode(SingleLinkedListImpl singleLinkedList, int index) {
        // 获取链表头节点
        HeroNode head = singleLinkedList.getHead();

        // 判断如果链表为空，返回null
        if(head.getNext() == null) {
            return null;
        }
        // 先遍历一遍获取链表长度
        int length = getLength(singleLinkedList);

        // 校验index，不通过则返回null
        if (index <=0 || index >length) {
            return null;
        }

        // 创建一个temp辅助遍历链表
        HeroNode temp = head.getNext();

        // 遍历到倒数index个节点，返回temp
        for (int i = 0; i < length - index; i++) {
            temp = temp.getNext();
        }
        return temp;
    }

    /**
     * 单链表反转
     * @param singleLinkedList 目标链表
     */
    public static void reverse(SingleLinkedListImpl singleLinkedList) {
        // 获取链表头节点
        HeroNode head = singleLinkedList.getHead();

        // 如果链表为空或者只有一个节点，无需反转
        if (head.getNext() == null || head.getNext().getNext() == null) {
            return;
        }

        // 创建一个temp辅助遍历链表
        HeroNode temp = head.getNext();
        // 创建一个next辅助指向temp的下一个节点
        HeroNode next;
        // 创建一个新的链表头，用于存放反转后的链表
        HeroNode reverseHead = new HeroNode(0, "", "");

        while(temp != null) {
            // 先暂时保存当前节点的下一个节点，因为后面需要使用
            next = temp.getNext();
            // 将temp的下一个节点指向新的链表的最前端
            temp.setNext(reverseHead.getNext());
            // 将temp连接到新的链表上
            reverseHead.setNext(temp);
            // 让temp后移
            temp = next;
        }
        //将head.next指向reverseHead.next, 实现单链表的反转
        head.setNext(reverseHead.getNext());
    }

    /**
     * 逆序打印链表
     * - 反转列表后直接打印，但会破坏原链表
     * - 利用栈先进后出的特性，将所有节点压入栈，实现逆序打印
     * @param singleLinkedList 目标链表
     */
    public static void reversePrint(SingleLinkedListImpl singleLinkedList) {
        // 获取链表头节点
        HeroNode head = singleLinkedList.getHead();

        // 如果链表为空，无需打印
        if (head.getNext() == null) {
            return;
        }

        // 创建一个栈，压入各个节点
        Stack<HeroNode> stack = new Stack<HeroNode>();
        // 创建一个temp辅助遍历链表
        HeroNode temp = head.getNext();

        // 将所有节点压入栈中
        while (temp != null) {
            stack.push(temp);
            temp = temp.getNext();
        }

        // 循环出栈打印
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }
}

/**
 * 定义HeroNode对象
 * 每个HeroNode对象就是一个节点
 */
@ToString(exclude = "next")
@Getter
@Setter
@RequiredArgsConstructor
class HeroNode {

    /**
     * 排名
     */
    @NonNull
    private int no;

    /**
     * 姓名
     */
    @NonNull
    private String name;

    /**
     * 昵称
     */
    @NonNull
    private String nickname;

    /**
     * 下一节点
     */
    private HeroNode next;
}

/**
 * 定义单链表
 */
@Getter
class SingleLinkedListImpl {
    /**
     * 初始化头节点，定义链表头部，不存放具体数据
     */
    private final HeroNode head = new HeroNode(0, "", "");

    /**
     * 添加节点到链表尾部
     * - 找到链表的最后节点
     * - 将最后节点的next指向新的节点
     * @param heroNode 新节点
     */
    public void add(HeroNode heroNode) {

        // 创建一个temp辅助遍历链表
        HeroNode temp = head;

        // 遍历链表，找到最后节点
        // 找到最后节点跳出循环
        while (temp.getNext() != null) {
            // 没有找到最后节点将temp后移
            temp = temp.getNext();
        }

        // 将最后节点的next指向新的节点
        temp.setNext(heroNode);
    }

    /**
     * 根据编号添加节点到链表指定位置
     * @param heroNode 新节点
     */
    public void addByNo(HeroNode heroNode) {

        // 创建一个temp辅助遍历链表，用于找到目标节点
        HeroNode temp = head;
        // flag标志目标的编号是否存在
        boolean flag = false;

        // 遍历链表，找到目标节点的前一个节点
        // 找到最后节点跳出循环
        while (temp.getNext() != null) {
            // 找到目标位置，就在temp的后面插入
            if(temp.getNext().getNo() > heroNode.getNo()) {
                break;
            }
            // 找到目标位置编号已经存在
            else if (temp.getNext().getNo() == heroNode.getNo()) {
                flag = true;
                break;
            }
            // 没有找到目标节点将temp后移
            temp = temp.getNext();
        }

        // 判断flag的值，不能添加说明编号存在
        if (flag) {
            System.out.printf("编号%d已经存在，不能添加\n", heroNode.getNo());
        }
        else {
            // 将最后节点的next指向新的节点
            heroNode.setNext(temp.getNext());
            temp.setNext(heroNode);
        }
    }

    /**
     * 按编号修改节点
     * @param newHeroNode 新的节点
     */
    public void update(HeroNode newHeroNode) {
        // 判断链表是否为空
        if (head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }

        // 根据编号找到需要修改的节点
        // 定义一个辅助变量
        HeroNode temp = head.getNext();
        // flag标志目标的编号是否存在
        boolean flag = false;

        // 遍历链表，找到目标节点
        // 找到最后节点跳出循环
        while(temp != null) {
            if(temp.getNo() == newHeroNode.getNo()) {
                // 找到目标节点
                flag = true;
                break;
            }
            temp = temp.getNext();
        }

        // 根据flag判断是否找到要修改的节点
        if(flag) {
            temp.setName(newHeroNode.getName());
            temp.setNickname(newHeroNode.getNickname());
        }
        // 没有找到
        else {
            System.out.printf("编号%d不存在，不能修改\n", newHeroNode.getNo());
        }
    }

    /**
     * 删除节点
     * @param no 删除节点编号
     */
    public void delete(int no) {

        // 根据编号找到需要删除的节点
        // 定义一个辅助变量
        HeroNode temp = head;

        // flag标志目标节点是否存在
        boolean flag = false;

        // 遍历链表，找到目标节点的前一个节点
        // 找到最后节点跳出循环
        while(temp.getNext() != null) {
            if(temp.getNext().getNo() == no) {
                // 找到目标节点
                flag = true;
                break;
            }
            temp = temp.getNext();
        }

        // 根据flag判断是否找到要删除的节点
        if(flag) {
            temp.setNext(temp.getNext().getNext());
        }
        // 没有找到
        else {
            System.out.printf("编号%d不存在，不能删除\n", no);
        }
    }

    /**
     * 遍历显示链表
     */
    public void list() {
        // 判断链表是否为空
        if (head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }

        // 创建一个temp辅助遍历链表
        HeroNode temp = head.getNext();

        // 判断是否到链表最后
        while (temp != null) {
            // 输出节点的信息
            System.out.println(temp);
            // 将temp后移
            temp = temp.getNext();
        }
    }
}