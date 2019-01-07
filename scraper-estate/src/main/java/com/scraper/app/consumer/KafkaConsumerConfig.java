package com.scraper.app.consumer;

import com.scraper.app.producer.avro.Review;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka")
    public KafkaConsumerProperties consumerProperties() {
        return new KafkaConsumerProperties();
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        KafkaConsumerProperties clientProperties = consumerProperties();
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, clientProperties.getBootstrapUrls());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, clientProperties.getConsumer().getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, clientProperties.getRegistryUrl());

        return props;
    }

    @Bean
    public ConsumerFactory<String, Review> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Review> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Review> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //setting number of concurrent threads which will read from kafka topic,
        //basically it's a number of consumers in consumer group
        //it has concurrency 5, because number of partition in Kafka topic is 5, 1 thread per partition.
        factory.setConcurrency(2);
        //setting number of ms when to emit "notification" if kafka consumer is idle,
        //e.g. no more messages in kafka topic
        //factory.getContainerProperties().setIdleEventInterval(3000L);

        return factory;
    }


}
