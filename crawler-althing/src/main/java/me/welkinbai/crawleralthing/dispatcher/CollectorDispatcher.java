package me.welkinbai.crawleralthing.dispatcher;

import me.welkinbai.crawleralthing.collector.Collector;
import me.welkinbai.crawleralthing.config.Config;
import me.welkinbai.crawleralthing.config.Config.CollectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class CollectorDispatcher implements Dispatcher, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(CollectorDispatcher.class);

    private List<Collector> collectorPool;
    private Map<Collector, ExecutorService> serviceMap;

    @Autowired
    private Config config;
    private ApplicationContext applicationContext;

    @Override
    public void run() {
        logger.info("CollectorDispatcher开始启动");
        List<CollectorConfig> collector = config.getCollector();
        if (CollectionUtils.isEmpty(collector)) {
            throw new IllegalStateException("配置未指定collector，至少要有一个收集器（collector）启动！");
        }
        if (collectorPool == null) {
            collectorPool = new ArrayList<>();
        }
        if (serviceMap == null) {
            serviceMap = new HashMap<>();
        }
        for (CollectorConfig collectorConfig : collector) {
            Collector aCollector = applicationContext.getBean(collectorConfig.getName(), Collector.class);
            collectorPool.add(aCollector);
            int threadCount = collectorConfig.getThreadCount();
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount, new CollectorThreadFactory(collectorConfig.getName()));
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(aCollector);
            }
            serviceMap.put(aCollector, executorService);
        }
    }

    @PreDestroy
    public void destroy() {
        if (serviceMap != null) {
            for (Entry<Collector, ExecutorService> entry : serviceMap.entrySet()) {
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

    public List<Collector> getCollectorPool() {
        return collectorPool;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
