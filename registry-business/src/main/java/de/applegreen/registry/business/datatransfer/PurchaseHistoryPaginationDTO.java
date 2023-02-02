package de.applegreen.registry.business.datatransfer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Alexander Tepe | a.tepe@hotmail.de
 */

@Getter
@Setter
@NoArgsConstructor
public class PurchaseHistoryPaginationDTO {

    @Min(value = 0, message = "Page Number must be at least 0")
    @NotNull(message = "Page Number must be specified")
    private Integer pageNumber;
    private Integer pageSize;
}
