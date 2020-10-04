package anis.com.beer.order.service.services.beer;

import anis.com.brewery.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerById(UUID id);
    Optional<BeerDto> getBeerByUpc(String upc);
}
