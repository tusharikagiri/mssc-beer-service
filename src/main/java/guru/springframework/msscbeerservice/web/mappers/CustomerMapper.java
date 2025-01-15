package guru.springframework.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;

import guru.sfg.brewery.model.CustomerDto;
import guru.springframework.msscbeerservice.domain.Customer;

@Mapper
public interface CustomerMapper {

	Customer customerDtoToCustomer(CustomerDto customerDto);
	
	CustomerDto customerToCustomerDto(Customer customer);
}
