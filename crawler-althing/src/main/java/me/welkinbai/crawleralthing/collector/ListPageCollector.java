package me.welkinbai.crawleralthing.collector;

import me.welkinbai.crawleralthing.pagemodel.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ListPageCollector implements Collector {
    private static final Logger logger = LoggerFactory.getLogger(ListPageCollector.class);

    @Override
    public Page collect(String path) {
        return null;
    }
}
