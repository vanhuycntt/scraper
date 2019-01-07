package com.scraper.app.producer;

import com.scraper.app.config.KafkaBeanFactoryPostProcessor;
import com.scraper.app.producer.avro.Review;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka")
    public KafkaProducerProperties producerProperties() {
        return new KafkaProducerProperties();
    }


    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        KafkaProducerProperties clientProperties = producerProperties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, clientProperties.getBootstrapUrls());
        props.put(ProducerConfig.ACKS_CONFIG, clientProperties.getProducer().getAcks());
        props.put(ProducerConfig.RETRIES_CONFIG, clientProperties.getProducer().getRetries());

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, clientProperties.getRegistryUrl());

        return props;
    }

    @Bean
    public ProducerFactory<String, Review> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Review> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public static KafkaBeanFactoryPostProcessor kafkaBeanFactoryPostProcessor() {
        return new KafkaBeanFactoryPostProcessor();
    }
}
