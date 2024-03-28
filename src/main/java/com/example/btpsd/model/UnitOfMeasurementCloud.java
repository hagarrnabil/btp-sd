package com.example.btpsd.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "unitOfMeasurementCloud")
public class UnitOfMeasurementCloud implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uomCloud;

    private String unitOfMeasure;

    private String unitOfMeasureSAPCode;

    private String unitOfMeasure_1;

    private String unitOfMeasureLongName;

    private String unitOfMeasureName;

}
