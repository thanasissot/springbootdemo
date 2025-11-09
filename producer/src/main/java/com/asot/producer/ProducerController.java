package com.asot.producer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produce")
public class ProducerController {

    private final MessageProducer producer;

    public ProducerController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String send(@RequestBody String message) {
        producer.sendMessage(message);
        return "Message sent: " + message;
    }
}

