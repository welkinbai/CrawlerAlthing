package me.welkinbai.crawleralthing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("config")
public class Config {

    private List<String> dispatcher;
    private List<WorkerConfig> collector;
    private List<WorkerConfig> senator;

    public List<String> getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(List<String> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public List<WorkerConfig> getCollector() {
        return collector;
    }

    public void setCollector(List<WorkerConfig> collector) {
        this.collector = collector;
    }

    public List<WorkerConfig> getSenator() {
        return senator;
    }

    public void setSenator(List<WorkerConfig> senator) {
        this.senator = senator;
    }

    public static class WorkerConfig {
        private String name;
        private int threadCount = 1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getThreadCount() {
            return threadCount;
        }

        public void setThreadCount(int threadCount) {
            this.threadCount = threadCount;
        }
    }
}
