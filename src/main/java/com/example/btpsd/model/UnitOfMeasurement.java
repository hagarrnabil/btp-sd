package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "unitOfMeasurement")
public class UnitOfMeasurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitOfMeasurementCode;


    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String code;

    //    @NotNull
    private String description;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "unitOfMeasurement")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "unitOfMeasurement")
    @JsonIgnore
    private Set<Formula> formulas = new HashSet<>();

    @OneToMany(mappedBy = "baseUnitOfMeasurement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ServiceNumber> baseServiceNumbers = new HashSet<>();

    @OneToMany(mappedBy = "toBeConvertedUnitOfMeasurement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ServiceNumber> toBeConvertedServiceNumbers = new HashSet<>();

    @OneToMany(mappedBy = "convertedUnitOfMeasurement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ServiceNumber> convertedServiceNumbers = new HashSet<>();

    public UnitOfMeasurement() {
    }

    public UnitOfMeasurement(String code, String description, Set<ModelSpecificationsDetails> modelSpecificationsDetails, Set<Formula> formulas, Set<ServiceNumber> baseServiceNumbers,
                             Set<ServiceNumber> toBeConvertedServiceNumbers, Set<ServiceNumber> convertedServiceNumbers)
    {
        this.code = code;
        this.description = description;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
        this.formulas = formulas;
        this.baseServiceNumbers = baseServiceNumbers;
        this.toBeConvertedServiceNumbers = toBeConvertedServiceNumbers;
        this.convertedServiceNumbers = convertedServiceNumbers;
    }

    public UnitOfMeasurement addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setUnitOfMeasurement(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }

    public UnitOfMeasurement addFormulas(Formula formula){
        formula.setUnitOfMeasurement(this);
        this.formulas.add(formula);
        return this;
    }

    public UnitOfMeasurement addBaseServiceNumbers(ServiceNumber serviceNumber){
        serviceNumber.setBaseUnitOfMeasurement(this);
        this.baseServiceNumbers.add(serviceNumber);
        return this;
    }

    public UnitOfMeasurement addToBeConvertedServiceNumbers(ServiceNumber serviceNumber){
        serviceNumber.setToBeConvertedUnitOfMeasurement(this);
        this.toBeConvertedServiceNumbers.add(serviceNumber);
        return this;
    }

    public UnitOfMeasurement addConvertedServiceNumbers(ServiceNumber serviceNumber){
        serviceNumber.setConvertedUnitOfMeasurement(this);
        this.convertedServiceNumbers.add(serviceNumber);
        return this;
    }
}