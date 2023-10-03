package dev.tigris.shipping.repository;

import dev.tigris.shipping.modal.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping,Long> {

}
