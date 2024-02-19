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
@NoArgsConstructor
@Builder
@Entity
@Table(name = "unitOfMeasurement")
public class UnitOfMeasurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitOfMeasurementCode;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String code;

    @NotNull
    private String description;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "unitOfMeasurement")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "unitOfMeasurement")
    @JsonIgnore
    private Set<Formula> formulas = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "unitOfMeasurement")
    @JsonIgnore
    private Set<ServiceNumber> serviceNumbers = new HashSet<>();

    public UnitOfMeasurement(String code, String description, Set<ModelSpecificationsDetails> modelSpecificationsDetails, Set<Formula> formulas, Set<ServiceNumber> serviceNumbers) {
        this.code = code;
        this.description = description;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
        this.formulas = formulas;
        this.serviceNumbers = serviceNumbers;
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

    public UnitOfMeasurement addServiceNumbers(ServiceNumber serviceNumber){
        serviceNumber.setUnitOfMeasurement(this);
        this.serviceNumbers.add(serviceNumber);
        return this;
    }
}
