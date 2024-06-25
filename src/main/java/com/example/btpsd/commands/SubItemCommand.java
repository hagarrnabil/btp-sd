package com.example.btpsd.commands;

import com.example.btpsd.model.Invoice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubItemCommand implements Serializable {

    private Long subItemCode;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    @JsonIgnore
    private List<MainItemCommand> mainItemCommandList = new ArrayList<>();

    @JsonIgnore
    private List<InvoiceCommand> invoiceCommandList = new ArrayList<>();

}
