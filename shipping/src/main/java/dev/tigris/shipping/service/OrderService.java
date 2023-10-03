package dev.tigris.shipping.service;

import dev.tigris.shipping.modal.Shipping;
import dev.tigris.shipping.repository.ShippingRepository;
import dev.tigris.shipping.request.OrderPlaceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ShippingRepository shippingRepository;
    private final KafkaTemplate kafkaTemplate;

    @KafkaListener(topics ="prod.orders.placed",groupId = "shipping-group")
    public void handleOrderPlacedEvent(OrderPlaceEvent event){
        Shipping shipping=new Shipping();
        shipping.setOrderId(event.getOrderId());
       this.shippingRepository.save(shipping);
       this.kafkaTemplate.send("prod.orders.shipped",String.valueOf(shipping.getOrderId()),String.valueOf(shipping.getOrderId()));
    }
}
