package com.johnny.datastructure.linkedlist;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author johnnyhao
 *
 * 双向链表
 *
 * - 单向链表，查找的方向只能是一个方向，而双向链表可以向前或者向后查找
 * - 单向链表不能自我删除，需要靠辅助节点，而双向链表，则可以自我删除
 *
 * 带头结点的双向链表
 */
public class DoubleLinkedList {

    public static void main(String[] args) {
        System.out.println("新的节点插入链表尾部");

        DoubleLinkedListImpl doubleLinkedList = new DoubleLinkedListImpl();
        doubleLinkedList.add(new DoubleHeroNode(1, "宋江", "及时雨"));
        doubleLinkedList.add(new DoubleHeroNode(2, "卢俊义", "玉麒麟"));
        doubleLinkedList.add(new DoubleHeroNode(3, "吴用", "智多星"));
        doubleLinkedList.add(new DoubleHeroNode(4, "林冲", "豹子头"));

        doubleLinkedList.list();

        System.out.println("新的节点按编号插入链表");

        doubleLinkedList = new DoubleLinkedListImpl();
        doubleLinkedList.addByNo(new DoubleHeroNode(1, "宋江", "及时雨"));
        doubleLinkedList.addByNo(new DoubleHeroNode(4, "林冲", "豹子头"));
        doubleLinkedList.addByNo(new DoubleHeroNode(3, "吴用", "智多星"));
        doubleLinkedList.addByNo(new DoubleHeroNode(2, "卢俊义", "玉麒麟"));

        doubleLinkedList.list();

        System.out.println("按编号修改链表节点");

        doubleLinkedList.update(new DoubleHeroNode(2, "new卢俊义", "new玉麒麟"));

        doubleLinkedList.update(new DoubleHeroNode(5, "new卢俊义", "new玉麒麟"));

        doubleLinkedList.list();

        System.out.println("按编号删除链表节点");

        doubleLinkedList.delete(2);

        doubleLinkedList.delete(5);

        doubleLinkedList.list();
    }
}

/**
 * 定义HeroNode对象
 * 每个HeroNode对象就是一个节点
 */
@ToString(exclude = {"pre", "next"})
@Getter
@Setter
@RequiredArgsConstructor
class DoubleHeroNode {

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
     * 上一节点
     */
    private DoubleHeroNode pre;

    /**
     * 下一节点
     */
    private DoubleHeroNode next;
}


/**
 * 定义双向链表
 */
@Getter
class DoubleLinkedListImpl {
    /**
     * 初始化头节点，定义链表头部，不存放具体数据
     */
    private final DoubleHeroNode head = new DoubleHeroNode(0, "", "");

    /**
     * 添加节点到链表尾部
     * - 找到链表的最后节点
     * - 将最后节点的next指向新的节点，新节点的pre指向最后节点
     * @param heroNode 新节点
     */
    public void add(DoubleHeroNode heroNode) {

        // 创建一个temp辅助遍历链表
        DoubleHeroNode temp = head;

        // 遍历链表，找到最后节点
        // 找到最后节点跳出循环
        while (temp.getNext() != null) {
            // 没有找到最后节点将temp后移
            temp = temp.getNext();
        }

        // 将最后节点的next指向新的节点，新节点的pre指向最后节点
        temp.setNext(heroNode);
        heroNode.setPre(temp);
    }

    /**
     * 根据编号添加节点到链表指定位置
     * @param heroNode 新节点
     */
    public void addByNo(DoubleHeroNode heroNode) {

        // 创建一个temp辅助遍历链表，用于找到目标位置的前一个节点
        DoubleHeroNode temp = head;
        // flag标志目标的编号是否存在
        boolean flag = false;

        // 遍历链表，找到目标节点
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
            if (temp.getNext() != null) {
                temp.getNext().setPre(heroNode);
                heroNode.setNext(temp.getNext());
            }
            temp.setNext(heroNode);
            heroNode.setPre(temp);
        }
    }

    /**
     * 按编号修改节点
     * @param newHeroNode 新的节点
     */
    public void update(DoubleHeroNode newHeroNode) {
        // 判断链表是否为空
        if (head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }

        // 根据编号找到需要修改的节点
        // 定义一个辅助变量
        DoubleHeroNode temp = head.getNext();
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
     * - 对于双向链表的删除可以直接找到要删除的节点，自我删除
     * @param no 删除节点编号
     */
    public void delete(int no) {

        // 根据编号找到需要删除的节点
        // 定义一个辅助变量
        DoubleHeroNode temp = head.getNext();

        // flag标志目标节点是否存在
        boolean flag = false;

        // 遍历链表，找到目标节点
        // 找到最后节点跳出循环
        while (temp != null) {
            if(temp.getNo() == no) {
                // 找到目标节点
                flag = true;
                break;
            }
            temp = temp.getNext();
        }

        // 根据flag判断是否找到要删除的节点
        if (flag) {
            // 目标节点的前一个节点的next指向目标节点的后一个节点
            temp.getPre().setNext(temp.getNext());
            // 目标节点的后一个节点的pre指向目标节点的前一个节点，但要注意目标节点是不是最后一个节点，否则会出现空指针
            if (temp.getNext() != null) {
                temp.getNext().setPre(temp.getPre());
            }
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
        DoubleHeroNode temp = head.getNext();

        // 判断是否到链表最后
        while (temp != null) {
            // 输出节点的信息
            System.out.println(temp);
            // 将temp后移
            temp = temp.getNext();
        }
    }
}