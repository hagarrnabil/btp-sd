package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FormulaCommand implements Serializable {

    private Long formulaCode;

    private String formula;

    private String description;

    private Integer numberOfParameters;

    private String parameterId;

    private String parameterDescription;

    private String formulaLogic;

    private String insertParameters;

    private String insertModifiers;

    private Integer enterLength;

    private Integer enterWidth;

    private String expression;

    private Double result;

    private String showResults;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();

}
