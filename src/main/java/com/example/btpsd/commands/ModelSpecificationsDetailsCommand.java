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

    private Long modelSpecDetails;
    private Long currency;
    private Long personnelNumber;
    private Long serviceType;
    private Long materialGroup;
    private Long unitOfMeasurement;
    private Long serviceNumber;
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
    private Integer netValue = quantity * grossPrice;
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
