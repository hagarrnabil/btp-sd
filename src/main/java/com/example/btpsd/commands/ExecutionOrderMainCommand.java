package com.example.btpsd.commands;

import com.example.btpsd.model.InvoiceMainItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExecutionOrderMainCommand implements Serializable {

    private Long executionOrderMainCode;

    private String referenceId;

    private Long serviceNumberCode;

    private String description;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String materialGroupCode;

    private String personnelNumberCode;

    private String lineTypeCode;

    private String serviceTypeCode;

    //    @NotNull
    private Integer totalQuantity;

    private Integer serviceQuantity;

    private Integer remainingQuantity;

    //    @NotNull
    private Double amountPerUnit;

    private Double total;

    private Double totalHeader;

    private Integer actualQuantity;

    private Integer actualPercentage;

    private Integer overFulfillmentPercentage;

    private Boolean unlimitedOverFulfillment;

    private Boolean manualPriceEntryAllowed;

    private String externalServiceNumber;

    private String serviceText;

    private String lineText;

    private String lineNumber;

    private Boolean biddersLine;

    private Boolean supplementaryLine;

    private Boolean lotCostOne;

    private Boolean doNotPrint;

    @JsonIgnore
    private InvoiceMainItem invoiceMainItem;
}
