package com.demo.userservice.consumers.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="kafka.topics.user-created")
@Getter
@Setter
public class UserCreatedConsumerProperties {
    private String topic;
    private String consumerGroup;
    private short replicationFactor;
    private String retentionInMs;

}
