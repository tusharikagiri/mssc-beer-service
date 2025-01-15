package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import guru.sfg.brewery.model.CustomerDto;

public interface CustomerService {
	public CustomerDto getCustomerById(UUID customerId);

	public CustomerDto saveCustomer(CustomerDto customerDto);

	public void updateCustomer(UUID customerId, CustomerDto customerDto);
	
	public void deleteById(UUID customerId);
}
