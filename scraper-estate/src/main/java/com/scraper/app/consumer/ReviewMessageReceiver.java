package com.scraper.app.consumer;


import com.scraper.app.producer.avro.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class ReviewMessageReceiver implements BeanFactoryAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewMessageReceiver.class);
    private BeanFactory beanFactory;
    @KafkaListener(topics = "udemy-reviews")
    public void receive(Review reviewConsumerRecord) {
        LOGGER.info("received value='{}'", reviewConsumerRecord.toString());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
