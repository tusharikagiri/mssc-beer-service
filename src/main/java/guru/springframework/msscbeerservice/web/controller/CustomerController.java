package guru.springframework.msscbeerservice.web.controller;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.msscbeerservice.services.CustomerService;
import guru.springframework.msscbeerservice.web.model.CustomerDto;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID customerId) {
		return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity saveCustomer(@Valid @RequestBody CustomerDto customerDto) {
		CustomerDto savedCustomer = customerService.saveCustomer(customerDto);
		System.out.println("Saved Customer " + savedCustomer.toString());
		
		HttpHeaders headers = new HttpHeaders();
		//TODO add the full hostname to the url.
		headers.add("Location", "/api/v1/customer/"+savedCustomer.getId().toString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@PutMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCustomer(@PathVariable("customerId") UUID customerId,@Valid @RequestBody CustomerDto customerDto) {
		customerService.updateCustomer(customerId, customerDto);
	}
	
	@DeleteMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCustomer(@PathVariable UUID customerId) {
		customerService.deleteById(customerId);
	}
}
