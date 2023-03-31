package de.applegreen.registry.persistence.repository;

import de.applegreen.registry.model.WCProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
public interface WCProductTypeRepository extends JpaRepository<WCProductType, Long> {

 @Query("select p from WCProductType p where p.wc_id = :wc_id")
 Optional<WCProductType> findByWc_id(@Param("wc_id") Long wc_id);
}
