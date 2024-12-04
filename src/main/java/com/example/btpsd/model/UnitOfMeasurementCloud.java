package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "unitOfMeasurementCloud")
public class UnitOfMeasurementCloud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long uomCloud;

    @ElementCollection
    private List<String> d = new ArrayList<String>();

    @ElementCollection
    private List<String> results = new ArrayList<String>();

    @JsonIgnore
    @ElementCollection
    private List<String> __metadata = new ArrayList<String>();

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
