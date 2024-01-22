package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "modelSpecifications")
public class ModelSpecifications implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String modelServSpec;

    @Builder.Default
    private Boolean blockingIndicator = true;

    @Builder.Default
    private Boolean serviceSelection = true;

    @NonNull
    private String description;

    private String searchTerm;

    @ManyToOne
    private ModelSpecificationsDetails modelSpecificationsDetails;

    @ManyToOne
    private Currency currency;

}
