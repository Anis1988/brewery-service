package anis.com.msscbeerserviceb.services.inventory;

import anis.com.msscbeerserviceb.services.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "inventory-failover")
public interface InventoryFailOverFeignClient {

    @GetMapping("/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnhandInventory();
}
