package com.scraper.app.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "kafka")
public class KafkaProducerProperties {
    private List<String> bootstrapUrls;
    private String registryUrl;
    private ProducerProps producer;

    public ProducerProps getProducer() {
        return producer;
    }

    public void setProducer(ProducerProps producer) {
        this.producer = producer;
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


    public static class ProducerProps {
        private String acks;
        private int retries;

        public String getAcks() {
            return acks;
        }

        public void setAcks(String acks) {
            this.acks = acks;
        }

        public int getRetries() {
            return retries;
        }

        public void setRetries(int retries) {
            this.retries = retries;
        }

    }
   }
