package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

@Service
public class BeerServiceImpl implements BeerService {

	@Override
	public BeerDto getBeerById(UUID beerId) {

		return BeerDto.builder().id(UUID.randomUUID())
				.beerName("Galaxy Cat")
				.beerStyle(BeerStyleEnum.PALE_ALE)
				.build();
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		
		return BeerDto.builder()
				.id(UUID.randomUUID())
				.beerName(beerDto.getBeerName())
				.beerStyle(beerDto.getBeerStyle())
				.build();
	}

	@Override
	public void updateBeer(UUID beerId, BeerDto beerDto) {
		// TODO impl - would add a real impl later.
		
	}

	@Override
	public void deleteById(UUID beerId) {
		// TODO impl - would add a real impl later.
		
	}

}
