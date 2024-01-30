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
@Table(name = "formula")
public class Formula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long formulaCode;

    @Column(unique = true, columnDefinition = "char(4)", nullable = false)
    @Length(max = 4)
    private String formula;

    @NotNull
    private String description;

    @NotNull
    private Integer numberOfParameters;


    @Column(unique = true, columnDefinition = "char(1)", nullable = false)
    @Length(max = 1)
    private String parameterId;

    @Column(unique = true, columnDefinition = "char(100)", nullable = false)
    @Length(max = 100)
    private String parameterDescription;

    private String formulaLogic;

    private String insertParameters;

    private String insertModifiers;

    private Integer enterLength;

    private Integer enterWidth;

    private String expression;

    private Double result;

    private String showResults;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "formula")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    public Formula(String formula, String description, Integer numberOfParameters, String parameterId,
                   String parameterDescription, String formulaLogic, String insertParameters, String insertModifiers,
                   Integer enterLength, Integer enterWidth, String expression, Double result, String showResults,
                   Set<ModelSpecificationsDetails> modelSpecificationsDetails) {
        this.formula = formula;
        this.description = description;
        this.numberOfParameters = numberOfParameters;
        this.parameterId = parameterId;
        this.parameterDescription = parameterDescription;
        this.formulaLogic = formulaLogic;
        this.insertParameters = insertParameters;
        this.insertModifiers = insertModifiers;
        this.enterLength = enterLength;
        this.enterWidth = enterWidth;
        this.expression = expression;
        this.result = result;
        this.showResults = showResults;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
    }

    public Formula addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setFormula(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }
}
