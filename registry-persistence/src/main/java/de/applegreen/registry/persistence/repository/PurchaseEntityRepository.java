package de.applegreen.registry.persistence.repository;

import de.applegreen.registry.model.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */
public interface PurchaseEntityRepository extends JpaRepository<PurchaseEntity, Long> {}
