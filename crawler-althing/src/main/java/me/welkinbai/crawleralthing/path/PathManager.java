package me.welkinbai.crawleralthing.path;

import me.welkinbai.crawleralthing.config.PathPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class PathManager {
    private BlockingDeque<Path> targetPathQueue = new LinkedBlockingDeque<>();

    @Autowired
    private PathPool pathPool;

    @PostConstruct
    public void init() {
        List<String> listPaths = pathPool.getList();
        if (!CollectionUtils.isEmpty(listPaths)) {
            for (String listPath : listPaths) {
                targetPathQueue.offer(new ListPath(listPath));
            }
        }
    }
}
