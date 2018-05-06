package me.welkinbai.crawleralthing.collector;

import me.welkinbai.crawleralthing.path.PathManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListPageCollector implements Collector {
    private static final Logger logger = LoggerFactory.getLogger(ListPageCollector.class);

    @Autowired
    private PathManager pathManager;

    @Override
    public void run() {

    }
}
