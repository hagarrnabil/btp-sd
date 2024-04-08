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
    private List<Double> testParameters = new ArrayList<Double>();

    private String expression;

    private double result;


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "formula")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "formula")
    @JsonIgnore
    private Set<ServiceNumber> serviceNumbers = new HashSet<>();


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