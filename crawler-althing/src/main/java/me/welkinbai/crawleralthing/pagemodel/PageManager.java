package me.welkinbai.crawleralthing.pagemodel;

import me.welkinbai.crawleralthing.path.PathType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

    public List<Page> popAllPage() {
        List<Page> allPage = new ArrayList<>();
        for (Entry<PathType, BlockingQueue<Page>> entry : pageMap.entrySet()) {
            BlockingQueue<Page> queue = entry.getValue();
            if (queue != null) {
                Page pageInQueue;
                while ((pageInQueue = queue.poll()) != null) {
                    allPage.add(pageInQueue);
                }
            }
        }
        return allPage;
    }
}
