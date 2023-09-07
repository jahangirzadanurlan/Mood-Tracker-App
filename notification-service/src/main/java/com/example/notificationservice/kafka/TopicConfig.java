package com.example.notificationservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic firstTopic(){
        return TopicBuilder.name("subscribe-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic secondTopic(){
        return TopicBuilder.name("report-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
