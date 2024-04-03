package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ModelSpecificationsDetailsCommand implements Serializable {

    private Long modelSpecDetailsCode;
    private Long currencyCode;
    private Long formulaCode;
    private Long personnelNumberCode;
    private Long serviceTypeCode;
    private Long materialGroupCode;
    private String unitOfMeasurementCode;
    private Long serviceNumberCode;
    private Long noServiceNumber;
    private Long lineTypeCode;
    private Boolean selectionCheckBox;
    private String lineIndex;
    private Boolean deletionIndicator;
    private String shortText;
    private Integer quantity;
    private Integer grossPrice;
    private Integer overFulfilmentPercentage;
    private Boolean priceChangedAllowed;
    private Boolean unlimitedOverFulfillment;
    private Integer pricePerUnitOfMeasurement;
    private String externalServiceNumber;
    private Integer netValue;
    private String serviceText;
    private String lineText;
    private String lineNumber;
    private String alternatives;
    private Boolean biddersLine;
    private Boolean supplementaryLine;
    private Boolean lotSizeForCostingIsOne;
    private Boolean dontUseFormula;
    private LocalDate lastChangeDate;
    @JsonIgnore
    private Set<ModelSpecificationsCommand> modelSpecificationsCommands = new HashSet<>();
}
