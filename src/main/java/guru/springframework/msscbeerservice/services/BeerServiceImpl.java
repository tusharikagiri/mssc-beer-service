package guru.springframework.msscbeerservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

	private final BeerMapper beerMapper;
	private final BeerRepository beerRepository;

	@Autowired
	public BeerServiceImpl(BeerMapper beerMapper, BeerRepository beerRepository) {
		this.beerMapper = beerMapper;
		this.beerRepository = beerRepository;

	}

	@Override
	public List<BeerDto> getAllBeers() {
		List<BeerDto> beerDtos = new ArrayList<>();
		beerRepository.findAll().forEach((Beer beer) -> {
			beerDtos.add(beerMapper.beerToBeerDto(beer));
		});
		return beerDtos;
	}

	@Override
	public Optional<BeerDto> getBeerById(UUID beerId) {
		Optional<Beer> beer = beerRepository.findById(beerId);
		if (beer.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(beerMapper.beerToBeerDto(beer.get()));
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		log.info("Save beer :" + beerDto.toString());
		Beer beer = beerMapper.beerDtoToBeer(beerDto);
		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) throws Exception {
		log.info("Update beer with ID :" + beerId);
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
		
		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(beerDto.getBeerStyle());
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());
		
		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Override
	public void deleteById(UUID beerId) {
		// TODO impl - would add a real impl later.
		log.info("delete beer with ID :" + beerId);
	}

}
