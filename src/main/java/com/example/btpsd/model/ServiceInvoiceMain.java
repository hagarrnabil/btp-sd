package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "serviceInvoice")
public class ServiceInvoiceMain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceInvoiceCode;

    private String referenceId;

    private Long serviceNumberCode;

    private String description;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String materialGroupCode;

    private String personnelNumberCode;

    private String lineTypeCode;

    private String serviceTypeCode;

    private Integer remainingQuantity;

    private Integer quantity;

    private Integer totalQuantity;

    private Double amountPerUnit;

    private Double total;

    private Integer actualQuantity;

    private Integer actualPercentage;

    private Integer overFulfillmentPercentage;

    private Boolean unlimitedOverFulfillment;

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

    private String alternatives;

    private Double totalHeader;

    @ManyToOne
    private ServiceNumber serviceNumber;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "execution_order_main_id")
    private ExecutionOrderMain executionOrderMain;

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public ServiceInvoiceMain(ExecutionOrderMain executionOrderMain) {
        this.serviceNumberCode = executionOrderMain.getServiceNumberCode();
        this.unitOfMeasurementCode = executionOrderMain.getUnitOfMeasurementCode();
        this.currencyCode = executionOrderMain.getCurrencyCode();
        this.description = executionOrderMain.getDescription();
        this.totalQuantity = executionOrderMain.getTotalQuantity();

        // Initialize with executionOrderMain but don't accumulate AQ here
        this.actualQuantity = executionOrderMain.getActualQuantity();
        this.actualPercentage = executionOrderMain.getActualPercentage();
        this.biddersLine = executionOrderMain.getBiddersLine();
        this.lineNumber = executionOrderMain.getLineNumber();
        this.doNotPrint = executionOrderMain.getDoNotPrint();
        this.supplementaryLine = executionOrderMain.getSupplementaryLine();
        this.lotCostOne = executionOrderMain.getLotCostOne();
        this.overFulfillmentPercentage = executionOrderMain.getOverFulfillmentPercentage();
        this.unlimitedOverFulfillment = executionOrderMain.getUnlimitedOverFulfillment();
        this.amountPerUnit = new BigDecimal(executionOrderMain.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.total = new BigDecimal(executionOrderMain.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.executionOrderMain = executionOrderMain;
    }

    public void updateFromExecutionOrder(ExecutionOrderMain executionOrderMain) {

        if (executionOrderMain == null) return;

        this.setExecutionOrderMain(executionOrderMain);

        // Check if this is the same execution order as the previous service invoice
        if (executionOrderMain.getActualQuantity() != null) {
            Integer calculatedActualQuantity = (this.getQuantity() != null) ? this.getQuantity() : 0;

            // Accumulate AQ only if it's the same execution order
            if (this.executionOrderMain != null && this.executionOrderMain.getExecutionOrderMainCode().equals(executionOrderMain.getExecutionOrderMainCode())) {
                calculatedActualQuantity += executionOrderMain.getActualQuantity();
            }

            this.setActualQuantity(calculatedActualQuantity);
        }
        if (executionOrderMain.getServiceNumberCode() != null) {
            this.serviceNumberCode = executionOrderMain.getServiceNumberCode();
        }
        if (executionOrderMain.getUnitOfMeasurementCode() != null) {
            this.unitOfMeasurementCode = executionOrderMain.getUnitOfMeasurementCode();
        }
        if (executionOrderMain.getCurrencyCode() != null) {
            this.currencyCode = executionOrderMain.getCurrencyCode();
        }
        if (executionOrderMain.getDescription() != null) {
            this.description = executionOrderMain.getDescription();
        }
        if (executionOrderMain.getTotalQuantity() != null) {
            this.totalQuantity = executionOrderMain.getTotalQuantity();
        }
        if (this.totalQuantity != null && this.actualQuantity != null) {
            this.remainingQuantity = this.totalQuantity - this.actualQuantity;
        } else {
            this.remainingQuantity = null; // Or some other fallback
        }
        if (executionOrderMain.getBiddersLine() != null) {
            this.biddersLine = executionOrderMain.getBiddersLine();
        }
        if (executionOrderMain.getLineNumber() != null) {
            this.lineNumber = executionOrderMain.getLineNumber();
        }
        if (executionOrderMain.getDoNotPrint() != null) {
            this.doNotPrint = executionOrderMain.getDoNotPrint();
        }
        if (executionOrderMain.getSupplementaryLine() != null) {
            this.supplementaryLine = executionOrderMain.getSupplementaryLine();
        }
        if (executionOrderMain.getLotCostOne() != null) {
            this.lotCostOne = executionOrderMain.getLotCostOne();
        }
        if (executionOrderMain.getOverFulfillmentPercentage() != null) {
            this.overFulfillmentPercentage = executionOrderMain.getOverFulfillmentPercentage();
        }
        if (executionOrderMain.getUnlimitedOverFulfillment() != null) {
            this.unlimitedOverFulfillment = executionOrderMain.getUnlimitedOverFulfillment();
        }
        if (this.totalQuantity != null && this.actualQuantity != null) {
            this.remainingQuantity = this.totalQuantity - this.actualQuantity;
        } else {
            this.remainingQuantity = null; // Or some other fallback
        }
        if (executionOrderMain.getAmountPerUnit() != null) {
            this.amountPerUnit = new BigDecimal(executionOrderMain.getAmountPerUnit())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
        if (this.unlimitedOverFulfillment != null && this.unlimitedOverFulfillment) {
            // Unlimited overfulfillment, no restriction
        } else if (this.overFulfillmentPercentage != null) {
            Integer maxAllowedQuantity = this.totalQuantity + (this.totalQuantity * this.overFulfillmentPercentage / 100);
            if (this.actualQuantity != null && this.actualQuantity > maxAllowedQuantity) {
                throw new RuntimeException("Actual quantity exceeds allowed overfulfillment limit.");
            }
        } else if (this.actualQuantity != null && this.actualQuantity > this.totalQuantity) {
            throw new RuntimeException("Actual quantity exceeds total quantity without overfulfillment.");
        }

        // Calculate actualPercentage after updating actualQuantity and totalQuantity
        this.actualPercentage = (this.actualQuantity != null && this.totalQuantity != null && this.totalQuantity > 0)
                ? (this.actualQuantity * 100) / this.totalQuantity
                : null;

        // Update actualPercentage in ExecutionOrderMain
        if (executionOrderMain != null) {
            executionOrderMain.setActualPercentage(this.actualPercentage);
        }

        // Recalculate total
        if (this.quantity != null && this.amountPerUnit != null) {
            this.total = new BigDecimal(this.quantity * this.amountPerUnit)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
    }

    public void setExecutionOrderMain(ExecutionOrderMain executionOrderMain) {
        this.executionOrderMain = executionOrderMain;
        executionOrderMain.setServiceInvoiceMain(this);
    }

    public void updateAQAPandRQ(Integer previousExcOrderAQ) {
        // Apply logic only if the execution order hasn't changed
        if (this.executionOrderMain != null && previousExcOrderAQ != null) {
            if (this.executionOrderMain.getExecutionOrderMainCode().equals(previousExcOrderAQ)) {
                // Calculate Actual Quantity (AQ) by accumulating previous AQ
                this.actualQuantity = this.quantity + previousExcOrderAQ;
            } else {
                // Handle new execution order; reset AQ accumulation
                this.actualQuantity = this.quantity;
            }

            // Calculate Actual Percentage (AP) and Remaining Quantity (RQ)
            if (this.totalQuantity != null && this.actualQuantity != null) {
                this.actualPercentage = (this.actualQuantity * 100) / this.totalQuantity;
                this.remainingQuantity = this.totalQuantity - this.actualQuantity;
            }
        }
    }


}