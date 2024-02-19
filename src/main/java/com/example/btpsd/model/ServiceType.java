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
@Table(name = "serviceType")
public class ServiceType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceTypeCode;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String serviceId;

    @NotNull
    private String description;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceType")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceType")
    @JsonIgnore
    private Set<ServiceNumber> serviceNumbers = new HashSet<>();

    public ServiceType(String serviceId, String description, Set<ModelSpecificationsDetails> modelSpecificationsDetails, Set<ServiceNumber> serviceNumbers) {
        this.serviceId = serviceId;
        this.description = description;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
        this.serviceNumbers = serviceNumbers;
    }

    public ServiceType addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setServiceType(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }

    public ServiceType addServiceNumbers(ServiceNumber serviceNumber){
        serviceNumber.setServiceType(this);
        this.serviceNumbers.add(serviceNumber);
        return this;
    }
}
