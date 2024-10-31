package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private Integer remainingQuantity;

    //    @NotNull
    private Double amountPerUnit;

    private Double total;

    private Double totalHeader;

    private Integer actualQuantity;

    private Integer previousQuantity;

    private Integer actualPercentage;

    private Integer overFulfillmentPercentage;

    private Boolean unlimitedOverFulfillment;

    private Boolean manualPriceEntryAllowed;

    private String externalServiceNumber;

    private String serviceText;

    private String lineText;

    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String lineNumber;

    private Boolean biddersLine;

    private Boolean supplementaryLine;

    private Boolean lotCostOne;

    private Boolean doNotPrint;


    @ManyToOne
    private ServiceNumber serviceNumber;

    @OneToOne
    @JoinColumn(name = "invoice_main_item_id")
    private InvoiceMainItem invoiceMainItem;

    @OneToOne(mappedBy = "executionOrderMain", cascade = CascadeType.PERSIST)
    private ServiceInvoiceMain serviceInvoiceMain;


    public ExecutionOrderMain(InvoiceMainItem invoiceMainItem) {
        this.serviceNumberCode = invoiceMainItem.getServiceNumberCode();
        this.unitOfMeasurementCode = invoiceMainItem.getUnitOfMeasurementCode();
        this.currencyCode = invoiceMainItem.getCurrencyCode();
        this.description = invoiceMainItem.getDescription();
        this.totalQuantity = invoiceMainItem.getQuantity();
        this.doNotPrint = invoiceMainItem.getDoNotPrint();

        if (invoiceMainItem.getProfitMargin() != null) {
            this.amountPerUnit = new BigDecimal(invoiceMainItem.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            this.total = new BigDecimal(invoiceMainItem.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } else {
            this.amountPerUnit = invoiceMainItem.getAmountPerUnit();
            this.total = invoiceMainItem.getTotal();
        }

        this.invoiceMainItem = invoiceMainItem;
    }

    public void updateFromInvoiceMainItem(InvoiceMainItem invoiceMainItem) {

        if (invoiceMainItem == null) return;

        this.serviceNumberCode = invoiceMainItem.getServiceNumberCode();
        this.unitOfMeasurementCode = invoiceMainItem.getUnitOfMeasurementCode();
        this.currencyCode = invoiceMainItem.getCurrencyCode();
        this.description = invoiceMainItem.getDescription();
        this.totalQuantity = invoiceMainItem.getQuantity();
        this.doNotPrint = invoiceMainItem.getDoNotPrint();
        this.invoiceMainItem = invoiceMainItem;

        // Set amountPerUnit and total based on profit margin availability
        if (invoiceMainItem.getProfitMargin() != null) {
            this.amountPerUnit = new BigDecimal(invoiceMainItem.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            this.total = new BigDecimal(invoiceMainItem.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } else {
            this.amountPerUnit = invoiceMainItem.getAmountPerUnit();
            this.total = invoiceMainItem.getTotal();
        }
    }

    public void setInvoiceMainItem(InvoiceMainItem invoiceMainItem) {
        this.invoiceMainItem = invoiceMainItem;
        invoiceMainItem.setExecutionOrderMain(this);
    }
}