package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dCloud")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class dCloud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long dCloud;

    @ElementCollection
    private List<String> results = new ArrayList<String>();
//
//    @JsonIgnore
//    private metadataCloud metadataCloud;

    @JsonIgnore
    @JsonProperty("UnitOfMeasure")
    private String unitOfMeasure;

    @JsonProperty("UnitOfMeasureSAPCode")
    private String unitOfMeasureSAPCode;

    @JsonIgnore
    @JsonProperty("UnitOfMeasure_1")
    private String unitOfMeasure_1;

    @JsonProperty("UnitOfMeasureLongName")
    private String unitOfMeasureLongName;

    @JsonProperty("UnitOfMeasureName")
    private String unitOfMeasureName;

}
