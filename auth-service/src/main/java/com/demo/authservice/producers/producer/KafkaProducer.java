package com.demo.authservice.producers.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;


    public void sendMessage(GenericMessage<?> message){
        CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(message);
        completableFuture.whenComplete((result,ex)->{
            if (ex==null){
                log.info("Message :{} published, topic : {}, partition : {} and offset : {}", message.getPayload(),
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
            else{
                log.error("Unable to deliver message to kafka",ex);
            }
        });
    }



}
