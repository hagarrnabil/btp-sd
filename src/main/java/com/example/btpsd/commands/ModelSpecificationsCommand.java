package com.example.btpsd.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ModelSpecificationsCommand implements Serializable {

    private Long modelSpecCode;
    private Long modelSpecDetailsCode;
    private Long currencyCode;
    private String modelServSpec;
    private Boolean blockingIndicator = true;
    private Boolean serviceSelection = true;
    private String description;
    private String searchTerm;

}
