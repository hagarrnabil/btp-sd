package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Entity
@Table(name = "unitOfMeasurement")
public class UnitOfMeasurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitOfMeasurementCode;
//
//    @Column(unique = true, length = 8, columnDefinition = "char(8)")
//    @Length(max = 8)
//    private String code;
//
//    private String description;
//
////    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
////    @Length(max = 225)
//    private String UnitOfMeasureSAPCode;
//
////    @NotNull
//    private String UnitOfMeasureLongName;
//
//    private String UnitOfMeasureName;

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
}