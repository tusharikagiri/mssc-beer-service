package guru.springframework.msscbeerservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
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

	@Cacheable(value = "beerCache", key = "#beerId", condition = "#showOnHandInventory == false")
	@Override
	public Optional<BeerDto> getBeerById(UUID beerId) {
		System.out.println("getBeerById is called.");
		Optional<Beer> beer = beerRepository.findById(beerId);
		if (beer.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(beerMapper.beerToBeerDtoWithInventory(beer.get()));
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

	@Cacheable(value="beerListCache", condition = "#showOnHandInventory == false")
	@Override
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
			Boolean showOnHandInventory) {
		BeerPagedList beerPagedList;
		Page<Beer> beerPage;
		
		System.out.println("I was called.");

		if (!StringUtils.hasLength(beerName) && (beerStyle != null)) {

			beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);

		} else if (!StringUtils.hasText(beerName) && beerStyle!=null) {
			// search beer_service name
			beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);

		} else if (StringUtils.hasText(beerName) && beerStyle!=null) {
			// search beer_service style
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);

		} else {

			beerPage = beerRepository.findAll(pageRequest);

		}

		if (showOnHandInventory) {
			beerPagedList = new BeerPagedList(
					beerPage.getContent().stream().map(beerMapper::beerToBeerDtoWithInventory)
							.collect(Collectors.toList()),
					PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
					beerPage.getTotalElements());
		} else {
			beerPagedList = new BeerPagedList(
					beerPage.getContent().stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList()),
					PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
					beerPage.getTotalElements());
		}

		return beerPagedList;
	}

	@Cacheable(cacheNames = "beerUpcCache")
	@Override
	public BeerDto findBeerByUpc(String upc) throws Exception {
		System.out.println("beer by upc : "+ upc);
		return beerRepository.findByUpc(upc)
				.map(beerMapper::beerToBeerDto).orElseThrow(NotFoundException::new);		
		 
	}
}
