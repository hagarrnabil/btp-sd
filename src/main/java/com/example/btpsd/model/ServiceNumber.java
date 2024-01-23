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
@Table(name = "serviceNumber")
public class ServiceNumber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceNumber;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String code;

    @NonNull
    private String description;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();
}
