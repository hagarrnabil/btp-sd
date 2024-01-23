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
    private Long modelSpecDetails;


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

    private Integer netValue = quantity * grossPrice;

    private String serviceText;

    private String lineText;

    private String formula;

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

    public ModelSpecificationsDetails addModelSpecifications(ModelSpecifications modelSpecifications){
        modelSpecifications.setModelSpecificationsDetails(this);
        this.modelSpecifications.add(modelSpecifications);
        return this;
    }
}
