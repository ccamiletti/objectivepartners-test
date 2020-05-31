package nl.objectivepartners.punkapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "beer")
public class BeerEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4080947611661399885L;

	@Id
	private Long id;
	
	@Column(name= "name")
	private String name;
	
	@Column(name= "tagline")
	private String tagLine;

	@Column(name = "first_brewed")
	private String firstBrewed;
	
	@Column(name  = "description")
	private String description;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "abv")
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

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public String getFirstBrewed() {
		return firstBrewed;
	}

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
