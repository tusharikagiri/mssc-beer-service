package guru.springframework.msscbeerservice.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;

public interface BeerService {

	Optional<BeerDto> getBeerById(UUID beerId);

	BeerDto saveNewBeer(BeerDto beerDto);

	BeerDto updateBeer(UUID beerId, BeerDto beerDto) throws Exception;

	void deleteById(UUID beerId);

	List<BeerDto> getAllBeers();

	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showOnHandInventory);
	
	BeerDto findBeerByUpc(String upc) throws Exception;
}
