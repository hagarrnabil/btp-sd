package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class dCloudCommand implements Serializable {

    private Long dCloud;

//    private List<String> results = new ArrayList<String>();

//    @JsonIgnore
//    private List<String> __metadata = new ArrayList<String>();

    @JsonIgnore
    private String unitOfMeasure;

    private String unitOfMeasureSAPCode;

    @JsonIgnore
    private String unitOfMeasure_1;

    private String unitOfMeasureLongName;

    private String unitOfMeasureName;

}
