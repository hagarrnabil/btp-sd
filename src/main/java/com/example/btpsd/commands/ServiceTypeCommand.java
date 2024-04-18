package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ServiceTypeCommand implements Serializable {

    private Long serviceTypeCode;

    private String serviceId;

    private String description;

    private LocalDate lastChangeDate;

//    @JsonIgnore
//    private Set<ModelSpecificationsDetailsCommand> modelSpecificationsDetailsCommands = new HashSet<>();

//    @JsonIgnore
//    private Set<ServiceNumberCommand> serviceNumberCommands = new HashSet<>();
}
