package com.example.btpsd.commands;

import com.example.btpsd.model.UnitOfMeasurement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ServiceNumberCommand implements Serializable {

    private Long serviceNumberCode;

    private String code;

    private Long formulaCode;

    private Long unitOfMeasurementCode;

    private Long serviceTypeCode;

    private Long materialGroupCode;

    private String description;

    private Boolean shortTextChangeAllowed;

    private Boolean deletionIndicator;

    private Boolean mainItem;

    private Integer numberToBeConverted;

    private Integer convertedNumber;

    private Instant lastChangeDate;

    private String serviceText;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();

    private UnitOfMeasurement baseUnitOfMeasurement;

    private UnitOfMeasurement toBeConvertedUnitOfMeasurement;

    private UnitOfMeasurement convertedUnitOfMeasurement;

    public ServiceNumberCommand() {
    }
}
