package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.sfg.brewery.model.CustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public CustomerDto getCustomerById(UUID customerId) {
		return CustomerDto.builder().id(UUID.randomUUID())
				.customerName("Tusharika")
				.build();
	}

	@Override
	public CustomerDto saveCustomer(CustomerDto customerDto) {
		return CustomerDto.builder()
				.id(UUID.randomUUID())
				.customerName(customerDto.getCustomerName())
				.build();
	}

	@Override
	public void updateCustomer(UUID customerId, CustomerDto customerDto) {
		// TODO Auto-generated method stub.
	}

	@Override
	public void deleteById(UUID customerId) {
		// TODO Auto-generated method stub
		
	}

}
