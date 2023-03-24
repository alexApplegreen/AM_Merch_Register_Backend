package de.applegreen.registry.persistence.repository;

import de.applegreen.registry.model.WCProductType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
public interface WCProductTypeRepository extends JpaRepository<WCProductType, Long> {}
