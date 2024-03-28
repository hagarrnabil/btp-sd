package com.example.btpsd.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasurementCloudCommand implements Serializable {

    private Long uomCloud;

    private String unitOfMeasure;

    private String unitOfMeasureSAPCode;

    private String unitOfMeasure_1;

    private String unitOfMeasureLongName;

    private String unitOfMeasureName;

}
