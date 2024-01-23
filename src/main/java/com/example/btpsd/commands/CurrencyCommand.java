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
public class CurrencyCommand implements Serializable {

    private Long currency;

    private String code;

    private String description;

    @JsonIgnore
    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();

    @JsonIgnore
    private Set<ModelSpecificationsCommand> modelSpecificationsCommands = new HashSet<>();

}
