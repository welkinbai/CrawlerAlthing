package me.welkinbai.crawleralthing.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractDispatcher implements Dispatcher, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDispatcher.class);

    private Map<Object, ExecutorService> serviceMap;
    private ApplicationContext applicationContext;

    protected void doDestroy() {
        if (serviceMap != null) {
            for (Entry<Object, ExecutorService> entry : serviceMap.entrySet()) {
                try {
                    ExecutorService value = entry.getValue();
                    value.shutdown();
                    value.awaitTermination(2, TimeUnit.SECONDS);
                    value.shutdownNow();
                } catch (InterruptedException e) {
                    logger.error("await termination interrupted:{}", Thread.interrupted());
                } catch (Exception e) {
                    logger.error("shutdown service meet exception", e);
                }
            }
        }
    }

    protected Map<Object, ExecutorService> serviceMap() {
        if (serviceMap == null) {
            serviceMap = new HashMap<>();
        }
        return serviceMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected ApplicationContext applicationContext() {
        return applicationContext;
    }

    @PreDestroy
    public void destroy() {
        doDestroy();
    }
}
