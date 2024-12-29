package guru.springframework.msscbeerservice.web.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbeerservice.web.model.BeerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void testGetBeerById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	void testSaveBeer() throws Exception {
		BeerDto beerDto = BeerDto.builder().build();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
		.andExpect(status().isCreated());
	}

	@Test
	void testUpdateBeerById() throws Exception {
		BeerDto beerDto = BeerDto.builder().build();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
		.andExpect(status().isNoContent());
	}

}
