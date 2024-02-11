package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FormulaCommand implements Serializable {

    private Long formulaCode;

    private String formula;

    private String description;

    private Integer numberOfParameters;

    private List<Character> parameterIds = new ArrayList<Character>();

    private List<String> parameterDescriptions = new ArrayList<String>();

    private String formulaLogic;

//    private String insertParameters;
//
//    private String insertModifiers;

    private List<Integer> testParameters = new ArrayList<Integer>();

    private String expression;

    private Integer result;

//    private String showResults;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();

    @JsonIgnore
    private Set<ServiceNumberCommand> serviceNumberCommands = new HashSet<>();
}
