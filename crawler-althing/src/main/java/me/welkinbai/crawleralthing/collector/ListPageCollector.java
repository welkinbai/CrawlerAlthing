package me.welkinbai.crawleralthing.collector;

import me.welkinbai.crawleralthing.pagemodel.ListPage;
import me.welkinbai.crawleralthing.pagemodel.PageManager;
import me.welkinbai.crawleralthing.path.ListPath;
import me.welkinbai.crawleralthing.path.Path;
import me.welkinbai.crawleralthing.path.PathManager;
import me.welkinbai.crawleralthing.path.PathType;
import me.welkinbai.crawleralthing.path.RawPath;
import me.welkinbai.crawleralthing.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class ListPageCollector implements Collector {
    private static final Logger logger = LoggerFactory.getLogger(ListPageCollector.class);

    @Autowired
    private PathManager pathManager;
    @Autowired
    private PageManager pageManager;

    @Override
    public void run() {
        while (true) {
            try {
                ListPath path = (ListPath) pathManager.pollPath(PathType.LIST_PATH);
                if (path != null) {
                    doCollect(path);
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                logger.info("meet InterruptedException.", e);
                Thread.interrupted();
            } catch (Exception e) {
                logger.error("meet Exception", e);
            }
        }
    }

    private void doCollect(ListPath path) {
        try {
            logger.info("will collect path:{}", path);
            Document document = Jsoup.connect(path.getUrl()).get();
            Elements lists = document.select("li:has(a[href])");
            if (lists.isEmpty()) {
                logger.info("not found li has a with href elements.path:{}", path);
                return;
            }
            ListPage listPage = new ListPage(path);
            for (Element element : lists) {
                Elements aEles = element.select("a");
                for (Element aEle : aEles) {
                    String url = extractUrl(aEle);
                    RawPath rawPath = new RawPath(url);
                    String title = extractTitle(aEle);
                    rawPath.setTitle(title);
                    Path sameUrlPathInPage = listPage.getSameUrlPathInPage(rawPath);
                    if (sameUrlPathInPageNullOrNotFull(sameUrlPathInPage, rawPath)) {
                        listPage.addPath(rawPath);
                    }
                }
            }
            if (!listPage.isEmpty()) {
                pageManager.addPage(listPage);
            }
            logger.info("collect done for path:{}", path);
//            logger.info("listPage:{}", listPage);
        } catch (IOException e) {
            logger.error("meet IOException when doCollect.path:{}", path, e);
        }
    }

    private boolean sameUrlPathInPageNullOrNotFull(Path sameUrlPathInPage, RawPath rawPath) {
        if (sameUrlPathInPage == null || sameUrlPathInPage.getPathType() != PathType.RAW_PATH) {
            return true;
        }
        RawPath sameUrlPathInPageRaw = (RawPath) sameUrlPathInPage;
        return StringUtils.isEmpty(sameUrlPathInPageRaw.getTitle()) && StringUtils.isNotEmpty(rawPath.getTitle());
    }

    private String extractTitle(Element aEle) {
        String text = aEle.text();
        String title = aEle.attr("title");
        return StringUtils.isNotEmpty(title) ? title : text;
    }

    private String extractUrl(Element aEle) {
        return aEle.attr("abs:href");
    }
}
