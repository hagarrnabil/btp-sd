package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
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

    private Boolean standardLine;

    private Boolean blanketLine;

    private Boolean contingencyLine;

    private Boolean atpQuantity;

    private Boolean informatoryLine;

    private Boolean internalLine;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();
}
