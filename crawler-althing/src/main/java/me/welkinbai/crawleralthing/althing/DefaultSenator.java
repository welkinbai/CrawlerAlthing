package me.welkinbai.crawleralthing.althing;

import me.welkinbai.crawleralthing.pagemodel.Page;
import me.welkinbai.crawleralthing.pagemodel.PageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class DefaultSenator implements LowSenator {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSenator.class);

    @Autowired
    private PageManager pageManager;

    @Override
    public void run() {
        while (true) {
            try {
                List<Page> pages = pageManager.popAllPage();
                for (Page page : pages) {
                    logger.info("[DefaultSenator]just print page:{}", page);
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                logger.warn("meet InterruptedException", e);
                Thread.interrupted();
            } catch (Exception e) {
                logger.error("meet Exception", e);
            }
        }
    }

}
