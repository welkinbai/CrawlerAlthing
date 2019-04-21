package me.welkinbai.crawleralthing.dispatcher;

import me.welkinbai.crawleralthing.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DispatcherTrigger implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherTrigger.class);

    private List<Dispatcher> dispatcherPool;

    @Autowired
    private Config config;
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        if (dispatcherPool == null) {
            dispatcherPool = new ArrayList<>();
            List<String> dispatcher = config.getDispatcher();
            if (!CollectionUtils.isEmpty(dispatcher)) {
                for (String dispatcherName : dispatcher) {
                    logger.info("调度器：{} 启动中..", dispatcherName);
                    Dispatcher aDispatcher = applicationContext.getBean(dispatcherName, Dispatcher.class);
                    dispatcherPool.add(aDispatcher);
                }
            } else {
                logger.warn("注意！！没有配置任何调度器！！");
            }
        }
        for (Dispatcher dispatcher : dispatcherPool) {
            dispatcher.run();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
