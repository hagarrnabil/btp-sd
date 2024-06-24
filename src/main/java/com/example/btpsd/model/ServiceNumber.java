package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDate;
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

    private Long noServiceNumber;

    @NotNull
    private String searchTerm;

    @NotNull
    private String serviceTypeCode;

    private String materialGroupCode;

    @NotNull
    private String description;

    private Boolean shortTextChangeAllowed;

    private Boolean deletionIndicator;

    private Boolean mainItem;

    private Integer numberToBeConverted;

    private Integer convertedNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastChangeDate;

    private String serviceText;

    private String baseUnitOfMeasurement;

    private String toBeConvertedUnitOfMeasurement;

    private String defaultUnitOfMeasurement;


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<Invoice> invoiceSet = new HashSet<>();


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<MainItem> mainItemSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<SubItem> subItemSet = new HashSet<>();


    public ServiceNumber addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setServiceNumber(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }

    public ServiceNumber addInvoice(Invoice invoice){
        invoice.setServiceNumber(this);
        this.invoiceSet.add(invoice);
        return this;
    }

    public ServiceNumber addMainItem(MainItem mainItem){
        mainItem.setServiceNumber(this);
        this.mainItemSet.add(mainItem);
        return this;
    }

    public ServiceNumber addSubItem(SubItem subItem){
        subItem.setServiceNumber(this);
        this.subItemSet.add(subItem);
        return this;
    }
}