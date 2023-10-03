package dev.tigris.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class OrderPlacedEvent {
    private int  orderId;
    private String product;
    private double price;
}
