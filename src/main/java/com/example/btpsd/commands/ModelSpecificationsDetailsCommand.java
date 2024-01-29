package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ModelSpecificationsDetailsCommand implements Serializable {

    private Long modelSpecDetailsCode;
    private Long currencyCode;
    private Long personnelNumberCode;
    private Long serviceTypeCode;
    private Long materialGroupCode;
    private Long unitOfMeasurementCode;
    private Long serviceNumberCode;
    private Boolean selectionCheckBox = true;
    private String lineIndex;
    private Boolean deletionIndicator = true;
    private String shortText;
    private Integer quantity;
    private Integer grossPrice;
    private Integer overFulfilmentPercentage;
    private Boolean priceChangedAllowed = true;
    private Boolean unlimitedOverFulfillment = true;
    private Integer pricePerUnitOfMeasurement;
    private String externalServiceNumber;
    private Integer netValue;
    private String serviceText;
    private String lineText;
    private String formula;
    private String lineNumber;
    private String alternatives;
    private Boolean biddersLine = true;
    private Boolean supplementaryLine = true;
    private Boolean lotSizeForCostingIsOne = true;
    @JsonIgnore
    private Set<ModelSpecificationsCommand> modelSpecificationsCommands = new HashSet<>();
}
