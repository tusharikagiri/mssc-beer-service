package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {

	public static final String BEER_1_UPC = "0631234200036";
	public static final String BEER_2_UPC = "0631234300019";
	public static final String BEER_3_UPC = "0083783375213";

	private final BeerRepository beerRepository;	 

	@Override
	public void run(String... args) throws Exception {
		loadBeerObjects();

	}

	private void loadBeerObjects() {
		
		if (beerRepository.count() == 0) {
			Beer b1 = Beer.builder().beerName("Mango Bobs").beerStyle(BeerStyleEnum.IPA)
					.quantityToBrew(200).price(new BigDecimal("12.95")).upc(BEER_1_UPC).build();

			Beer b2 = Beer.builder().beerName("Galaxy Cat")
					.beerStyle(BeerStyleEnum.PALE_ALE).quantityToBrew(200).price(new BigDecimal("12.95"))
					.upc(BEER_2_UPC).build();

			Beer b3 = Beer.builder().beerName("Pinball Porter")
					.beerStyle(BeerStyleEnum.PALE_ALE).quantityToBrew(200).price(new BigDecimal("12.95"))
					.upc(BEER_3_UPC).build();

			beerRepository.save(b1);
			beerRepository.save(b2);
			beerRepository.save(b3);
		}
	}

}
