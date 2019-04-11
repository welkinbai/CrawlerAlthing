package me.welkinbai.crawleralthing.dispatcher;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private static AtomicInteger count = new AtomicInteger(0);
    private String name;
    private String prefix;

    public NamedThreadFactory(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + name + "-Thread-" + count.addAndGet(1));
    }
}
