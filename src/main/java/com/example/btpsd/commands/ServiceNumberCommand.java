package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ServiceNumberCommand implements Serializable {

    private Long serviceNumberCode;

    private String code;

    private Long formulaCode;

    private String description;

    private Boolean shortTextChangeAllowed = true;

    private Boolean deletionIndicator = true;

    private Boolean mainItem = true;

    private Boolean checkBox = true;

    private Integer numberToBeConverted;

    private Integer convertedNumber;

    private Instant lastChangeDate;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();
}
