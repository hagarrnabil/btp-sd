package com.example.btpsd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.*;

import java.io.Serializable;

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

    private Long modelSpecDetailsCode;

    private Long currencyCode;


    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String modelServSpec;

    @Builder.Default
    private Boolean blockingIndicator = true;

    @Builder.Default
    private Boolean serviceSelection = true;

    @NotNull
    private String description;

    private String searchTerm;

    @ManyToOne
    private ModelSpecificationsDetails modelSpecificationsDetails;

    @ManyToOne
    private Currency currency;

    public ModelSpecifications(Long modelSpecDetailsCode, Long currencyCode, String modelServSpec, Boolean blockingIndicator,
                               Boolean serviceSelection, String description, String searchTerm,
                               ModelSpecificationsDetails modelSpecificationsDetails, Currency currency) {
        this.modelSpecDetailsCode = modelSpecDetailsCode;
        this.currencyCode = currencyCode;
        this.modelServSpec = modelServSpec;
        this.blockingIndicator = blockingIndicator;
        this.serviceSelection = serviceSelection;
        this.description = description;
        this.searchTerm = searchTerm;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
        this.currency = currency;
    }
}
