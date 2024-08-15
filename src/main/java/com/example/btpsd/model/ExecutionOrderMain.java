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
@Table(name = "executionOrderMain")
public class ExecutionOrderMain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long executionOrderMainCode;

//    private Long invoiceMainItemCode;

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

    //    @NotNull
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


    @OneToMany(mappedBy = "executionOrderMain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("executionOrdersubList")
    private List<ExecutionOrderSub> executionOrderSubList = new ArrayList<>();

    @ManyToOne
    private ServiceNumber serviceNumber;

    @OneToOne
    @JoinColumn(name = "invoice_main_item_id")
    private InvoiceMainItem invoiceMainItem;

    public ExecutionOrderMain addExecutionOrderSub(ExecutionOrderSub executionOrderSub) {
        if (!this.executionOrderSubList.contains(executionOrderSub)) {
            executionOrderSub.setExecutionOrderMain(this);
            this.executionOrderSubList.add(executionOrderSub);
        }
        return this;
    }

    public ExecutionOrderMain(InvoiceMainItem invoiceMainItem) {
        this.serviceNumberCode = invoiceMainItem.getServiceNumberCode();
        this.unitOfMeasurementCode = invoiceMainItem.getUnitOfMeasurementCode();
        this.currencyCode = invoiceMainItem.getCurrencyCode();
        this.description = invoiceMainItem.getDescription();
        this.totalQuantity = invoiceMainItem.getQuantity();
        this.total = invoiceMainItem.getTotalWithProfit();
        this.doNotPrint = invoiceMainItem.getDoNotPrint();
        this.amountPerUnit = invoiceMainItem.getAmountPerUnitWithProfit();
        this.invoiceMainItem = invoiceMainItem;
    }
}
