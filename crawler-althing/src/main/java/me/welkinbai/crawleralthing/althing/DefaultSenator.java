package me.welkinbai.crawleralthing.althing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultSenator implements LowSenator {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSenator.class);

    @Override
    public void run() {

    }
}
