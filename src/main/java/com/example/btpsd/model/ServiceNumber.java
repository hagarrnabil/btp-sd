package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "serviceNumber")
public class ServiceNumber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceNumberCode;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String code;

    private Long formulaCode;

    private Long unitOfMeasurementCode;

    private Long serviceTypeCode;

    private Long materialGroupCode;

    @NotNull
    private String description;

    private Boolean shortTextChangeAllowed;

    private Boolean deletionIndicator;

    private Boolean mainItem;

    private Integer numberToBeConverted;

    private Integer convertedNumber;

    @CreationTimestamp
    private Instant lastChangeDate;

    private String serviceText;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    @ManyToOne
    private Formula formula;

    @ManyToOne
    private UnitOfMeasurement baseUnitOfMeasurement;

    @ManyToOne
    private UnitOfMeasurement toBeConvertedUnitOfMeasurement;

    @ManyToOne
    private UnitOfMeasurement convertedUnitOfMeasurement;

    @ManyToOne
    private ServiceType serviceType;

    @ManyToOne
    private MaterialGroup materialGroup;

    public ServiceNumber(String code, Long formulaCode, Long unitOfMeasurementCode, Long serviceTypeCode, Long materialGroupCode, String description, Boolean shortTextChangeAllowed,
                         Boolean deletionIndicator, Boolean mainItem, Integer numberToBeConverted, Integer convertedNumber, Instant lastChangeDate, String serviceText,
                         Set<ModelSpecificationsDetails> modelSpecificationsDetails, Formula formula, UnitOfMeasurement baseUnitOfMeasurement, UnitOfMeasurement toBeConvertedUnitOfMeasurement,
                         UnitOfMeasurement convertedUnitOfMeasurement, ServiceType serviceType, MaterialGroup materialGroup)
    {
        this.code = code;
        this.formulaCode = formulaCode;
        this.unitOfMeasurementCode = unitOfMeasurementCode;
        this.serviceTypeCode = serviceTypeCode;
        this.materialGroupCode = materialGroupCode;
        this.description = description;
        this.shortTextChangeAllowed = shortTextChangeAllowed;
        this.deletionIndicator = deletionIndicator;
        this.mainItem = mainItem;
        this.numberToBeConverted = numberToBeConverted;
        this.convertedNumber = convertedNumber;
        this.lastChangeDate = lastChangeDate;
        this.serviceText = serviceText;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
        this.formula = formula;
        this.baseUnitOfMeasurement = baseUnitOfMeasurement;
        this.toBeConvertedUnitOfMeasurement = toBeConvertedUnitOfMeasurement;
        this.convertedUnitOfMeasurement = convertedUnitOfMeasurement;
        this.serviceType = serviceType;
        this.materialGroup = materialGroup;
    }

    public ServiceNumber addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setServiceNumber(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }
}
