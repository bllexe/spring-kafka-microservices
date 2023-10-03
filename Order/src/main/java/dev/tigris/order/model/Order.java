package dev.tigris.order.model;

import dev.tigris.order.request.PlaceOrderRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
@Builder
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private String product;
    private double price;
    private String status;

    public static Order fromOrder(PlaceOrderRequest orderRequest){
        return Order.builder()
                .product(orderRequest.getProduct())
                .price(orderRequest.getPrice())
                .build();
    }
}
