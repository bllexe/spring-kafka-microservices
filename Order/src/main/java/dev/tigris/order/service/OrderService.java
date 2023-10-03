package dev.tigris.order.service;

import dev.tigris.order.client.InventoryClient;
import dev.tigris.order.model.InventoryStatus;
import dev.tigris.order.model.Order;
import dev.tigris.order.repository.OrderRepository;
import dev.tigris.order.request.OrderPlacedEvent;
import dev.tigris.order.request.PlaceOrderRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaTemplate kafkaTemplate;
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(PlaceOrderRequest request) {

        //inventory does have any stock
        InventoryStatus status = inventoryClient.exists(request.getProduct());
        if (!status.isExists()) {
            throw new EntityNotFoundException("Product does not exist");
        }
        Order order = new Order();
        order.setPrice(request.getPrice());
        order.setProduct(request.getProduct());
        order.setStatus("PLACED");
        Order savedOrder = this.orderRepository.save(order);

        //after save data db send kafka, send order id which already save
        this.kafkaTemplate.send("prod.orders.placed", String.valueOf(savedOrder.getId()), OrderPlacedEvent.builder()
                .orderId(order.getId().intValue())
                .product(request.getProduct())
                .price(request.getPrice())
                .build());
    }

    @KafkaListener(topics = "prod.orders.shipped", groupId = "order-group")
    public void handleOrderShippedEvent(String orderId) {
        this.orderRepository.findById(Long.valueOf(orderId)).ifPresent(order -> {
            order.setStatus("SHIPPED");
            this.orderRepository.save(order);
        });
    }
}
