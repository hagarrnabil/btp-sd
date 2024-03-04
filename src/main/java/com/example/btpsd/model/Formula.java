package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "formula")
public class Formula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long formulaCode;

    @Column(unique = true, columnDefinition = "char(4)", nullable = false)
    @Length(max = 4)
    private String formula;

    private Long unitOfMeasurementCode;

    @NotNull
    private String description;

    @NotNull
    private Integer numberOfParameters;

    @NotNull
    @ElementCollection
    private List<Character> parameterIds = new ArrayList<Character>();

    @NotNull
    @ElementCollection
    private List<String> parameterDescriptions = new ArrayList<String>();

    private String formulaLogic;

    @ElementCollection
    private List<Integer> testParameters = new ArrayList<Integer>();

    private String expression;

    private Double result;


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "formula")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "formula")
    @JsonIgnore
    private Set<ServiceNumber> serviceNumbers = new HashSet<>();

    @ManyToOne
    private UnitOfMeasurement unitOfMeasurement;

    public Formula(String formula, Long unitOfMeasurementCode, String description, Integer numberOfParameters, List<Character> parameterIds, List<String> parameterDescriptions,
                   String formulaLogic, List<Integer> testParameters, String expression, Double result, Set<ModelSpecificationsDetails> modelSpecificationsDetails,
                   Set<ServiceNumber> serviceNumbers, UnitOfMeasurement unitOfMeasurement)
    {
        this.formula = formula;
        this.unitOfMeasurementCode = unitOfMeasurementCode;
        this.description = description;
        this.numberOfParameters = numberOfParameters;
        this.parameterIds = parameterIds;
        this.parameterDescriptions = parameterDescriptions;
        this.formulaLogic = formulaLogic;
        this.testParameters = testParameters;
        this.expression = expression;
        this.result = result;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
        this.serviceNumbers = serviceNumbers;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Formula addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails) {
        modelSpecificationsDetails.setFormula(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }

    public Formula addServiceNumbers(ServiceNumber serviceNumber) {
        serviceNumber.setFormula(this);
        this.serviceNumbers.add(serviceNumber);
        return this;
    }
}
