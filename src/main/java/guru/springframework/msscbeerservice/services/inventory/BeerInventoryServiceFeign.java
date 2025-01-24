package guru.springframework.msscbeerservice.services.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import guru.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BeerInventoryServiceFeign implements BeerInventoryService {

	private final InventoryServiceFeignClient inventoryServiceFeignClient;

	@Override
	public Integer getOnHandInventory(UUID beerId) {

		log.debug("Calling Inventory Service - BeerId: " + beerId);

		ResponseEntity<List<BeerInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnHandInventory(beerId);

		// sum from inventory list
		Integer onHand = Objects.requireNonNull(responseEntity.getBody())
				.stream()
				.mapToInt(BeerInventoryDto::getQuantityOnHand)
				.sum();
		
		log.debug("BeerId : " + beerId + " On Hand is: " + onHand);
		return onHand;
	}

}
