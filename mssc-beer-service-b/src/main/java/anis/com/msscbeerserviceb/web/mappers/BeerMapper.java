package anis.com.msscbeerserviceb.web.mappers;

import anis.com.msscbeerserviceb.domain.Beer;
import anis.com.brewery.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

}
