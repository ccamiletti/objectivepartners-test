package nl.objectivepartners.punkapi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.objectivepartners.punkapi.dto.BeerDTO;
import nl.objectivepartners.punkapi.exception.ApiError;
import nl.objectivepartners.punkapi.service.BeerService;
import nl.objectivepartners.punkapi.utils.PunkUtils;

@RestController
@RequestMapping("/beers")
public class BeerController {

	private static final Logger logger = LogManager.getLogger(BeerController.class);
	
	public final BeerService beerService;

	public BeerController(BeerService beerService) {
		this.beerService = beerService;
	}

	/**
	 * Return a boolean checking if page and perPage meets the condition page>0 and (perPage <= 1 and perPage >= 80)
	 * @param page
	 * @param perPage
	 * @return a boolean value if in range or not
	 */
	private Boolean checkParametersInRange(int page, int perPage) {
		return (page >= PunkUtils.MIN_PAGE && (perPage <= PunkUtils.MAX_PER_PAGE && perPage >= PunkUtils.MIN_PAGE))
				? Boolean.TRUE
				: Boolean.FALSE;
	}

	/**
	 * Method to get a list of beers filtered by name
	 * @param beerName
	 * @return list of beers filtered by name
	 * @throws ApiError in case of error.
	 */
	@GetMapping(params={"beer_name"})
	public ResponseEntity<List<BeerDTO>> findByName(@RequestParam(name = "beer_name") String beerName) throws ApiError {
		ResponseEntity<List<BeerDTO>> responseEntity = null;
		try {
			List<BeerDTO> beerList = this.beerService.findByName(beerName);
			responseEntity = new ResponseEntity<List<BeerDTO>>(beerList, HttpStatus.OK);
		}catch(Exception e) {
			logger.error(PunkUtils.FIND_BY_NAME_ERROR_MSG.concat(beerName), () -> e);
			throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, PunkUtils.FIND_BY_NAME_ERROR_MSG.concat(beerName), e.getMessage());
		}
		return responseEntity;
	}

	/**
	 * Method to get a list of beers filtered by description
	 * @param description
	 * @return list of beers filtered by description
	 * @throws ApiError in case of error.
	 */
	@GetMapping(params={"description"})
	public ResponseEntity<List<BeerDTO>> findByDescription(@RequestParam(name = "description") String description) throws ApiError {
		ResponseEntity<List<BeerDTO>> responseEntity = null;
		try {
			List<BeerDTO> beerList = this.beerService.findByDescription(description);
			responseEntity = new ResponseEntity<List<BeerDTO>>(beerList, HttpStatus.OK);
		}catch(Exception e) {
			logger.error(PunkUtils.FIND_BY_DESC_ERROR_MSG.concat(description), () -> e);
			throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,  PunkUtils.FIND_BY_DESC_ERROR_MSG.concat(description), e.getMessage());
		}
		return responseEntity;
	}
	
	/**
	 * Return a list of beers per page. The default pagination is from 1 to 25  
	 * @param page number
	 * @param perPage records per page
	 * @return list of beers paginated
	 * @throws ApiError in case of error or bad request
	 */
	@GetMapping
	public ResponseEntity<List<BeerDTO>> findBeersPerPage(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "per_page") int perPage) throws ApiError {
		
		ResponseEntity<List<BeerDTO>> responseEntity = null;
		try {
			if (this.checkParametersInRange(page, perPage)) {
				List<BeerDTO> beerList = this.beerService.findBeers(page, perPage);
				responseEntity = new ResponseEntity<List<BeerDTO>>(beerList, HttpStatus.OK);
			}else {
				logger.info(PunkUtils.BAD_REQ_PAG_MSG);
				throw new ApiError(HttpStatus.BAD_REQUEST, PunkUtils.BAD_REQ_PAG_MSG);
			}
		}catch(ApiError e) {
			throw e;
		}catch(Exception e) {
			logger.error(PunkUtils.PAG_ERRO_MSG, () -> e);
			throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, PunkUtils.PAG_ERRO_MSG, e.getMessage());
		}
		return responseEntity;		
	}

	/**
	 * Method that return a list of all beers.
	 * @return list of all beers
	 * @throws ApiError in case of error
	 */
	@GetMapping("/getAllBeers")
	public ResponseEntity<List<BeerDTO>> findAllBeers() throws ApiError {
		try {
			List<BeerDTO> beerList = this.beerService.findAllBeers();
			return new ResponseEntity<List<BeerDTO>>(beerList, HttpStatus.OK);
		}catch(Exception e) {
			logger.error(PunkUtils.ALL_BEERS_ERROR_MSG, () -> e);
			throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, PunkUtils.ALL_BEERS_ERROR_MSG, e.getMessage());
		}
	}

	/**
	 * Return a list of all beers.
	 * @param id
	 * @return beer object
	 * @throws ApiError in the case the beer not exist or there was an internal server error
	 */
	@GetMapping("/{id}")
	public ResponseEntity<BeerDTO> findById(@PathVariable("id") Long id) throws ApiError {
		ResponseEntity<BeerDTO> responseEntity = null;
			responseEntity = this.beerService.findById(id).map(beer -> {
				return new ResponseEntity<BeerDTO>(beer, HttpStatus.OK);
			}).orElseThrow(() -> {
				logger.error(PunkUtils.NO_BEER_ERROR_MSG + id);
				return new ApiError(HttpStatus.NOT_FOUND, PunkUtils.NO_BEER_ERROR_MSG + id);
			});
			
		return responseEntity;
	}

	/**
	 * Return a beer object randomly
	 * @return beer object
	 * @throws ApiError in the case of error
	 */
	@GetMapping("/random")
	public ResponseEntity<BeerDTO> getRandomBeer() throws ApiError {
		try {
			return new ResponseEntity<BeerDTO>(this.beerService.getRandomBeer(), HttpStatus.OK);
		}catch(Exception e) {
			logger.error(PunkUtils.RANDOM_ERROR_MSG, () -> e);
			throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, PunkUtils.RANDOM_ERROR_MSG, e.getMessage());
		}
	}

}
