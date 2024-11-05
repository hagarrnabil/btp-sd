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

    private String referenceSDDocument;

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

    @Column(columnDefinition = "char(9)")
    @Length(max = 9)
    private String temporaryDeletion;

    @ManyToOne
    private ServiceNumber serviceNumber;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "execution_order_main_id")
    private ExecutionOrderMain executionOrderMain;

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }


    public void updateFromExecutionOrder(ExecutionOrderMain executionOrderMain) {


        if (executionOrderMain == null) return;

        this.setExecutionOrderMain(executionOrderMain);

        // Check and update serviceNumberCode
        if (executionOrderMain.getServiceNumberCode() != null) {
            this.serviceNumberCode = executionOrderMain.getServiceNumberCode();
        }

        // Check and update unitOfMeasurementCode
        if (executionOrderMain.getUnitOfMeasurementCode() != null) {
            this.unitOfMeasurementCode = executionOrderMain.getUnitOfMeasurementCode();
        }

        // Check and update currencyCode
        if (executionOrderMain.getCurrencyCode() != null) {
            this.currencyCode = executionOrderMain.getCurrencyCode();
        }

        // Check and update description
        if (executionOrderMain.getDescription() != null) {
            this.description = executionOrderMain.getDescription();
        }

        // Check and update totalQuantity
        if (executionOrderMain.getTotalQuantity() != null) {
            this.totalQuantity = executionOrderMain.getTotalQuantity();
        }

        // Check and update actualQuantity
        Integer calculatedActualQuantity = (this.getQuantity() != null) ? this.getQuantity() : 0;
        if (executionOrderMain.getActualQuantity() != null) {
            calculatedActualQuantity += executionOrderMain.getActualQuantity();
        }
        this.setActualQuantity(calculatedActualQuantity);


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

        // Check and update amountPerUnit
        if (executionOrderMain.getAmountPerUnit() != null) {
            this.amountPerUnit = new BigDecimal(executionOrderMain.getAmountPerUnit())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        // Handle overfulfillment logic
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

}