package com.javarush.task.task22.task2208;

import java.util.HashMap;
import java.util.Map;

/* 
Формируем WHERE
*/
public class Solution {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Ivanov");
        map.put("country", "Ukraine");
        map.put("city", "Kiev");
        map.put("age", null);

        System.out.println(getQuery(map));
    }

    public static String getQuery(Map<String, String> params) {

        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, String> str : params.entrySet()) {
            if (str.getValue() != null) {
                if (index > 0) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append(str.getKey()).append(" = '").append(str.getValue()).append("'");
                index++;
            }
        }

        return stringBuilder.toString();
    }
}
