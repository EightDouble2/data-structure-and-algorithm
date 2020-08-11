package com.johnny.datastructure.queue;

import java.util.Scanner;

/**
 * @author johnnyhao
 *
 * 环形队列
 *
 * 对普通数组模拟队列进行优化优化，充分利用数组
 * 将数组看做是一个环形的(通过取模的方式来实现即可)
 */
public class RingArrayQueue {

    public static void main(String[] args) {
        // 创建队列
        RingArrayQueueImpl arrayQueue = new RingArrayQueueImpl(5);

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
 * 使用数组模拟环形队列
 */
class RingArrayQueueImpl {

    /**
     * 数组最大容量
     */
    private final int maxSize;

    /**
     * 队列头(指向列队第一个元素)
     */
    private int front;

    /**
     * 队列尾(指向列队最后一个元素的后一个位置，预留一个位置是为了区分空和满)
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
    public RingArrayQueueImpl(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
        front = 0;
        rear = 0;
    }

    /**
     * 判断队列是否已满
     * @return boolean
     */
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        // 直接添加数据，然后将rear后移取模
        arr[rear] = data;
        rear = (rear + 1) % maxSize;
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
        int data = arr[front];
        front = (front + 1) % maxSize;
        return data;
    }

    /**
     * 显示队列
     */
    public void show() {
        // 判断列队是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }

        // 求出当前队列的有效数据个数
        int size = (rear + maxSize - front) % maxSize;

        // 从front开始遍历
        for (int i = front; i < front + size; i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }
}
