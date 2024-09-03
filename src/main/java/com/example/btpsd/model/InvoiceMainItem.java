package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoiceMainItem")
public class InvoiceMainItem implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceMainItemCode;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private String description;

    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    private Double profitMargin;

    private Double totalWithProfit;

    private Boolean doNotPrint;

    private Double amountPerUnitWithProfit;


    @OneToMany(mappedBy = "mainItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("subItemList")
    private List<InvoiceSubItem> subItemList = new ArrayList<>();

    @ManyToOne
    private ServiceNumber serviceNumber;

    @OneToOne(mappedBy = "invoiceMainItem", cascade = CascadeType.ALL)
    private ExecutionOrderMain executionOrderMain;


    public InvoiceMainItem addSubItem(InvoiceSubItem subItem) {
        if (!this.subItemList.contains(subItem)) {
            subItem.setMainItem(this);
            this.subItemList.add(subItem);
        }
        return this;
    }

}
