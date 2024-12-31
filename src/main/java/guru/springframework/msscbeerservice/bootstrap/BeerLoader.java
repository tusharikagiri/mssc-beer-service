package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

@Component
public class BeerLoader implements CommandLineRunner {

	private final BeerRepository beerRepository;

	public BeerLoader(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadBeerObjects();

	}

	private void loadBeerObjects() {
		if (beerRepository.count() == 0) {
			beerRepository.save(Beer.builder()
					.beerName("Mango Bobs")
					.beerStyle(BeerStyleEnum.IPA)
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(337010000001L)
					.price(new BigDecimal("12.95"))
					.build());
			
			beerRepository.save(Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle(BeerStyleEnum.PALE_ALE)
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(337010000002L)
					.price(new BigDecimal("11.95"))
					.build());
		}

	}

}
