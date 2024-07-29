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
public class ExecutionOrderMain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceMainItemCode;

    private Long serviceNumberCode;

    private String description;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String materialGroupCode;

    private String personnelNumberCode;

    private String lineTypeCode;

    @NotNull
    private Integer totalQuantity;

    @NotNull
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

//    @OneToMany(mappedBy = "mainItem", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonProperty("subItemList")
//    private List<InvoiceSubItem> subItemList = new ArrayList<>();

    @ManyToOne
    private ServiceNumber serviceNumber;


}
