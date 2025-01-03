package guru.springframework.msscbeerservice.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {
	
	private final BeerService beerService;
	
	public BeerController(BeerService beerService) {
		this.beerService = beerService;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<BeerDto>> getAll() {
		return new ResponseEntity<List<BeerDto>>(beerService.getAllBeers(), HttpStatus.OK); 
	}

	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@NotNull @PathVariable("beerId") UUID beerId) {
		log.info("Getting beer with ID {} ", beerId);
		System.out.println("Get Beer : " + beerId.toString());
		Optional<BeerDto> beerDto = beerService.getBeerById(beerId);
		if(beerDto.isEmpty()) {
			return new ResponseEntity<>(beerDto.get(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(beerDto.get(), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity saveBeer(@Valid @RequestBody BeerDto beerDto) {
		
		  BeerDto savedBeerDto = beerService.saveNewBeer(beerDto);
		  //System.out.println("Saved Beer : " + savedBeerDto.toString());
		 
		
		HttpHeaders headers = new HttpHeaders();
		//TODO add the full hostname to the url.
		headers.add("Location", "/api/v1/beer/" + savedBeerDto.getId().toString());
		
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@PutMapping("/{beerId}")
	public ResponseEntity handleBeerUpdate(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) throws Exception {
		beerService.updateBeer(beerId, beerDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{beerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBeer(@PathVariable("beerId") UUID beerId) {
		beerService.deleteById(beerId);
	}
}
