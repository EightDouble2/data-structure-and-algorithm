package com.johnny.datastructure.stack;

import java.util.Scanner;

/**
 * @author johnnyhao
 *
 * 栈
 *
 * - 栈(stack)是一个先入后出(FILO-First In Last Out)的有序列表。
 * - 栈是限制线性表中元素的插入和删除只能在线性表的同一端进行的一种特殊线性表
 *   允许插入和删除的一端，为变化的一端，称为栈顶(Top)，另一端为固定的一端，称为栈底(Bottom)。
 * - 根据栈的定义可知，最先放入栈中元素在栈底，最后放入的元素在栈顶，而删除元素刚好相反，最后放入的元素最先删除，最先放入的元素最后删除
 * - 入栈(push)：插入数据，出栈(pop)：取出数据
 *
 * 应用场景：
 * - 子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，直到子程序执行完后再将地址取出，以回到原来的程序中
 * - 处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆栈中
 * - 表达式的转换[中缀表达式转后缀表达式]与求值(实际解决)
 * - 二叉树的遍历
 * - 图形的深度优先(depth 一 first)搜索法
 */
public class ArrayStack {

    public static void main(String[] args) {
        // 创建栈
        ArrayStackImpl arrayStack = new ArrayStackImpl(5);

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
                        arrayStack.push(value);
                    }
                    catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try {
                        System.out.printf("出栈：%d\n", arrayStack.pop());
                    }
                    catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 's':
                    try {
                        arrayStack.show();
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
 * 数组模拟栈
 */
class ArrayStackImpl {

    /**
     * 最大容量
     */
    private final int maxSize;

    /**
     * 数组模拟栈
     */
    private final int[] stack;

    /**
     * 栈顶，初始化为-1
     */
    private int top = -1;

    /**
     * 构造器
     */
    public ArrayStackImpl(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[this.maxSize];
    }

    /**
     * 判断栈满
     * @return boolean
     */
    public boolean isFull() {
        return this.top == this.maxSize - 1;
    }

    /**
     * 判断栈空
     * @return boolean
     */
    public boolean isEmpty() {
        return this.top == -1;
    }

    /**
     * 入栈
     * @param value 参数
     */
    public void push(int value) {
        // 判断栈满
        if (isFull()) {
            throw new RuntimeException("栈满");
        }
        // 栈顶后移，插入数据
        top++;
        stack[top] = value;
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
        // 取出数据，栈顶前移
        int value = stack[top];
        top--;
        return value;
    }

    /**
     * 遍历打印栈
     */
    public void show() {
        // 判断栈空
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }
        // 遍历打印栈
        for (int i = top; i > -1; i--) {
            System.out.println(stack[i]);
        }
    }
}
