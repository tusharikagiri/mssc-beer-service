package guru.springframework.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerDto;

@Component
@Mapper(componentModel = "spring", uses = {DateMapper.class})
public interface BeerMapper {
	
	  BeerDto beerToBeerDto(Beer beer);
	  
	  Beer beerDtoToBeer(BeerDto dto);	 

}
