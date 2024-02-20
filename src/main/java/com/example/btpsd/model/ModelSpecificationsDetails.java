package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "modelSpecificationsDetails")
public class ModelSpecificationsDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long modelSpecDetailsCode;

    private Long serviceNumberCode;

    private Long serviceTypeCode;

    private Long materialGroupCode;

    private Long personnelNumberCode;

    private Long unitOfMeasurementCode;

    private Long currencyCode;

    private Long formulaCode;

    private Long lineTypeCode;

    @Builder.Default
    private Boolean selectionCheckBox = true;

    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String lineIndex;

    @Builder.Default
    private Boolean deletionIndicator = true;

    private String shortText;

    private Integer quantity;

    private Integer grossPrice;

    private Integer overFulfilmentPercentage;

    @Builder.Default
    private Boolean priceChangedAllowed = true;

    @Builder.Default
    private Boolean unlimitedOverFulfillment = true;

    private Integer pricePerUnitOfMeasurement;

    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String externalServiceNumber;

    private Integer netValue;

    private String serviceText;

    private String lineText;

//    private String formula;

    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String lineNumber;

    private String alternatives;

    @Builder.Default
    private Boolean biddersLine = true;

    @Builder.Default
    private Boolean supplementaryLine = true;

    @Builder.Default
    private Boolean lotSizeForCostingIsOne = true;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "modelSpecificationsDetails")
    @JsonIgnore
    private Set<ModelSpecifications> modelSpecifications = new HashSet<>();

    @ManyToOne
    private ServiceNumber serviceNumber;

    @ManyToOne
    private UnitOfMeasurement unitOfMeasurement;

    @ManyToOne
    private MaterialGroup materialGroup;

    @ManyToOne
    private ServiceType serviceType;

    @ManyToOne
    private PersonnelNumber personnelNumber;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Formula formula;

    @ManyToOne
    private LineType lineType;

    public ModelSpecificationsDetails(Long serviceNumberCode, Long serviceTypeCode,
                                      Long materialGroupCode, Long personnelNumberCode,
                                      Long unitOfMeasurementCode, Long currencyCode,
                                      Boolean selectionCheckBox, String lineIndex,
                                      Boolean deletionIndicator, String shortText,
                                      Integer quantity, Integer grossPrice,
                                      Integer overFulfilmentPercentage,
                                      Boolean priceChangedAllowed,
                                      Boolean unlimitedOverFulfillment,
                                      Integer pricePerUnitOfMeasurement,
                                      String externalServiceNumber, Integer netValue,
                                      String serviceText, String lineText, String lineNumber,
                                      String alternatives, Boolean biddersLine,
                                      Boolean supplementaryLine, Boolean lotSizeForCostingIsOne,
                                      Set<ModelSpecifications> modelSpecifications,
                                      ServiceNumber serviceNumber,
                                      UnitOfMeasurement unitOfMeasurement, MaterialGroup materialGroup,
                                      ServiceType serviceType, PersonnelNumber personnelNumber,
                                      Currency currency, Formula formula) {
        this.serviceNumberCode = serviceNumberCode;
        this.serviceTypeCode = serviceTypeCode;
        this.materialGroupCode = materialGroupCode;
        this.personnelNumberCode = personnelNumberCode;
        this.unitOfMeasurementCode = unitOfMeasurementCode;
        this.currencyCode = currencyCode;
        this.selectionCheckBox = selectionCheckBox;
        this.lineIndex = lineIndex;
        this.deletionIndicator = deletionIndicator;
        this.shortText = shortText;
        this.quantity = quantity;
        this.grossPrice = grossPrice;
        this.overFulfilmentPercentage = overFulfilmentPercentage;
        this.priceChangedAllowed = priceChangedAllowed;
        this.unlimitedOverFulfillment = unlimitedOverFulfillment;
        this.pricePerUnitOfMeasurement = pricePerUnitOfMeasurement;
        this.externalServiceNumber = externalServiceNumber;
        this.netValue = netValue;
        this.serviceText = serviceText;
        this.lineText = lineText;
        this.lineNumber = lineNumber;
        this.alternatives = alternatives;
        this.biddersLine = biddersLine;
        this.supplementaryLine = supplementaryLine;
        this.lotSizeForCostingIsOne = lotSizeForCostingIsOne;
        this.modelSpecifications = modelSpecifications;
        this.serviceNumber = serviceNumber;
        this.unitOfMeasurement = unitOfMeasurement;
        this.materialGroup = materialGroup;
        this.serviceType = serviceType;
        this.personnelNumber = personnelNumber;
        this.currency = currency;
        this.formula = formula;
    }

    public ModelSpecificationsDetails addModelSpecifications(ModelSpecifications modelSpecifications){
        modelSpecifications.setModelSpecificationsDetails(this);
        this.modelSpecifications.add(modelSpecifications);
        return this;
    }
}
