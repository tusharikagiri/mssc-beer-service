package guru.springframework.msscbeerservice.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/v1/")
@RestController
public class BeerController {
	
	private static final Integer DEFAULT_PAGE_NUMBER = 0;
	private static final Integer DEFAULT_PAGE_SIZE = 25;
	private final BeerService beerService;
	
	public BeerController(BeerService beerService) {
		this.beerService = beerService;
	}
	
	@GetMapping(path = "beers", produces = {"application/json"})
	public ResponseEntity<BeerPagedList> listBeers(
			@RequestParam(value="pageNumber", required = false) Integer pageNumber,
			@RequestParam(value="pageSize", required = false) Integer pageSize,
			@RequestParam(value="beerName", required = false) String beerName,
			@RequestParam(value="beerStyle", required = false) BeerStyleEnum beerStyle,
			@RequestParam(value="showOnHandInventory", required = false) Boolean showOnHandInventory
			) {
		if(showOnHandInventory == null) {
			showOnHandInventory = false;
		}
		
		 if(pageNumber == null || pageNumber < 0) {
			 pageNumber = DEFAULT_PAGE_NUMBER;
		 }
		 
		 if(pageSize == null || pageSize < 1) {
			 pageSize = DEFAULT_PAGE_SIZE;
		 }
		 
		 BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showOnHandInventory);
		 
		 return new ResponseEntity<>(beerList, HttpStatus.OK);
	}
	
	@GetMapping(path = "beer/", produces = {"application/json"})
	public ResponseEntity<List<BeerDto>> getAll() {
		return new ResponseEntity<List<BeerDto>>(beerService.getAllBeers(), HttpStatus.OK); 
	}

	@GetMapping("beer/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@NotNull @PathVariable("beerId") UUID beerId) {
		log.info("Getting beer with ID {} ", beerId);
		System.out.println("Get Beer : " + beerId.toString());
		Optional<BeerDto> beerDto = beerService.getBeerById(beerId);
		if(beerDto.isEmpty()) {
			return new ResponseEntity<>(beerDto.get(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(beerDto.get(), HttpStatus.OK);
	}

	@PostMapping("beer/")
	public ResponseEntity saveBeer(@Valid @RequestBody BeerDto beerDto) {
		
		  BeerDto savedBeerDto = beerService.saveNewBeer(beerDto);
		  //System.out.println("Saved Beer : " + savedBeerDto.toString());
		 
		
		HttpHeaders headers = new HttpHeaders();
		//TODO add the full hostname to the url.
		headers.add("Location", "/api/v1/beer/" + savedBeerDto.getId().toString());
		
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@PutMapping("beer/{beerId}")
	public ResponseEntity handleBeerUpdate(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) throws Exception {
		beerService.updateBeer(beerId, beerDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("beer/{beerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBeer(@PathVariable("beerId") UUID beerId) {
		beerService.deleteById(beerId);
	}
	
	@GetMapping("beerUpc/{upc}/")
	public ResponseEntity<BeerDto> getBeerByUPC(@NotNull @PathVariable("upc") String upc) throws Exception {
		log.info("Getting beer with UPC {} ", upc);
		System.out.println("Get Beer : " + upc.toString());
		try {
	        BeerDto beerDto = beerService.findBeerByUpc(upc);
	        return new ResponseEntity<>(beerDto, HttpStatus.OK);
	    } catch (NotFoundException ex) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
}
