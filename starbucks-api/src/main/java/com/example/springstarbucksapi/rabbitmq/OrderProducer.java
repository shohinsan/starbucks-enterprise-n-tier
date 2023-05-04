package com.example.springstarbucksapi.rabbitmq;

import com.example.springstarbucksapi.model.StarbucksOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(StarbucksOrder order) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String orderJson = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend("orderQueue", orderJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}