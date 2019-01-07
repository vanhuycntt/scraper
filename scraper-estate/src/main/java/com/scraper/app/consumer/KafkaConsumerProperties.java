package com.scraper.app.consumer;


import java.util.List;


public class KafkaConsumerProperties {
    private List<String> bootstrapUrls;
    private String registryUrl;
    private ConsumerProps consumer;

    public ConsumerProps getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerProps consumer) {
        this.consumer = consumer;
    }

    public List<String> getBootstrapUrls() {
        return bootstrapUrls;
    }

    public void setBootstrapUrls(List<String> bootstrapUrls) {
        this.bootstrapUrls = bootstrapUrls;
    }

    public String getRegistryUrl() {
        return registryUrl;
    }

    public void setRegistryUrl(String registryUrl) {
        this.registryUrl = registryUrl;
    }


    public static class ConsumerProps {
        private String groupId;
        private int zookeeperSessionTimeoutMs;
        private int zookeeperSyncTimeMs;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getZookeeperSessionTimeoutMs() {
            return zookeeperSessionTimeoutMs;
        }

        public void setZookeeperSessionTimeoutMs(int zookeeperSessionTimeoutMs) {
            this.zookeeperSessionTimeoutMs = zookeeperSessionTimeoutMs;
        }

        public int getZookeeperSyncTimeMs() {
            return zookeeperSyncTimeMs;
        }

        public void setZookeeperSyncTimeMs(int zookeeperSyncTimeMs) {
            this.zookeeperSyncTimeMs = zookeeperSyncTimeMs;
        }
    }
   }
