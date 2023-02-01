package de.applegreen.registry.web.controller;

import de.applegreen.registry.business.datatransfer.PurchaseDTO;
import de.applegreen.registry.business.datatransfer.PurchaseHistoryPaginationDTO;
import de.applegreen.registry.business.util.HasLogger;
import de.applegreen.registry.model.PurchaseEntity;
import de.applegreen.registry.persistence.repository.PurchaseEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */

@Controller
@RequestMapping("/purchases")
public class PurchaseController implements HasLogger {

    private final int PAGE_SIZE_DEFAULT = 20;
    private final PurchaseEntityRepository purchaseEntityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseController(
            PurchaseEntityRepository purchaseEntityRepository,
            ModelMapper modelMapper
    ) {
        this.purchaseEntityRepository = purchaseEntityRepository;
        this.modelMapper = modelMapper;
    }

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
        List<PurchaseEntity> purchaseEntityList = purchaseHistoryPage.toList();
        purchaseEntityList.forEach((purchase) -> {
            Map<String, Object> subMap = new HashMap<>();
            subMap.put("total cost", purchase.getTotal_cost());
            subMap.put("id", purchase.getId());
            responseData.put(purchase.getTimestamp(), subMap);
        });
        return ResponseEntity.ok().body(responseData);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/new")
    public ResponseEntity newPurchase(@Valid @RequestBody PurchaseDTO purchaseDTO) {
        PurchaseEntity purchaseEntity = this.modelMapper.map(purchaseDTO, PurchaseEntity.class);
        this.purchaseEntityRepository.save(purchaseEntity);
        this.getLogger().info("New Purchase about " + purchaseDTO.getTotalCost().toString() + "€");
        return ResponseEntity.ok().build();
    }
}
