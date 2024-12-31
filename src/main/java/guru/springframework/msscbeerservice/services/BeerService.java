package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public interface BeerService {

	BeerDto getBeerById(UUID beerId);

	BeerDto saveNewBeer(BeerDto beerDto);

	void updateBeer(UUID beerId, BeerDto beerDto);

	void deleteById(UUID beerId);

}
