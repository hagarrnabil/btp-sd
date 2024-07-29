package com.example.btpsd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoiceMainItem")
public class ExecutionOrderSub implements Serializable {

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

    private String externalServiceNumber;

    private String serviceText;

    private String lineText;

    private String lineNumber;

    private Boolean biddersLine;

    private Boolean supplementaryLine;

    private Boolean lotCostOne;

    private Boolean doNotPrint;

    @ManyToOne
    private ServiceNumber serviceNumber;
}
