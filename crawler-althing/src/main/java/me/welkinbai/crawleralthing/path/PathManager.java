package me.welkinbai.crawleralthing.path;

import me.welkinbai.crawleralthing.config.PathPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class PathManager {
    private Map<Integer, BlockingQueue<Path>> targetPathQueueMap = new HashMap<>();

    @Autowired
    private PathPool pathPool;

    @PostConstruct
    public void init() {
        List<String> listPaths = pathPool.getList();
        if (!CollectionUtils.isEmpty(listPaths)) {
            LinkedBlockingDeque<Path> listPathDeque = new LinkedBlockingDeque<>();
            for (String listPath : listPaths) {
                listPathDeque.offer(new ListPath(listPath));
            }
            targetPathQueueMap.put(PathType.LIST_PATH.getCode(), listPathDeque);
        }
    }

    public Path pollPath(PathType pathType) {
        BlockingQueue<Path> paths = targetPathQueueMap.get(pathType.getCode());
        return paths == null ? null : paths.poll();
    }
}
