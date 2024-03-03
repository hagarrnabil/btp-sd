package com.example.btpsd.commands;

import com.example.btpsd.model.ServiceNumber;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasurementCommand implements Serializable {

    private Long unitOfMeasurementCode;

    private String code;

    private String description;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();

    @JsonIgnore
    private Set<FormulaCommand> formulaCommands = new HashSet<>();

    @JsonIgnore
    private Set<ServiceNumberCommand> baseServiceNumberCommands = new HashSet<>();

    @JsonIgnore
    private Set<ServiceNumberCommand> toBeConvertedServiceNumberCommands = new HashSet<>();

    @JsonIgnore
    private Set<ServiceNumberCommand> convertedServiceNumberCommands = new HashSet<>();
}
