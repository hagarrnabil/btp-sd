package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UnitOfMeasurementCloudCommand implements Serializable {

    private Long uomCloud;

    @ElementCollection
    private List<String> d = new ArrayList<String>();

    @ElementCollection
    private List<String> results = new ArrayList<String>();

    @JsonIgnore
    @ElementCollection
    private List<String> __metadata = new ArrayList<String>();

    @JsonIgnore
    private String unitOfMeasure;

    private String unitOfMeasureSAPCode;

    @JsonIgnore
    private String unitOfMeasure_1;

    private String unitOfMeasureLongName;

    private String unitOfMeasureName;

}
