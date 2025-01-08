package guru.springframework.msscbeerservice.web.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.msscbeerservice.web.mappers")
class BeerControllerTest {
	
	@MockitoBean
	BeerService beerService;
	
	@MockitoBean
	BeerMapper beerMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private BeerDto beerDto;
	
	@BeforeEach
	public void setUp() { 
		beerDto = getValidBeerDto();
	}
	

	@Test
	void testGetBeerById() throws Exception {
		when(beerService.getBeerById(UUID.fromString("19c360aa-1fd5-4213-83aa-8c52c10cce3d"))).thenReturn(Optional.of(beerDto));
		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.fromString("19c360aa-1fd5-4213-83aa-8c52c10cce3d")).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(document("v1/beer-get", pathParameters(
				parameterWithName("beerId").description("UUID of the desired beer to get.")
				),
				responseFields(
						fieldWithPath("id").description("UUID of the beer object").type(UUID.class),
						fieldWithPath("version").description("Version Number"),
						fieldWithPath("createdDate").description("Date Created").type(OffsetDateTime.class),
						fieldWithPath("lastModifiedDate").description("Date Modified").type(OffsetDateTime.class),
						fieldWithPath("beerName").description("Beer Name"),
						fieldWithPath("beerStyle").description("Beer Style"),
						fieldWithPath("upc").description("UPC of Beer"),
						fieldWithPath("price").description("Price"),
						fieldWithPath("quantityOnHand").description("Minimum Quantity on Hand"),
						fieldWithPath("quantityToBrew").description("Quantity on hand")
						)));
	}

	@Test
	void testSaveBeer() throws Exception {
		BeerDto beerDto = BeerDto.builder()
				.id(null)
				.beerName("Test Beer")
				.beerStyle(BeerStyleEnum.ALE)
				.price(new BigDecimal("2.99"))
				.upc(BeerLoader.BEER_1_UPC)
				.quantityOnHand(15)
				.quantityToBrew(3500)
				.build();
		
		BeerDto savedBeerDto = BeerDto.builder()
				.id(UUID.fromString("19c360aa-1fd5-4213-83aa-8c52c10cce3d"))
				.beerName("Test Beer")
				.beerStyle(BeerStyleEnum.ALE)
				.price(new BigDecimal("2.99"))
				.upc(BeerLoader.BEER_1_UPC)
				.quantityOnHand(15)
				.quantityToBrew(3500)
				.build();
		
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		when(beerService.saveNewBeer(beerDto)).thenReturn(savedBeerDto);
		
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		
		mockMvc.perform(post("/api/v1/beer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
		.andExpect(status().isCreated())
		.andDo(document("v1/beer-new",
                requestFields(
                		fields.withPath("id").ignored(),
                        fields.withPath("version").ignored(),
                        fields.withPath("createdDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerName").description("Name of the beer"),
                        fields.withPath("beerStyle").description("Style of Beer"),
                        fields.withPath("upc").description("Beer UPC").attributes(),
                        fields.withPath("price").description("Beer Price"),
                        fields.withPath("quantityToBrew").ignored(),
                        fields.withPath("quantityOnHand").ignored()
                )));
	}

	@Test
	void testUpdateBeerById() throws Exception {
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
		.andExpect(status().isNoContent());
	}
	
	BeerDto getValidBeerDto() {
		return BeerDto.builder()
				.id(UUID.fromString("19c360aa-1fd5-4213-83aa-8c52c10cce3d"))
				.beerName("Test Beer")
				.beerStyle(BeerStyleEnum.ALE)
				.price(new BigDecimal("2.99"))
				.upc(BeerLoader.BEER_1_UPC)
				.quantityToBrew(3500)
				.build();
	}
	
	private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}
