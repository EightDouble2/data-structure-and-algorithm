package com.johnny.datastructure.linkedlist;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author johnnyhao
 *
 * 环形单链表
 *
 * Josephus问题
 * 设编号为1，2，…n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，数到m的那个人出列，它的下一位又从1开始报数，数到m的那个人又出列，依次类推，直到所有人出列为止，由此产生一个出队编号的序列
 */
public class CircleSingleLinkedList {

    public static void main(String[] args) {
        // 创建链表
        CircleSingleLinkedListImpl circleSingleLinkedList = new CircleSingleLinkedListImpl();
        circleSingleLinkedList.add(5);
        circleSingleLinkedList.show();

        josephus(1, 2, 5);
    }

    /**
     * 约瑟夫问题
     * @param startNum 起始位置
     * @param countNum 计数
     * @param totalNum 总人数
     */
    public static void josephus(int startNum, int countNum, int totalNum) {
        // 校验参数
        if (startNum < 1 || totalNum < 1 || startNum > totalNum) {
            System.out.println("参数输入有误， 请重新输入");
            return;
        }

        // 创建链表
        CircleSingleLinkedListImpl circleSingleLinkedList = new CircleSingleLinkedListImpl();
        circleSingleLinkedList.add(totalNum);

        // 创建一个startBoy，tempBoy指针辅助遍历链表
        BoyNode startBoy = circleSingleLinkedList.getFirstNode();
        BoyNode tempBoy = circleSingleLinkedList.getFirstNode();

        // 将tempBoy指针指向链表最后一个节点
        while (tempBoy.getNext() != startBoy) {
            tempBoy = tempBoy.getNext();
        }

        // 将startBoy指针指向startNum个节点，tempBoy指针指向startNum个节点的前一个节点
        for (int j = 0; j < startNum - 1; j++) {
            startBoy = startBoy.getNext();
            tempBoy = tempBoy.getNext();
        }

        // 当startBoy和tempBoy指向同一个节点时，说明链表中只有一个节点，退出循环
        while (startBoy != tempBoy) {
            // 开始计数，startBoy和tempBoy同时移动countNum-1次
            for (int i = 0; i < countNum - 1; i++) {
                startBoy = startBoy.getNext();
                tempBoy = tempBoy.getNext();
            }

            // 此时startBoy就是需要移除的节点
            System.out.printf("移除%d号节点\n", startBoy.getNo());

            startBoy = startBoy.getNext();
            tempBoy.setNext(startBoy);
        }

        System.out.printf("最后留下%d号节点\n", startBoy.getNo());
    }
}

/**
 * 定义BoyNode对象
 * 每个BoyNode对象就是一个节点
 */
@ToString(exclude = {"next"})
@Getter
@Setter
@RequiredArgsConstructor
class BoyNode {

    /**
     * 编号
     */
    @NonNull
    private int no;

    /**
     * 下一节点
     */
    private BoyNode next;
}

/**
 * 创建环形单链表
 */
@Getter
class CircleSingleLinkedListImpl {

    /**
     * 头节点指针
     */
    private BoyNode firstNode;

    /**
     * 按总人数创建环形单链表
     * @param totalNum 总人数
     */
    public void add(int totalNum) {

        // 校验总人数
        if (totalNum <= 0) {
            System.out.println("总人数不正确");
            return;
        }

        // 创建一个curBoy辅助遍历链表
        BoyNode curBoy = null;

        // 循环创建链表
        for (int i = 1; i <= totalNum; i++) {
            // 根据编号生成节点
            BoyNode boyNode = new BoyNode(i);

            // 如果是第一个节点
            if (i == 1) {
                // 将头指针指向第一个节点，头指针的next指向自己
                firstNode = boyNode;
                firstNode.setNext(firstNode);
                // 将辅助指针指向头指针
                curBoy = firstNode;
            }
            else {
                // 将节点插入到最后一个节点，辅助指针指向插入的最后一个节点
                curBoy.setNext(boyNode);
                boyNode.setNext(firstNode);
                curBoy = boyNode;
            }
        }
    }

    /**
     * 遍历打印环形链表
     */
    public void show() {
        // 判断链表是否为空
        if (firstNode == null) {
            System.out.println("链表为空");
            return;
        }

        // 创建一个curBoy辅助遍历链表
        BoyNode curBoy = firstNode;

        // 循环遍历
        while (true) {
            System.out.println(curBoy);

            // 循环到最后一个节点跳出循环
            if (curBoy.getNext() == firstNode) {
                break;
            }

            curBoy = curBoy.getNext();
        }
    }
}