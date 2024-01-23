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
@Table(name = "serviceType")
public class ServiceType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceType;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String code;

    @NonNull
    private String description;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceType")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();
}
