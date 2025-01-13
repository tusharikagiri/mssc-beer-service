package guru.springframework.msscbeerservice.services.brewing;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.sfg.beer.common.events.BrewBeerEvent;
import guru.sfg.beer.common.events.NewInventoryEvent;
import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

	private final BeerMapper beerMapper;

	private final BeerRepository beerRepository;
	private final JmsTemplate jmsTemplate;

	@Transactional
	@JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
	public void listen(BrewBeerEvent event) throws NotFoundException {
		BeerDto beerDto = event.getBeerDto();
		Beer beer = beerRepository.findById(beerDto.getId())
				.orElseThrow(() -> new NotFoundException());		
		beerDto.setQuantityOnHand(beer.getQuantityToBrew());
		
		NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
		log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH : " + beerDto.getQuantityOnHand());
		jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
	}

}
