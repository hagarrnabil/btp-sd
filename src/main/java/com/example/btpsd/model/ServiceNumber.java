package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@IdClass(ServiceNumberId.class)
@Table(name = "serviceNumber")
public class ServiceNumber implements Serializable {

    @Id
    @Column(nullable = false)
    private Long serviceNumberCode;

    @Id
    @Column(nullable = false)
    private Long noServiceNumber;

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

    private String baseUnitOfMeasurement;

    private String toBeConvertedUnitOfMeasurement;

    private String defaultUnitOfMeasurement;

    private Double conversionRule;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    @ManyToOne
    private Formula formula;

    @ManyToOne
    private ServiceType serviceType;

    @ManyToOne
    private MaterialGroup materialGroup;


    public ServiceNumber addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setServiceNumber(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }
}