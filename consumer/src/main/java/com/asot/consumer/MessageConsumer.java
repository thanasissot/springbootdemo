package com.asot.consumer;

import com.asot.shared.dto.NewTradeOrder;
import com.asot.shared.service.TradeOrderConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final TradeOrderConsumerService tradeOrderConsumerService;
//    private final ReentrantLock processingLock = new ReentrantLock();
    private final XmlMapper xmlMapper = new XmlMapper();

//    public MessageConsumer(TradeOrderConsumerService tradeOrderConsumerService) throws JAXBException {
//        this.tradeOrderConsumerService = tradeOrderConsumerService;
//        this.jaxbContext = JAXBContext.newInstance(NewTradeOrderDto.class);
//    }

    @JmsListener(destination = "test-queue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @JmsListener(destination = "trade-order-queue")
    public void receiveTradeOrderMessage(String xmlMessage) {
        log.info("Received trade order message: {}", xmlMessage);

//        // Ensure only one message is processed at a time
////        processingLock.lock();
//        try {
            processTradeOrderMessage(xmlMessage);
//        } finally {
////            processingLock.unlock();
//        }
    }

    @PostConstruct
    public void init() {

    }

    private void processTradeOrderMessage(String xmlMessage) {
        try {
            // Parse XML to DTO
            NewTradeOrder dto = parseXmlMessage(xmlMessage);
            log.info("Parsed trade order DTO: {}", dto);

            // Process the trade order
            var tradeOrder = tradeOrderConsumerService.processNewTradeOrder(dto);
            log.info("Successfully processed trade order with dealRef: {}", tradeOrder.getDealRef());

        } catch (JsonProcessingException e) {
            log.error("Failed to parse XML message: {}", xmlMessage, e);
            throw new RuntimeException("XML parsing failed", e);
        } catch (IllegalArgumentException e) {
            log.error("Validation error processing trade order message: {}", e.getMessage());
            throw new RuntimeException("Validation failed", e);
        } catch (Exception e) {
            log.error("Error processing trade order message: {}", xmlMessage, e);
            throw new RuntimeException("Processing failed", e);
        }
    }

    private NewTradeOrder parseXmlMessage(String xmlMessage) throws JsonProcessingException {
        return xmlMapper.readValue(xmlMessage, NewTradeOrder.class);
    }
}
