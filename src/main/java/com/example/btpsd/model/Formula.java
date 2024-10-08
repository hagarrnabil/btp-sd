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

    @ElementCollection
    @CollectionTable(name = "formula_parameter_ids", joinColumns = @JoinColumn(name = "formula_code"))
    @Column(name = "parameter_id", nullable = false)
    private List<String> parameterIds;

    @ElementCollection
    @CollectionTable(name = "formula_parameter_descriptions", joinColumns = @JoinColumn(name = "formula_code"))
    @Column(name = "parameter_description", nullable = false)
    private List<String> parameterDescriptions;

    @ElementCollection
    @CollectionTable(name = "formula_test_parameters", joinColumns = @JoinColumn(name = "formula_code"))
    @Column(name = "test_parameter")
    private List<Double> testParameters;

    private String formulaLogic;

    private String expression;

    private double result;

}