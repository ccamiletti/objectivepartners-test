package nl.objectivepartners;

import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import nl.objectivepartners.punkapi.dto.BeerDTO;


public class ObjetivesPartnersTest {

	private static final HttpHeaders headers = new HttpHeaders();
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
	private static final String PARAMETERS = "parameters";
	
	public static HttpEntity<String> getHttpEntity() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", USER_AGENT);
        return new HttpEntity<String>(PARAMETERS, headers);
	}

	@Test
	public void testGetBeersByName() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String beerName = "IPA";
		final String baseUrl = "http://localhost:8080/v2/beers?beer_name="+beerName;
		ResponseEntity<BeerDTO[]> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, this.getHttpEntity(), BeerDTO[].class);
		
		Arrays.asList(responseEntity.getBody()).forEach(beer -> {
			Assertions.assertTrue(beer.getName().toLowerCase().contains(beerName.toLowerCase()));
		});
	}

	@Test
	public void testGetBeersByDescription() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String description = "bitter";
		final String baseUrl = "http://localhost:8080/v2/beers?description=".concat(description);
		ResponseEntity<BeerDTO[]> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, this.getHttpEntity(), BeerDTO[].class);
		
		Arrays.asList(responseEntity.getBody()).forEach(beer -> {
			Assertions.assertTrue(beer.getDescription().toLowerCase().contains(description.toLowerCase()));
		});
	}

	@Test
	public void testGetBeersDefaultPaginatedFrom1To25() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8080/v2/beers";
		ResponseEntity<BeerDTO[]> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, this.getHttpEntity(), BeerDTO[].class);
		Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
	}

	@Test
	public void testGetRandomBeers() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8080/v2/beers/random";
		ResponseEntity<BeerDTO> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.GET, this.getHttpEntity(), BeerDTO.class);
		Assertions.assertTrue(responseEntity.hasBody());
	}

	@Test
	public void testGetBadRequestPerPaginationException() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8080/v2/beers?page=1&per_page=81";
		Assertions.assertThrows(
				HttpClientErrorException.class,
                () -> { restTemplate.exchange(baseUrl, HttpMethod.GET, this.getHttpEntity(), BeerDTO[].class); }
        );
	}

	
	
}
