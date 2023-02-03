package de.applegreen.registry.web.controller;

import de.applegreen.registry.business.datatransfer.PurchaseDTO;
import de.applegreen.registry.business.datatransfer.PurchaseHistoryPaginationDTO;
import de.applegreen.registry.business.util.AdviceAnnotations;
import de.applegreen.registry.business.util.HasLogger;
import de.applegreen.registry.model.ProductEntity;
import de.applegreen.registry.model.PurchaseEntity;
import de.applegreen.registry.persistence.repository.ProductEntityRepository;
import de.applegreen.registry.persistence.repository.PurchaseEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */

@Controller
@RequestMapping("/purchases")
public class PurchaseController implements HasLogger {

    private final int PAGE_SIZE_DEFAULT = 20;
    private final PurchaseEntityRepository purchaseEntityRepository;
    private final ProductEntityRepository productEntityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseController(
            PurchaseEntityRepository purchaseEntityRepository,
            ProductEntityRepository productEntityRepository,
            ModelMapper modelMapper
    ) {
        this.purchaseEntityRepository = purchaseEntityRepository;
        this.productEntityRepository = productEntityRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Method to retrieve a paginated history of purchases
     * Result is sorted by descending timestamps
     *
     * @param pagination DTO which wraps page number and size arguments
     * @return HTTP Response with status code 200 and history of recent purchases
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/history")
    public ResponseEntity getPurchaseHistory(@Valid @RequestBody PurchaseHistoryPaginationDTO pagination) {
        Page<PurchaseEntity> purchaseHistoryPage = this.purchaseEntityRepository.findAll(PageRequest.of(
                pagination.getPageNumber(),
                pagination.getPageSize() == 0 ? this.PAGE_SIZE_DEFAULT : pagination.getPageSize(),
                Sort.by(Sort.Direction.DESC, "timestamp")
        ));
        if (purchaseHistoryPage.isEmpty()) {
            this.getLogger().warn("Purchase History requested on empty database");
            return ResponseEntity.noContent().build();
        }
        Map<Timestamp, Object> responseData = new HashMap<>();
        List<PurchaseEntity> purchaseEntityList = purchaseHistoryPage.getContent();
        purchaseEntityList.forEach((purchase) -> {
            Map<String, Object> subMap = new HashMap<>();
            subMap.put("total cost", purchase.getTotal_cost());
            subMap.put("id", purchase.getId());
            responseData.put(purchase.getTimestamp(), subMap);
        });
        return ResponseEntity.ok().body(responseData);
    }

    /**
     * Method to register a new purchase
     * This will also trigger a sync with the woocommerce Instance
     *
     * @param purchaseDTO the Purchase
     * @return 200 if entity was saved
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/new")
    @AdviceAnnotations.PurchaseCommit
    public ResponseEntity newPurchase(@Valid @RequestBody PurchaseDTO purchaseDTO) {
        PurchaseEntity purchaseEntity = this.modelMapper.map(purchaseDTO, PurchaseEntity.class);
        Set<ProductEntity> transientProducts = new HashSet<>();
        purchaseDTO.getSold_products().forEach((productDTO) -> {
            ProductEntity productEntity = this.modelMapper.map(productDTO, ProductEntity.class);
            transientProducts.add(productEntity);
            this.productEntityRepository.saveAndFlush(productEntity);
        });
        purchaseEntity.setSold_products(transientProducts);
        this.purchaseEntityRepository.save(purchaseEntity);
        this.getLogger().info("New Purchase about " + purchaseDTO.getTotal_cost().toString() + "€");
        return ResponseEntity.ok().build();
    }

    /**
     * Method to get Detail of single Purchase
     *
     * @param purchase_id Long id of the purchase inside the Database
     * @return 200 and instance of purchase if ID exists, 404 else
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/details/{purchase_id}")
    public ResponseEntity details(@PathVariable("purchase_id") Long purchase_id) {
        Optional<PurchaseEntity> purchaseEntityOptional = this.purchaseEntityRepository.findById(purchase_id);
        try {
            PurchaseEntity purchase = purchaseEntityOptional.orElseThrow();
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", purchase.getId());
            responseData.put("timestamp", purchase.getTimestamp());
            responseData.put("total cost", purchase.getTotal_cost());
            responseData.put("sold products", new HashMap<String, Object>());
            Set<ProductEntity> productEntities = purchase.getSold_products();
            productEntities.forEach(productEntity -> {
                Map<String, Object> subData = new HashMap<>();
                subData.put("description", productEntity.getProduct_description());
                subData.put("cost", productEntity.getCost());
                subData.put("amount", productEntity.getAmount());
                Map<String, Object> subDataMap = (Map<String, Object>) responseData.get("sold products");
                subDataMap.put(productEntity.getId().toString(), subData);
            });
            return ResponseEntity.ok(responseData);
        }
        catch (NoSuchElementException e) {
            this.getLogger().warn("Purchase with id " + purchase_id + " does not exists");
            return ResponseEntity.notFound().build();
        }
    }
}
