package com.johnny.datastructure.queue;

import java.util.Scanner;

/**
 * @author johnnyhao
 *
 * 队列
 *
 * 队列是一个有序列表，可以用数组或链表来实现
 * 遵循先入先出的原则，即：先存入队列的数据先取出，后存入队列的数据后取出
 */
public class ArrayQueue {

    public static void main(String[] args) {
        // 创建队列
        ArrayQueueImpl arrayQueue = new ArrayQueueImpl(5);

        // 输出菜单
        char key;
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("a(add)：添加数据");
            System.out.println("g(get)：取出数据");
            System.out.println("s(show)：显示队列");
            System.out.println("e(exit)：退出");
            key = scanner.next().charAt(0);

            switch (key) {
                case 'a':
                    System.out.println("输入一个数：");
                    int data = scanner.nextInt();
                    try {
                        arrayQueue.add(data);
                    }
                    catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try {
                        System.out.printf("输出数据：%d\n", arrayQueue.get());
                    }
                    catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 's':
                    try {
                        arrayQueue.show();
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
 * 使用数组模拟队列
 */
class ArrayQueueImpl {

    /**
     * 数组最大容量
     */
    private final int maxSize;

    /**
     * 队列头
     */
    private int front;

    /**
     * 队列尾
     */
    private int rear;

    /**
     * 存放数据的数组，模拟队列
     */
    private final int[] arr;

    /**
     * 创建队列的构造器
     * @param maxSize 数组最大容量
     */
    public ArrayQueueImpl(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
        front = -1;
        rear = -1;
    }

    /**
     * 判断队列是否已满
     * @return boolean
     */
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    /**
     * 判断队列是否为空
     * @return boolean
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /**
     * 添加数据到队列
     * @param data 添加的数据
     */
    public void add(int data) {
        // 判断队列是否已满
        if (isFull()) {
            throw new RuntimeException("队列已满");
        }
        rear++;
        arr[rear] = data;
    }

    /**
     * 获取队列数据
     * @return 获取的数据
     */
    public int get() {
        // 判断列队是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        front++;
        return arr[front];
    }

    /**
     * 显示队列
     */
    public void show() {
        // 判断列队是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        for (int i = front + 1; i < rear; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }
}
