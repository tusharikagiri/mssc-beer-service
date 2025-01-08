package guru.springframework.msscbeerservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerDto;

@DecoratedWith(BeerMapperDecorator.class)
@Mapper(componentModel = "spring", uses = {DateMapper.class})
public interface BeerMapper {
	
	  BeerDto beerToBeerDto(Beer beer);
	  
	  Beer beerDtoToBeer(BeerDto dto);	 
	  
	  BeerDto beerToBeerDtoWithInventory(Beer beer);

}
