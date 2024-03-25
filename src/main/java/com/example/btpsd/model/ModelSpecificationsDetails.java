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
@Table(name = "modelSpecificationsDetails")
public class ModelSpecificationsDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long modelSpecDetailsCode;

    private Long serviceNumberCode;

    @Column(unique = true)
    private Long noServiceNumber;

    private Long serviceTypeCode;

    private Long materialGroupCode;

    private Long personnelNumberCode;

    private String unitOfMeasurementCode;

//    @NotNull
    private Long currencyCode;

    private Long formulaCode;

    private Long lineTypeCode;

    private Boolean selectionCheckBox;

    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String lineIndex;

    private Boolean deletionIndicator;

    private String shortText;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer grossPrice;

    private Integer overFulfilmentPercentage;

    private Boolean priceChangedAllowed;

    private Boolean unlimitedOverFulfillment;

    private Integer pricePerUnitOfMeasurement;

    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String externalServiceNumber;

    private Integer netValue;

    private String serviceText;

    private String lineText;

    @Column(unique = true, columnDefinition = "char(225)")
    @Length(max = 225)
    private String lineNumber;

    private String alternatives;

    private Boolean biddersLine;

    private Boolean supplementaryLine;

    private Boolean lotSizeForCostingIsOne;

    @Builder.Default
    private Boolean dontUseFormula = false;


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


    public ModelSpecificationsDetails addModelSpecifications(ModelSpecifications modelSpecifications){
        modelSpecifications.setModelSpecificationsDetails(this);
        this.modelSpecifications.add(modelSpecifications);
        return this;
    }
}
