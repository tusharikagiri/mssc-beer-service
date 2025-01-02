package guru.springframework.msscbeerservice.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public interface BeerService {

	Optional<BeerDto> getBeerById(UUID beerId);

	BeerDto saveNewBeer(BeerDto beerDto);

	void updateBeer(UUID beerId, BeerDto beerDto) throws Exception;

	void deleteById(UUID beerId);

	List<BeerDto> getAllBeers();

}
