package guru.sfg.beer.common.events;

import java.io.Serializable;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

	private static final long serialVersionUID = 7600320396886919407L;
	
	private BeerDto beerDto;

}
