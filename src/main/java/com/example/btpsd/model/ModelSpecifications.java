package com.example.btpsd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Long modelSpecCode;


//    private Long modelSpecDetailsCode;

    @ElementCollection
    private List<Long> modelSpecDetailsCode = new ArrayList<Long>();

    private Long currencyCode;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String modelServSpec;

    private Boolean blockingIndicator;

    private Boolean serviceSelection;

    @NotNull
    private String description;

    private String searchTerm;

    @ManyToOne
    private ModelSpecificationsDetails modelSpecificationsDetails;

    @ManyToOne
    private Currency currency;
}
