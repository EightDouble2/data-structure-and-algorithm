package com.johnny.datastructure.stack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author johnnyhao
 *
 * 逆波兰计算器
 *
 * 中缀表达式：就是常见的运算表达式，如(3+4)×5-6
 *           - 中缀表达式对人是最熟悉的，但是对于计算器来说却不好操作，所以往往会将中缀表达式换成其他表达式进行操作(一般转成后缀表达式)
 * 前缀表达式：波兰表达式，- * + 3 4 5 6
 *           - 从右至左扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算，并将结果入栈
 *           - 重复上述过程直到表达式最左端，最后运算得出的值即为表达式的结果
 * 后缀表达式：逆波兰表达式，3 4 + 5 * 6 -
 *           - 从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算，并将结果入栈
 *           - 重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
 */
public class ReversePolishNotationCalculator {

    public static void main(String[] args) {
        // 中缀表达式
        String expression = "1+((2+3)*4)-5";

        // 中缀表达式转换为后缀表达式的List
        List<String> suffixExpressionList = toSuffixExpression(expression);
        System.out.printf("后缀表达式的List：%s\n", suffixExpressionList);

        // 计算后缀表达式结果
        int result = calculate(suffixExpressionList);
        System.out.printf("结果：%d\n", result);
    }

    /**
     * 中缀表达式转后缀表达式
     *
     * 转换方法：
     * 1.初始化两个栈：运算符栈s1和储存中间结果的栈s2；
     * 2.从左至右扫描中缀表达式；
     * 3.遇到操作数时，将其压s2；
     * 4.遇到运算符时，比较其与s1栈顶运算符的优先级：
     *   1）如果s1为空，或栈顶运算符为左括号"("，则直接将此运算符入栈；
     *   2）否则，若优先级比栈顶运算符的高，也将运算符压入s1；
     *   3）否则，将s1栈顶的运算符弹出并压入到s2中，再次转到(4-1)与s1中新的栈顶运算符相比较；
     * 5.遇到括号时：
     *   1）如果是左括号"("，则直接压入s1
     *   2）如果是右括号")"，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
     * 6.重复步骤2至5，直到表达式的最右边
     * 7.将s1中剩余的运算符依次弹出并压入s2
     * 8.依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
     *
     * @param expression 中缀表达式
     * @return 后缀表达式
     */
    public static List<String> toSuffixExpression(String expression) {
        // 将中缀表达式转换成中缀表达式的List方便取值
        List<String> expressionList = new ArrayList<String>();

        // 遍历expression
        for (int i = 0; i < expression.length(); i++) {
            // 取出一个字符
            char ch = expression.charAt(i);

            // 将字符添加到中缀表达式的List
            expressionList.add(String.valueOf(ch));

            // 判断字符是否为数字
            if (String.valueOf(ch).matches("\\d")) {
                // 若为数字，向后遍历，若还为数字进行拼接
                i++;
                while (i < expression.length() && String.valueOf(expression.charAt(i)).matches("\\d")) {
                    expressionList.set(expressionList.size() - 1, expressionList.get(expressionList.size() - 1) + expression.charAt(i));
                    i++;
                }
                // 跳出循环，计数器复位
                i--;
            }
        }

        // 中缀表达式的List转换成后缀表达式List
        // 符号栈s1
        Stack<String> opeStack = new Stack<String>();
        // 因为中间结果栈s2不需要pop，而且需要逆序push，所以直接使用List
        List<String> suffixExpressionList = new LinkedList<String>();

        // 遍历中缀表达式的List
        expressionList.forEach(item -> {
            // 遇到操作数时，将其压入s2
            if (item.matches("\\d+")) {
                suffixExpressionList.add(item);
            }
            // 遇到左括号时，将其压入s1
            else if ("(".equals(item)) {
                opeStack.push(item);
            }
            // 遇到右括号时，依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
            else if (")".equals(item)) {
                while (!"(".equals(opeStack.peek())) {
                    suffixExpressionList.add(opeStack.pop());
                }
                // pop出"("
                opeStack.pop();
            }
            // 遇到符号时，
            else {
                // 如果s1为空，或栈顶运算符为左括号"("，则直接将运算符压入s1
                // 若优先级比栈顶运算符的高，也将运算符压入s1
                // 否则将s1栈顶的运算符弹出并压入到s2中，再重复判断
                while (!opeStack.isEmpty() && !"(".equals(opeStack.peek()) && priority(opeStack.peek(), item)) {
                    suffixExpressionList.add(opeStack.pop());
                }
                opeStack.push(item);
            }
        });

        // 将s1中剩余的运算符依次弹出并压入s2
        while (!opeStack.isEmpty()) {
            suffixExpressionList.add(opeStack.pop());
        }

        // 返回结果
        return suffixExpressionList;
    }

    /**
     * 判断字符优先级
     * @param ope1 字符
     * @param ope2 字符
     * @return 返回true为ope1的优先级高或相同
     */
    public static boolean priority(String ope1, String ope2) {
        return ("*".equals(ope1) || "/".equals(ope1) ? 1 : 0) - ("*".equals(ope2) || "/".equals(ope2) ? 1 : 0) >= 0;
    }

    /**
     * 后缀表达式计算
     * - 从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算，并将结果入栈
     * - 重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
     * @param suffixExpressionList 后缀表达式的List
     * @return 结果
     */
    public static int calculate(List<String> suffixExpressionList) {
        // 创建一个数字栈
        Stack<Integer> stack = new Stack<>();

        // 循环后缀表达式的List
        suffixExpressionList.forEach(item -> {
            // 如果为数字，则压入栈中
            if (item.matches("\\d+")) {
                stack.push(Integer.parseInt(item));
            }
            // 如果为符号，弹出栈顶的两个数，用运算符对它们做相应的计算，并将结果入栈
            else {
                stack.push(calculate(stack.pop(), stack.pop(), item));
            }
        });

        return stack.pop();
    }



    /**
     * 计算
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @param ope 符号
     * @return 返回结果
     */
    public static int calculate(int num2, int num1, String ope) {
        if ("+".equals(ope)) {
            return num1 + num2;
        }
        else if ("-".equals(ope)) {
            return num1 - num2;
        }
        else if ("*".equals(ope)) {
            return num1 * num2;
        }
        else if ("/".equals(ope)) {
            return num1 / num2;
        }
        return 0;
    }
}
