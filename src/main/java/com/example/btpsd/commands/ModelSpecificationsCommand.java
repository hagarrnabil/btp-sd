package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ModelSpecificationsCommand implements Serializable {

    private Long modelSpecCode;

    private List<Long> modelSpecDetailsCode = new ArrayList<Long>();

    private String currencyCode;

    private String modelServSpec;

    private Boolean blockingIndicator;

    private Boolean serviceSelection;

    private String description;

    private String searchTerm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastChangeDate;
}
