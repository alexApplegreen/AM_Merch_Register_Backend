package de.applegreen.registry.business.datatransfer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */

@Getter
@Setter
@NoArgsConstructor
public class PurchaseDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductDTO {

        private Long Product_id;
        private String product_description;
        private BigDecimal cost;
        private Integer amount;
    }

    @NotNull
    private Timestamp timestamp;
    @NotNull
    @Min(value = 0, message = "Total Cost of a Purchase must be a positive Value")
    private BigDecimal total_cost;
    @NotEmpty(message = "At least one product must have been sold")
    @NotNull
    private Set<ProductDTO> sold_products;
}
