package com.johnny.algorithm.knuthmorrispratt;

import java.util.Arrays;

/**
 * @author johnnyhao
 *
 * KMP算法
 *
 * 暴力搜索
 * - 匹配不匹配，回退到匹配开始时的下一位重新开始匹配
 *
 * KMP搜索
 * - 利用之前判断过信息，通过一个部分匹配表，保存模式串中前后最长公共子序列的长度，每次回溯时，通过部分匹配表找到，前面匹配过的位置，省去了大量的计算时间
 */
public class KnuthMorrisPratt {

    public static void main(String[] args) {
        String srcString = "BBC ABCDAB ABCDABCDABDE";
        String targetString = "ABCDABD";

        int index = violenceSearch(srcString, targetString);
        System.out.printf("暴力搜索，下标为：%d\n", index);

        int[] next = kmpNext(targetString);
        System.out.printf("KMP部分匹配表：%s\n", Arrays.toString(next));

        index = kmpSearch(srcString, targetString, next);
        System.out.printf("KMP搜索，下标为：%d\n", index);
    }

    /**
     * 暴力搜索
     * @param srcString 源字符串
     * @param targetString 目标字符串
     * @return 下标
     */
    public static int violenceSearch(String srcString, String targetString) {
        // 定义指针分别指向两字符数组
        int i = 0;
        int j = 0;

        // 遍历源字符数组
        while (i < srcString.length() && j < targetString.length()) {
            // 如果和目标字符匹配，继续匹配下一位
            if (srcString.charAt(i) == targetString.charAt(j)) {
                i++;
                j++;
            }
            // 如果和目标字符不匹配，回退到匹配开始时的下一位重新开始匹配
            else {
                i = i - j + 1;
                j = 0;
            }
        }

        // 目标字符数组的指针遍历完了整个目标字符数组，则匹配成功，返回匹配开始时的下标
        if (j == targetString.length()) {
            return i - j;
        }
        // 否则返回-1
        else {
            return -1;
        }
    }

    /**
     * KMP搜索
     * @param srcString 源字符串
     * @param targetString 目标字符串
     * @param next 目标字符串部分匹配表
     * return 下标
     */
    public static int kmpSearch(String srcString, String targetString, int[] next) {
        //遍历
        for(int i = 0, j = 0; i < srcString.length(); i++) {
            // KMP算法核心点
            // j为当前位置的部分匹配值，当匹配不上时需将j回退回next[j - 1]
            while( j > 0 && srcString.charAt(i) != targetString.charAt(j)) {
                j = next[j-1];
            }

            if(srcString.charAt(i) == targetString.charAt(j)) {
                j++;
            }
            if(j == targetString.length()) {
                return i - j + 1;
            }
        }
        return  -1;
    }

    /**
     * 生成目标字符串部分匹配表
     * @param targetString 目标字符串
     * @return 目标字符串部分匹配表
     */
    public static int[] kmpNext(String targetString) {
        // 定义部分匹配表
        int[] next = new int[targetString.length()];

        // 如果字符串是长度为1时部分匹配值就为0，所以从第二位开始计算部分匹配值
        for(int i = 1, j = 0; i < targetString.length(); i++) {
            // KMP算法核心点
            // j为当前位置的部分匹配值，当匹配不上时需将j回退回next[j - 1]
            while(j > 0 && targetString.charAt(i) != targetString.charAt(j)) {
                j = next[j - 1];
            }

            // 当匹配不上时，部分匹配值加1
            if(targetString.charAt(i) == targetString.charAt(j)) {
                j++;
            }

            // 记录当前位置的部分匹配值
            next[i] = j;
        }
        return next;
    }
}
