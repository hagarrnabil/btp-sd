package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String currencyCode;

    private String formulaCode;

    private String personnelNumberCode;

    private String serviceTypeCode;

    private String materialGroupCode;

    private String unitOfMeasurementCode;

    private Long serviceNumberCode;

    private Long noServiceNumber;

    private String lineTypeCode;

    private Boolean selectionCheckBox;

    private String lineIndex;

//    private Boolean deletionIndicator;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastChangeDate;

    @JsonIgnore
    private Set<ModelSpecificationsCommand> modelSpecificationsCommands = new HashSet<>();
}
