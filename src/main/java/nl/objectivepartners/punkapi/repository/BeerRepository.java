package nl.objectivepartners.punkapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.objectivepartners.punkapi.model.BeerEntity;

@Repository
public interface BeerRepository extends JpaRepository<BeerEntity, Long> {
	
	List<BeerEntity> findByDescriptionLike(String description);
	List<BeerEntity> findByNameLike(String description);
	
}
