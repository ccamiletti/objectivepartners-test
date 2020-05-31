package nl.objectivepartners.punkapi.service;

import java.util.List;
import java.util.Optional;

import nl.objectivepartners.punkapi.dto.BeerDTO;

public interface BeerService {

	List<BeerDTO> findAllBeers();
	Optional<BeerDTO> findById(Long id);
	List<BeerDTO> findBeers(int page, int perPage);
	BeerDTO getRandomBeer();
	List<BeerDTO> findByName(String name);
	List<BeerDTO> findByDescription(String description);

	
}
