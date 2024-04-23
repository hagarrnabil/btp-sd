package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
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

    private String serviceTypeCode;

    private String materialGroupCode;

    private String personnelNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private String lineTypeCode;

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

    @Column(columnDefinition = "char(225)")
    @Length(max = 225)
    private String externalServiceNumber;

    private Integer netValue;

    private String serviceText;

    private String lineText;

    @Column(columnDefinition = "char(225)")
    @Length(max = 225)
    private String lineNumber;

    private String alternatives;

    private Boolean biddersLine;

    private Boolean supplementaryLine;

    private Boolean lotSizeForCostingIsOne;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastChangeDate;


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "modelSpecificationsDetails")
    @JsonIgnore
    private Set<ModelSpecifications> modelSpecifications = new HashSet<>();

    @ManyToOne
    private ServiceNumber serviceNumber;


    public ModelSpecificationsDetails addModelSpecifications(ModelSpecifications modelSpecifications){
        modelSpecifications.setModelSpecificationsDetails(this);
        this.modelSpecifications.add(modelSpecifications);
        return this;
    }
}
