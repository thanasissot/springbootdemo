package com.asot.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final JmsTemplate jmsTemplate;

    public MessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message, String destinationQueue) {
        jmsTemplate.convertAndSend(destinationQueue, message);
        System.out.println("Sent: " + message);
    }
}
