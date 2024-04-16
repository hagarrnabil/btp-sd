package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "resultsCloud")
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"unitOfMeasure", "__metadata", "unitOfMeasure_1"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class resultsCloud implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long resultsCloud;

//    private String id;
//
//    private String uri;
//
//    private String type;

//    @ElementCollection
//    @JsonIgnore
//    private Map<String, Object> __metadata;
//
//    @JsonIgnore
//    @JsonProperty("UnitOfMeasure")
//    private String unitOfMeasure;

    @JsonProperty("UnitOfMeasureSAPCode")
    private String unitOfMeasureSAPCode;

//    @JsonIgnore
//    @JsonProperty("UnitOfMeasure_1")
//    private String unitOfMeasure_1;

    @JsonProperty("UnitOfMeasureLongName")
    private String unitOfMeasureLongName;

    @JsonProperty("UnitOfMeasureName")
    private String unitOfMeasureName;
}
