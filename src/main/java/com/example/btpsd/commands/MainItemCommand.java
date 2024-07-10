package com.example.btpsd.commands;

import com.example.btpsd.model.SubItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MainItemCommand implements Serializable {

    private Long mainItemCode;

//    private List<Long> subItemCode = new ArrayList<Long>();

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    private Double profitMargin;

    private Double totalWithProfit;

//    @JsonIgnore
//    private List<InvoiceCommand> invoiceCommandList = new ArrayList<>();

    private Set<SubItemCommand> subItemCommandList = new HashSet<>();

//    @JsonIgnore
//    private SubItemCommand subItemCommand;
}
