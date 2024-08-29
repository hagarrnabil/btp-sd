package com.example.btpsd.model;

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

    @ManyToOne
    private ServiceNumber serviceNumber;

    @OneToOne
    @JoinColumn(name = "execution_order_main_id")
    private ExecutionOrderMain executionOrderMain;

    public ServiceInvoiceMain(ExecutionOrderMain executionOrderMain) {
        this.serviceNumberCode = executionOrderMain.getServiceNumberCode();
        this.unitOfMeasurementCode = executionOrderMain.getUnitOfMeasurementCode();
        this.currencyCode = executionOrderMain.getCurrencyCode();
        this.description = executionOrderMain.getDescription();
        this.totalQuantity = executionOrderMain.getTotalQuantity();
        this.actualQuantity = executionOrderMain.getActualQuantity();
        this.actualPercentage = executionOrderMain.getActualPercentage();
        this.biddersLine = executionOrderMain.getBiddersLine();
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

        this.serviceNumberCode = executionOrderMain.getServiceNumberCode();
        this.unitOfMeasurementCode = executionOrderMain.getUnitOfMeasurementCode();
        this.currencyCode = executionOrderMain.getCurrencyCode();
        this.description = executionOrderMain.getDescription();
        this.totalQuantity = executionOrderMain.getTotalQuantity();
        this.actualQuantity = executionOrderMain.getActualQuantity();
        this.actualPercentage = executionOrderMain.getActualPercentage();
        this.biddersLine = executionOrderMain.getBiddersLine();
        this.doNotPrint = executionOrderMain.getDoNotPrint();
        this.supplementaryLine = executionOrderMain.getSupplementaryLine();
        this.lotCostOne = executionOrderMain.getLotCostOne();
        this.overFulfillmentPercentage = executionOrderMain.getOverFulfillmentPercentage();
        this.unlimitedOverFulfillment = executionOrderMain.getUnlimitedOverFulfillment();
        this.executionOrderMain = executionOrderMain;
        this.amountPerUnit = new BigDecimal(executionOrderMain.getAmountPerUnit()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.total = new BigDecimal(executionOrderMain.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue();

    }

    public void setExecutionOrderMain(ExecutionOrderMain executionOrderMain) {
        this.executionOrderMain = executionOrderMain;
        executionOrderMain.setServiceInvoiceMain(this);
    }
}
