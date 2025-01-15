package guru.sfg.brewery.model.events;

import java.io.Serializable;

import guru.sfg.brewery.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

	private static final long serialVersionUID = 7600320396886919407L;
	
	private BeerDto beerDto;

}
