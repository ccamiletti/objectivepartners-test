package nl.objectivepartners.punkapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerDTO {
	
	private Long id;
	private String name;
	private String tagLine;
	private String firstBrewed;
	private String description;
	private String imageUrl;
	private Double abv;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTagLine() {
		return tagLine;
	}
	
	@JsonProperty("tagline")
	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}
	public String getFirstBrewed() {
		return firstBrewed;
	}
	
	@JsonProperty("first_brewed")
	public void setFirstBrewed(String firstBrewed) {
		this.firstBrewed = firstBrewed;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	
	@JsonProperty("image_url")
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Double getAbv() {
		return abv;
	}
	public void setAbv(Double abv) {
		this.abv = abv;
	}


}
