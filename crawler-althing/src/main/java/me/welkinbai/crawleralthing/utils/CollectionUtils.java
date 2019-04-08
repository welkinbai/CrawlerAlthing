package me.welkinbai.crawleralthing.utils;

import java.util.Collection;

public class CollectionUtils {
    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return collection != null && !collection.isEmpty();
    }
}
