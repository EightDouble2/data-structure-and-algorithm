package com.johnny.datastructure.stack;

import java.util.Stack;

/**
 * @author johnnyhao
 *
 * 使用栈实现计算器(中缀表达式)
 */
public class Calculator {

    public static void main(String[] args) {
        // 表达式
        String expression = "5+70*2-2+5+2*6+10";

        // 创建数字栈和符号栈
        Stack<Integer> numStack = new Stack<Integer>();
        Stack<Character> opeStack = new Stack<Character>();

        for (int i = 0; i < expression.length(); i++) {
            // 取出一个字符
            char ch = expression.charAt(i);

            // 判断字符是否为符号
            if (isOpe(ch)) {
                // 若符号栈不为空并且符号栈顶的符号比当前符号优先级高，在数字栈中pop两个数，在符号栈中pop一个符号，进行运算，将结果压入数字栈
                if (!opeStack.isEmpty() && priority(opeStack.peek(), ch)) {
                    numStack.push(calculate(numStack.pop(), numStack.pop(), opeStack.pop()));
                }
                // 将当前符号压入符号栈
                opeStack.push(ch);
            }
            else {
                // 若为数字，向后遍历，若还为数字进行拼接
                int num = Integer.parseInt(String.valueOf(ch));
                i++;
                while (i < expression.length() && !isOpe(expression.charAt(i))) {
                    num = num * 10 + Integer.parseInt(String.valueOf(expression.charAt(i)));
                    i++;
                }
                // 跳出循环，将得到的数字压入数字栈，计数器复位
                numStack.push(num);
                i--;
            }
        }

        // 当扫描完毕，顺序从数字栈和符号栈中pop出数字和符号，并计算
        // 当符号栈为空，则计算完毕
        while (!opeStack.isEmpty()) {
            numStack.push(calculate(numStack.pop(), numStack.pop(), opeStack.pop()));
        }

        // 此时数字栈中仅剩最后一个数字，即为结果
        System.out.printf("表达式%s=%d", expression, numStack.pop());
    }

    /**
     * 判断字符是否为符号
     * @param ch 字符
     * @return 返回true为符号
     */
    public static boolean isOpe(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * 判断字符优先级
     * @param ope1 字符
     * @param ope2 字符
     * @return 返回true为ope1的优先级高或相同
     */
    public static boolean priority(char ope1, char ope2) {
        return (ope1 == '*' || ope1 == '/' ? 1 : 0) - (ope2 == '*' || ope2 == '/' ? 1 : 0) >= 0;
    }

    /**
     * 计算
     * @param num2 第二个数字
     * @param num1 第一个数字
     * @param ope 符号
     * @return 返回结果
     */
    public static int calculate(int num2, int num1, char ope) {
        if (ope == '+') {
            return num1 + num2;
        }
        else if (ope == '-') {
            return num1 - num2;
        }
        else if (ope == '*') {
            return num1 * num2;
        }
        else if (ope == '/') {
            return num1 / num2;
        }
        return 0;
    }
}
