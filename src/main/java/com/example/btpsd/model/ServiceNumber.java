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

    @NotNull
    private String description;

    @Builder.Default
    private Boolean shortTextChangeAllowed = true;

    @Builder.Default
    private Boolean deletionIndicator = true;

    @Builder.Default
    private Boolean mainItem = true;

    @Builder.Default
    private Boolean checkBox = true;

    private Integer numberToBeConverted;

    private Integer convertedNumber;

    @CreationTimestamp
    private Instant lastChangeDate;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    public ServiceNumber(String code, String description, Set<ModelSpecificationsDetails> modelSpecificationsDetails) {
        this.code = code;
        this.description = description;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
    }

    public ServiceNumber addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setServiceNumber(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }
}
