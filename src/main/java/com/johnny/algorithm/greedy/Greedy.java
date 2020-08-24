package com.johnny.algorithm.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author johnnyhao
 *
 * 贪心算法
 *
 * 在对问题进行求解时，在每一步选择中都采取最好或者最优(即最有利)的选择，从而希望能够导致结果是最好或者最优的算法
 *
 * 贪婪算法所得到的结果不一定是最优的结果(有时候会是最优解)，但是都是相对近似(接近)最优解的结果
 */
public class Greedy {

    public static void main(String[] args) {
        // 初始化电台集合
        Map<String, Set<String>> radioStations = new HashMap<String, Set<String>>(16){
            {
                put("K1", new HashSet<String>(){
                    {
                        add("北京");
                        add("上海");
                        add("天津");
                    }
                });
                put("K2", new HashSet<String>(){
                    {
                        add("广州");
                        add("北京");
                        add("深圳");
                    }
                });
                put("K3", new HashSet<String>(){
                    {
                        add("成都");
                        add("上海");
                        add("杭州");
                    }
                });
                put("K4", new HashSet<String>(){
                    {
                        add("上海");
                        add("天津");
                    }
                });
                put("K5", new HashSet<String>(){
                    {
                        add("杭州");
                        add("大连");
                    }
                });
            }
        };

        // 使用贪心算法得出选择的电台
        List<String> selectRadioStations = setCovering(radioStations);

        System.out.println(selectRadioStations);
    }

    /**
     * 集合覆盖问题
     * 选择最少的广播台，让所有的地区都可以接收到信号
     * @param radioStations 电台集合
     * @return 选择的电台集合
     */
    public static List<String> setCovering(Map<String, Set<String>> radioStations) {
        // 初始化选择电台的集合
        List<String> selectRadioStations = new ArrayList<>();

        // 遍历电台，获取所有地区
        Set<String> allAreas = new HashSet<>();
        radioStations.forEach((key, value) -> allAreas.addAll(value));

        // 遍历所有电台直到已覆盖所有地区
        while (!allAreas.isEmpty()) {
            // 定义maxKey用于存放能够覆盖最大未覆盖的地区对应的电台的key
            String[] maxKey = {null};

            // 贪心算法遍历所有电台，每次选择出最优电台
            radioStations.forEach((key, value) -> {
                // 定义一个记录此次覆盖地区的辅助集合，先将当前电台的地区赋值给辅助集合
                Set<String> tempAreas = new HashSet<>(value);
                // 再让辅助集合与所有地区集合取交集，求出此次循环所覆盖的地区
                tempAreas.retainAll(allAreas);

                // 判断此次循环所覆盖的地区是否比之前循环得到的最大未覆盖地区对应的电台
                boolean flag = !tempAreas.isEmpty() && (maxKey[0] == null || tempAreas.size() > radioStations.get(maxKey[0]).size());

                // 如果大于就将最大未覆盖地区对应的电台置为当前电台
                if (flag) {
                    maxKey[0] = key;
                }
            });

            // 如果maxKey不为空，则说明找到了此次循环的最优电台
            if (maxKey[0] != null) {
                // 将找到电台添加到选择电台的集合中
                selectRadioStations.add(maxKey[0]);
                // 在所有地区集合中去除掉此次覆盖的地区
                allAreas.removeAll(radioStations.get(maxKey[0]));
            }
        }

        // 返回选择电台的集合
        return selectRadioStations;
    }
}
