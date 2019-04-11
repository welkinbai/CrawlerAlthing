package me.welkinbai.crawleralthing.dispatcher;

import me.welkinbai.crawleralthing.collector.Collector;
import me.welkinbai.crawleralthing.config.Config;
import me.welkinbai.crawleralthing.config.Config.WorkerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CollectorDispatcher extends AbstractDispatcher implements Dispatcher {
    private static final Logger logger = LoggerFactory.getLogger(CollectorDispatcher.class);

    private List<Collector> collectorPool;

    @Autowired
    private Config config;

    @Override
    public void run() {
        logger.info("CollectorDispatcher开始启动");
        List<WorkerConfig> collector = getCollectorConfigs();
        for (WorkerConfig workerConfig : collector) {
            Collector aCollector = applicationContext().getBean(workerConfig.getName(), Collector.class);
            collectorPool().add(aCollector);
            int threadCount = workerConfig.getThreadCount();
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount, new NamedThreadFactory("Collector-", workerConfig.getName()));
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(aCollector);
            }
            serviceMap().put(aCollector, executorService);
        }
    }

    private List<Collector> collectorPool() {
        if (collectorPool == null) {
            collectorPool = new ArrayList<>();
        }
        return collectorPool;
    }

    private List<WorkerConfig> getCollectorConfigs() {
        List<WorkerConfig> collector = config.getCollector();
        if (CollectionUtils.isEmpty(collector)) {
            throw new IllegalStateException("配置未指定collector，至少要有一个收集器（collector）启动！");
        }
        return collector;
    }

    public List<Collector> getCollectorPool() {
        return collectorPool;
    }

}
