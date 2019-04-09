package me.welkinbai.crawleralthing.utils;

public class StringUtils {
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
