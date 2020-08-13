package com.johnny.datastructure.stack;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Scanner;

/**
 * @author johnnyhao
 *
 * 链表模拟栈
 */
public class LinkedStack {

    public static void main(String[] args) {
        // 创建栈
        LinkedStackImpl linkedStack = new LinkedStackImpl();

        // 输出菜单
        char key;
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("a(push)：入栈");
            System.out.println("g(pop)：出栈");
            System.out.println("s(show)：显示栈");
            System.out.println("e(exit)：退出");
            key = scanner.next().charAt(0);

            switch (key) {
                case 'a':
                    System.out.println("输入一个数：");
                    int value = scanner.nextInt();
                    try {
                        linkedStack.push(value);
                    }
                    catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try {
                        System.out.printf("出栈：%d\n", linkedStack.pop());
                    }
                    catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 's':
                    try {
                        linkedStack.show();
                    }
                    catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }
}

/**
 * 节点
 */
@ToString(exclude = "next")
@Getter
@Setter
@RequiredArgsConstructor
class Node {
    /**
     * 数据
     */
    @NonNull
    private int value;

    /**
     * 下一节点
     */
    private Node next;
}

/**
 * 链表模拟栈
 */
class LinkedStackImpl {
    /**
     * 初始化头节点，定义链表头部，不存放具体数据
     */
    private final Node head = new Node(0);

    /**
     * 判断栈空
     * @return boolean
     */
    public boolean isEmpty() {
        return this.head.getNext() == null;
    }

    /**
     * 入栈
     * @param value 参数
     */
    public void push(int value) {
        // 头插法插入数据
        Node node = new Node(value);
        node.setNext(head.getNext());
        head.setNext(node);
    }

    /**
     * 出栈
     * @return 返回值
     */
    public int pop() {
        // 判断栈空
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }
        // 取出第一个节点
        Node node = head.getNext();
        head.setNext(head.getNext().getNext());
        return node.getValue();
    }

    /**
     * 遍历打印栈
     */
    public void show() {
        // 判断栈空
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }

        // 创建一个temp辅助遍历链表，用于找到目标节点
        Node temp = head.getNext();

        // 遍历打印栈
        while (temp != null) {
            System.out.println(temp.getValue());
            temp = temp.getNext();
        }
    }
}