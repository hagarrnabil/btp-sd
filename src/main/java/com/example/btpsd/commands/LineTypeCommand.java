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
public class LineTypeCommand implements Serializable {

    private Long lineTypeCode;

    private String code;

    private String nz;

    private String pz;

    private String ez;

    private String fz;

    private String hz;

    private String iz;

    private String standardLine;

    private String blanketLine;

    private String contingencyLine;

    private String atpQuantity;

    private String informatoryLine;

    private String internalLine;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();
}
