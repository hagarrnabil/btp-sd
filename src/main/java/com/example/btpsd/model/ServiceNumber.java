package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
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

    @Column(unique = true)
    private String serviceNumberCodeString;

    private Long noServiceNumber;

    private String searchTerm;

    private String serviceTypeCode;

    private String materialGroupCode;

    private String unitOfMeasurementCode;

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
    private Set<InvoiceMainItem> mainItemSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<InvoiceSubItem> subItemSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<ServiceInvoiceMain> serviceInvoiceMainSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "serviceNumber")
    @JsonIgnore
    private Set<ExecutionOrderMain> executionOrderMainSet = new HashSet<>();

    public ServiceNumber addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails){
        modelSpecificationsDetails.setServiceNumber(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }

    public ServiceNumber addMainItem(InvoiceMainItem mainItem){
        mainItem.setServiceNumber(this);
        this.mainItemSet.add(mainItem);
        return this;
    }

    public ServiceNumber addSubItem(InvoiceSubItem subItem){
        subItem.setServiceNumber(this);
        this.subItemSet.add(subItem);
        return this;
    }

    public ServiceNumber addServiceInvoiceMain(ServiceInvoiceMain serviceInvoiceMain){
        serviceInvoiceMain.setServiceNumber(this);
        this.serviceInvoiceMainSet.add(serviceInvoiceMain);
        return this;
    }

    public ServiceNumber addExecutionOrderMainItem(ExecutionOrderMain executionOrderMain){
        executionOrderMain.setServiceNumber(this);
        this.executionOrderMainSet.add(executionOrderMain);
        return this;
    }

//    public Long getServiceNumberCode() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return serviceNumberCode;
//        } else {
//            return null; // Return null if access is denied
//        }
//    }
//
//    // Getter for unitOfMeasurementCode
//    public String getUnitOfMeasurementCode() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return unitOfMeasurementCode;
//        } else {
//            return null; // Return null if access is denied
//        }
//    }
//
//    // Getter for description
//    public String getDescription() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return description;
//        } else {
//            return null; // Return null if access is denied
//        }
//    }
//
//    // Setter for unitOfMeasurementCode
//    public void setUnitOfMeasurementCode(String unitOfMeasurementCode) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.unitOfMeasurementCode = unitOfMeasurementCode;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    // Setter for description
//    public void setDescription(String description) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.description = description;
//        } else {
//            throw new SecurityException("Access denied to set description field");
//        }
//    }
//
//    // Helper method to check roles
//    private boolean hasRole(String role) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth != null && auth.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
//    }

}