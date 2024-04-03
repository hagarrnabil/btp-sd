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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ServiceNumberCommand implements Serializable {

    private Long serviceNumberCode;

    private Long noServiceNumber;

    private String searchTerm;

    private Long formulaCode;

    private String serviceTypeCode;

    private Long materialGroupCode;

    private String description;

    private Boolean shortTextChangeAllowed;

    private Boolean deletionIndicator;

    private Boolean mainItem;

    private Integer numberToBeConverted;

    private Integer convertedNumber;

    private LocalDate lastChangeDate;

    private String serviceText;

    private String baseUnitOfMeasurement;

    private String toBeConvertedUnitOfMeasurement;

    private String defaultUnitOfMeasurement;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();
}