package guru.springframework.msscbeerservice.services.inventory;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbeerservice.bootstrap.BeerLoader;

@SpringBootTest
public class BeerInventoryServiceRestTemplateImplTest {
	
	@Autowired
	BeerInventoryService beerInventoryService;
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void getOnhanndInventory() {
		Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
		System.out.println(qoh);
	}

}
