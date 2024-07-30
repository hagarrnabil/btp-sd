package com.example.btpsd.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ExecutionOrderMainCommand implements Serializable {

    private Long invoiceMainItemCode;

    private Long serviceNumberCode;

    private String description;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String materialGroupCode;

    private String personnelNumberCode;

    private String lineTypeCode;

    private Integer totalQuantity;

    private Double amountPerUnit;

    private Double total;

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
}
