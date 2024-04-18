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
@Table(name = "currency")
public class Currency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long currencyCode;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String code;

    @NotNull
    private String description;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "currency")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();
//
//    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "currency")
//    @JsonIgnore
//    private Set<ModelSpecifications> modelSpecifications = new HashSet<>();
//
//
//    public Currency addModelSpecifications(ModelSpecifications modelSpecifications) {
//        modelSpecifications.setCurrency(this);
//        this.modelSpecifications.add(modelSpecifications);
//        return this;
//    }
//
    public Currency addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails) {
        modelSpecificationsDetails.setCurrency(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }
}
