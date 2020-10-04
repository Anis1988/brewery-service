package anis.com.msscbeerserviceb.services.inventory;

import anis.com.msscbeerserviceb.config.FeignClientConfig;
import anis.com.msscbeerserviceb.services.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "inventory-service",fallback = InventoryServiceFeignClientFailover.class,configuration = FeignClientConfig.class)
public interface InventoryServiceFeignClient {

    @GetMapping(BeerInventoryServiceRestTemplate.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory( @PathVariable UUID beerId);
}
