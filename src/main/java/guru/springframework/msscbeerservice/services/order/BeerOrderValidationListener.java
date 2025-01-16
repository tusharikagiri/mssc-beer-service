package guru.springframework.msscbeerservice.services.order;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateOrderResult;
import guru.springframework.msscbeerservice.config.JmsConfig;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {
private final JmsTemplate jmsTemplate;

private final BeerOrderValidator beerOrderValidator;

	
	@JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
	public void listenForValidateOrder(@Payload BeerOrderDto beerOrderDto, @Headers MessageHeaders headers, Message message) {
		Boolean isValid = beerOrderValidator.validateOrder(beerOrderDto);
		
		jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, 
				ValidateOrderResult.builder()
				.beerOrderId(beerOrderDto.getId())
				.isValid(isValid)
				.build());
	}
}
