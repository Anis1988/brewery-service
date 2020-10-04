package anis.com.msscbeerserviceb.services.order;


import anis.com.brewery.model.events.BeerOrderDto;
import anis.com.msscbeerserviceb.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
public class BeerOrderValidator {
    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrderDto) {
        AtomicInteger beersNotFound = new AtomicInteger();
        beerOrderDto.getBeerOrderLines().forEach(orderLine -> {
            if(beerRepository.findByUpc(orderLine.getUpc())==null)
                beersNotFound.incrementAndGet();
        });
        return beersNotFound.get() == 0;
    }
}
