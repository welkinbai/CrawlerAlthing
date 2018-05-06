package me.welkinbai.crawleralthing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrawlerAlthingApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerAlthingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrawlerAlthingApplication.class, args);
        LOGGER.info("启动完毕...");
    }
}
