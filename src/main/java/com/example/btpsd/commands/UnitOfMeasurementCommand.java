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
//    private Long unitOfMeasurementCode;
//
//    private String UnitOfMeasureSAPCode;
//
//    private String UnitOfMeasureLongName;
//
//    private String UnitOfMeasureName;
}