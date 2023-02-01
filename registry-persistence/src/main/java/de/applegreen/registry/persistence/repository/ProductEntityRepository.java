package de.applegreen.registry.persistence.repository;

import de.applegreen.registry.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {}