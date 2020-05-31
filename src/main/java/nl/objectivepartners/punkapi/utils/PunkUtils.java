package nl.objectivepartners.punkapi.utils;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

public class PunkUtils {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
	public static final int MIN_PAGE = 1;
	public static final int MAX_PER_PAGE = 80;
	public static final String BAD_REQ_PAG_MSG = "Page Must be a number greater than 0 and per_page Must be a number greater than 0 and less than 80";
	public static final String PAG_ERRO_MSG = "There was an error getting beers";
	public static final String ALL_BEERS_ERROR_MSG = "There was an error getting all beers";
	public static final String FIND_BY_NAME_ERROR_MSG = "There was an error finding by name:";
	public static final String FIND_BY_DESC_ERROR_MSG = "There was an error finding by description:";
	public static final String RANDOM_ERROR_MSG = "There was an error getting a random beer";
	public static final String NO_BEER_ERROR_MSG = "No beer found that matches the ID:";
	private static final HttpHeaders headers = new HttpHeaders();
	private static final String URI_PUNK_BASE = "https://api.punkapi.com/v2/beers";
	private static final String PARAMETERS = "parameters";
		
	public static String getPunkPaginatedUri(int page, int perPage) {
		return UriComponentsBuilder.fromUriString(URI_PUNK_BASE).queryParam("page", page)
				.queryParam("per_page", perPage).build().toUriString();
	}
	
	public static HttpEntity<String> getHttpEntity() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", USER_AGENT);
        return new HttpEntity<String>(PARAMETERS, headers);
	}
	
}
