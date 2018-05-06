package me.welkinbai.crawleralthing.dispatcher;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectorThreadFactory implements ThreadFactory {
    private static AtomicInteger count = new AtomicInteger(0);
    private String collectorName;

    public CollectorThreadFactory(String collectorName) {
        this.collectorName = collectorName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "Collector-" + collectorName + "-Thread-" + count.addAndGet(1));
    }
}
