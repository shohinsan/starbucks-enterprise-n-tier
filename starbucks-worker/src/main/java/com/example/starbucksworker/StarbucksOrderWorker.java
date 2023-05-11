package com.example.starbucksworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RabbitListener(queues = "orderQueue")
public class StarbucksOrderWorker {

    private static final Logger log = LoggerFactory.getLogger(StarbucksOrderWorker.class);

    @Autowired
    private StarbucksOrderRepository orders ;

    @RabbitHandler
    public void processStarbucksOrders(String orderNumber) {
        log.info( "Received  Order # " + orderNumber) ;
        // Sleeping to simulate busy work
        try {
            Thread.sleep(60000); // 60 seconds
        } catch (Exception e) {}

        List<StarbucksOrder> list = orders.findByRegister( orderNumber ) ;
        if ( !list.isEmpty() ) {
            StarbucksOrder order = list.get(0) ;
            order.setStatus ( "FULFILLED" ) ;

            orders.save(order) ;
            log.info( "Processed Order # " + orderNumber );
        } else {
            log.info( "[ERROR] Order # " + orderNumber + " Not Found!" );
        }
    }
}