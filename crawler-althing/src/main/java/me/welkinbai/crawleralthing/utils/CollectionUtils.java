package me.welkinbai.crawleralthing.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }
}
