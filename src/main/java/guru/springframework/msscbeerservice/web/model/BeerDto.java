package guru.springframework.msscbeerservice.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto {
	
	@Null
	private UUID id;
	
	@Null
	private Integer version   ;

	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
	private OffsetDateTime createdDate;
	
	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", shape = JsonFormat.Shape.STRING)
	private OffsetDateTime lastModifiedDate;

	@NotBlank
	@Size(min=3, max=100)
	private String beerName;
	
	@NotNull
	private BeerStyleEnum beerStyle;
	
	@Positive
	@NotNull
	private Long upc;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull
	@Positive
	private BigDecimal price;
	
	private Integer minOnHand;
	private Integer quantityToBrew;
}
