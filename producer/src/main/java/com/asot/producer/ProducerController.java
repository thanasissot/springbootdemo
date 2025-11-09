package com.asot.producer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produce")
public class ProducerController {

    private final MessageProducer producer;

    public ProducerController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String send(@RequestBody String message, @RequestParam(name = "queue", required = false, defaultValue = "test-queue") String queue) {
        producer.sendMessage(message, queue);
        return "Message sent: " + message;
    }
}

