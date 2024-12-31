package guru.springframework.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;

import guru.springframework.msscbeerservice.domain.Customer;
import guru.springframework.msscbeerservice.web.model.CustomerDto;

@Mapper
public interface CustomerMapper {

	Customer customerDtoToCustomer(CustomerDto customerDto);
	
	CustomerDto customerToCustomerDto(Customer customer);
}
