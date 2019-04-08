package me.welkinbai.crawleralthing.pagemodel;

import me.welkinbai.crawleralthing.path.PathType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class PageManager {
    private Map<PathType, BlockingQueue<Page>> pageMap = new HashMap<>();

    public boolean addPage(Page page) {
        PathType pathType = page.getPath().getPathType();
        ensurePageQueue(pathType);
        return pageMap.get(pathType).offer(page);
    }

    private void ensurePageQueue(PathType pathType) {
        BlockingQueue<Page> pages = pageMap.get(pathType);
        if (pages == null) {
            pageMap.put(pathType, new LinkedBlockingQueue<>());
        }
    }
}
