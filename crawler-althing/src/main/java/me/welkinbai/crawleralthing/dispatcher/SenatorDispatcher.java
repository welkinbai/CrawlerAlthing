package me.welkinbai.crawleralthing.dispatcher;

import me.welkinbai.crawleralthing.althing.Senator;
import me.welkinbai.crawleralthing.config.Config;
import me.welkinbai.crawleralthing.config.Config.WorkerConfig;
import me.welkinbai.crawleralthing.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SenatorDispatcher extends AbstractDispatcher implements Dispatcher {
    private static final Logger logger = LoggerFactory.getLogger(SenatorDispatcher.class);

    @Autowired
    private Config config;
    private List<Senator> senatorPool;

    @Override
    public void run() {
        logger.info("SenatorDispatcher开始启动");
        List<WorkerConfig> senatorConfigs = getSenatorConfigs();
        for (WorkerConfig workerConfig : senatorConfigs) {
            Senator aSenator = applicationContext().getBean(workerConfig.getName(), Senator.class);
            senatorPool().add(aSenator);
            int threadCount = workerConfig.getThreadCount();
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount, new NamedThreadFactory("Senator-", workerConfig.getName()));
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(aSenator);
            }
            serviceMap().put(aSenator, executorService);
        }
        if (CollectionUtils.isEmpty(senatorConfigs)) {
            runDefaultSenator();
        }
    }

    private void runDefaultSenator() {
        Senator defaultSenator = applicationContext().getBean("defaultSenator", Senator.class);
        senatorPool().add(defaultSenator);
        ExecutorService executorService = Executors.newFixedThreadPool(1, new NamedThreadFactory("Senator-", "defaultSenator"));
        executorService.execute(defaultSenator);
        serviceMap().put(defaultSenator, executorService);
    }

    private List<Senator> senatorPool() {
        if (senatorPool == null) {
            senatorPool = new ArrayList<>();
        }
        return senatorPool;
    }

    private List<WorkerConfig> getSenatorConfigs() {
        return config.getSenator() == null ? Collections.emptyList() : config.getSenator();
    }
}
