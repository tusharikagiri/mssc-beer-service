package guru.springframework.msscbeerservice.services.brewing;

import java.util.List;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import guru.sfg.beer.common.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
	
	private final BeerRepository beerRepository;
	private final BeerInventoryService beerInventoryService;
	private final JmsTemplate jmsTemplate;
	private final BeerMapper beerMapper;

	@Scheduled(fixedRate = 10000)
	public void checkForLowInventory() {
		List<Beer> beers = beerRepository.findAll();
		beers.forEach(beer -> {
			Integer qntyOnHand = beerInventoryService.getOnHandInventory(beer.getId());
			
			log.debug("Min qnty on hand :" + beer.getMinOnHand());
			log.debug("Inventory is : "+ qntyOnHand);
			
			if(beer.getMinOnHand() >= qntyOnHand) {
				jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
			}
		});
	}

}
