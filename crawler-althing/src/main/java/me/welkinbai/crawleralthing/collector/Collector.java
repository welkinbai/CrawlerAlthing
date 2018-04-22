package me.welkinbai.crawleralthing.collector;

import me.welkinbai.crawleralthing.pagemodel.Page;

public interface Collector {
    Page collect(String path);


}
