package dev.tigris.order.client;

import dev.tigris.order.model.InventoryStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Component
@FeignClient(url="http://localhost:8092",name ="inventories")
 public interface InventoryClient {

    @GetMapping("/inventories")
    InventoryStatus exists(@RequestParam("productId") String productId);
 }
