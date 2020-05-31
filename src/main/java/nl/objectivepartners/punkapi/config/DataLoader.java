package nl.objectivepartners.punkapi.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import nl.objectivepartners.punkapi.dto.BeerDTO;
import nl.objectivepartners.punkapi.model.BeerEntity;
import nl.objectivepartners.punkapi.repository.BeerRepository;
import nl.objectivepartners.punkapi.utils.PunkUtils;

/**
* Spring Component class to get beers from PunkApi and save them in the database.
*/
@Component
public class DataLoader  implements ApplicationRunner {

	private static final Logger logger = LogManager.getLogger(DataLoader.class);
	
	public final BeerRepository beerRepository;
	private final ModelMapper modelMapper;

	
	public DataLoader(BeerRepository beerRepository, ModelMapper modelMapper) {
		this.beerRepository = beerRepository;
		this.modelMapper = modelMapper;
	}
	/**
	 * Method to call punkApi to get all beers recursively
	 * @param restTemplate instance
	 * @param beersDTOList list to load from punkapi
	 * @param page number
	 * @param perPage records per page
	 * @return list of BeerDTO objects
	 */
	private List<BeerDTO> getAllBeersFromPunkApi(RestTemplate restTemplate, List<BeerDTO> beersDTOList, int page, int perPage) {
		
		ResponseEntity<BeerDTO[]> responseEntity = restTemplate.exchange(PunkUtils.getPunkPaginatedUri(page, perPage),
				HttpMethod.GET, PunkUtils.getHttpEntity(), BeerDTO[].class);
		
		if (responseEntity.hasBody() && responseEntity.getBody().length > 0) {
			beersDTOList.addAll(Arrays.asList(responseEntity.getBody()));
			getAllBeersFromPunkApi(restTemplate, beersDTOList, ++page, perPage);
		}
		return beersDTOList;	
	}
	
	public void loadData() {
		List<BeerDTO> beersDTOList = new ArrayList<BeerDTO>(); 
		try {
			RestTemplate restTemplate = new RestTemplate();
			this.getAllBeersFromPunkApi(restTemplate, beersDTOList, PunkUtils.MIN_PAGE, PunkUtils.MAX_PER_PAGE);
			List<BeerEntity> beersEntityList = beersDTOList.stream()
					 .map(beer -> this.modelMapper.map(beer, BeerEntity.class))
					 .collect(Collectors.toList());
			
			this.beerRepository.saveAll(beersEntityList);
			
		}catch(Exception e) {
			logger.error("There was an error loading the data: ", () -> e);
			throw e;
		}		
		
	}
    
    public void run(ApplicationArguments args) {
    	this.loadData();
    }
}