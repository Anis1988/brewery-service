package anis.com.beer.order.service.web.mappers;

import anis.com.beer.order.service.domain.Customer;
import anis.com.brewery.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto (Customer customer);

    Customer dtoToCustomer (CustomerDto customerDto);
}
