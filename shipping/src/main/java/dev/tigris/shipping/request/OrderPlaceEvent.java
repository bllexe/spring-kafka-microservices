package dev.tigris.shipping.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlaceEvent {

    private int orderId;
    private String product;
    private double price;
}
