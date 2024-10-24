package com.example.btpsd.dtos;

import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceMainItemDtoExceptTotalAmountDto implements Serializable {

    private Long invoiceMainItemCode;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private String description;

    private Integer quantity;

    private Double amountPerUnit;

    private Double profitMargin;

    private Boolean doNotPrint;

    private Double amountPerUnitWithProfit;

    @JsonProperty("subItems")
    private List<InvoiceSubItemCommand> subItems = new ArrayList<>();
}
