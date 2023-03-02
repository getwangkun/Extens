package com.cdvtc.news.util;

import java.util.ArrayList;

/**
 * 调试Demo，用于Debug操作练习
 */
public class DebugDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("tom");
        list.add("rose");
        list.add("jack");

        String str = concatListToStr(list);
        System.out.println(str);
    }

    private static String concatListToStr(ArrayList<String> list) {
        if (list == null) {
            return "";
        }
        String result = "";
        for (String itemStr : list) {
            result += itemStr;
        }
        String helloWord = sayHello() + sayWorld();
        return result;
    }

    private static String sayHello() {
        System.out.println("hello");
        return "hello";
    }

    private static String sayWorld() {
        System.out.println("world");
        return "world";
    }
}
