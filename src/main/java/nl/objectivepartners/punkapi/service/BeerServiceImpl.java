package nl.objectivepartners.punkapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.objectivepartners.punkapi.dto.BeerDTO;
import nl.objectivepartners.punkapi.model.BeerEntity;
import nl.objectivepartners.punkapi.repository.BeerRepository;

@Service
@Transactional
public class BeerServiceImpl implements BeerService {
	
	private final BeerRepository beerRepository;
	private final ModelMapper modelMapper;
	private final String WILD_CARD = "%";
	
	public BeerServiceImpl(BeerRepository beerRepository, ModelMapper modelMapper) {
		this.beerRepository = beerRepository;
		this.modelMapper = modelMapper;
	}
	
	private String addWildcard(String attr) {
		attr = WILD_CARD.concat(attr).concat(WILD_CARD);
		return attr;
	}
	
	@Transactional(readOnly = true)
	public Optional<BeerDTO> findById(Long id) {
		return this.beerRepository.findById(id)
				.map(beer -> this.modelMapper.map(beer, BeerDTO.class));
	}
	
	
	@Transactional(readOnly = true)
	public List<BeerDTO> findAllBeers() {
		return this.beerRepository.findAll()
						.stream()
						.map(beer -> this.modelMapper.map(beer, BeerDTO.class))
						.collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public List<BeerDTO> findBeers(int page, int pageSize) {
		Pageable paging = PageRequest.of(page-1, pageSize);
		Page<BeerEntity> pagedResult = this.beerRepository.findAll(paging);
		
		return pagedResult.getContent()
				.stream()
				.map(beer -> this.modelMapper.map(beer, BeerDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public BeerDTO getRandomBeer() {
		List<BeerDTO> beersListDTO = this.findAllBeers();
		return beersListDTO.get(new Random().nextInt(beersListDTO.size()));
	}

	@Override
	@Transactional(readOnly = true)
	public List<BeerDTO> findByName(String name) {
		return this.beerRepository.findByNameLike(this.addWildcard(name))
				.stream()
				.map(beer -> this.modelMapper.map(beer, BeerDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<BeerDTO> findByDescription(String description) {
		return this.beerRepository.findByDescriptionLike(this.addWildcard(description))
				.stream()
				.map(beer -> this.modelMapper.map(beer, BeerDTO.class))
				.collect(Collectors.toList());

	}	


}
