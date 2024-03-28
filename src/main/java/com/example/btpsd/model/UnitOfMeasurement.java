package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "unitOfMeasurement")
public class UnitOfMeasurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitOfMeasurementCode;


    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String code;

    @NotNull
    private String description;

//    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "unitOfMeasurement")
//    @JsonIgnore
//    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();


//    public UnitOfMeasurement addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
//        modelSpecificationsDetails.setUnitOfMeasurement(this);
//        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
//        return this;
//    }
}