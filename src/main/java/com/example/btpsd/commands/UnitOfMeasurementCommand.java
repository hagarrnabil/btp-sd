package com.example.btpsd.commands;

import com.example.btpsd.model.ServiceNumber;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//@Getter
//@Setter
//@NoArgsConstructor
public class UnitOfMeasurementCommand implements Serializable {


    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("Description")
    private String description;

    // Getters and setters
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    private Long unitOfMeasurementCode;
//
//    private String code;
//
//    private String description;
//    private Long unitOfMeasurementCode;
//
//    private String UnitOfMeasureSAPCode;
//
//    private String UnitOfMeasureLongName;
//
//    private String UnitOfMeasureName;
}