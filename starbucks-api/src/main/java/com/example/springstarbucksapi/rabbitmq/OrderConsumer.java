package com.example.springstarbucksapi.rabbitmq;

import com.example.springstarbucksapi.model.StarbucksOrder;
import com.example.springstarbucksapi.service.StarbucksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private StarbucksService starbucksService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveOrder(String orderJson) {
        try {
            StarbucksOrder order = objectMapper.readValue(orderJson, StarbucksOrder.class);

            // Simulate order preparation
            Thread.sleep(10000); // Wait for 10 seconds
            order.setStatus("Order is ready");
            starbucksService.updateOrderStatus(order);

        } catch (JsonProcessingException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}