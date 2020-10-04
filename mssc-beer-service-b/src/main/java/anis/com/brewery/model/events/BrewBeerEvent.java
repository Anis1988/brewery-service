package anis.com.brewery.model.events;

import anis.com.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent  {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
