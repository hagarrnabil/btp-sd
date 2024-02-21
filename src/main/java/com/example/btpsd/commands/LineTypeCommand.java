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

    private String NZ;

    private String PZ;

    private String EZ;

    private String FZ;

    private String HZ;

    private String IZ;

    private String standardLine;

    private String blanketLine;

    private String contingencyLine;

    private String atpQuantity;

    private String informatoryLine;

    private String internalLine;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();
}
