package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoiceMainItem")
public class InvoiceMainItem implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceMainItemCode;

    //    @Column(nullable = false, unique = true)
    private String uniqueId;

    private String referenceSDDocument;

    private String salesQuotationItem;

    private String salesQuotationItemText;

    private String referenceId;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private String description;

    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    private Double totalHeader;

    private Double profitMargin;

    private Double totalWithProfit;

    private Boolean doNotPrint;

    private Double amountPerUnitWithProfit;

    @OneToMany(mappedBy = "mainItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("subItemList")
    private List<InvoiceSubItem> subItemList = new ArrayList<>();

    @ManyToOne
    private ServiceNumber serviceNumber;

    @OneToOne(mappedBy = "invoiceMainItem", cascade = CascadeType.ALL)
    private ExecutionOrderMain executionOrderMain;


    public InvoiceMainItem addSubItem(InvoiceSubItem subItem) {
        if (!this.subItemList.contains(subItem)) {
            subItem.setMainItem(this);
            this.subItemList.add(subItem);
        }
        return this;
    }

    public Map<String, Double> calculateTotal() {
        if (this.quantity != null && this.amountPerUnit != null) {
            // Basic total calculation
            this.total = this.quantity * this.amountPerUnit;

            // Calculate total with profit margin if set
            if (this.profitMargin != null) {
                this.totalWithProfit = this.total + (this.total * (this.profitMargin / 100));
                this.amountPerUnitWithProfit = this.amountPerUnit + (this.amountPerUnit * (this.profitMargin / 100));
            } else {
                this.totalWithProfit = this.total;
                this.amountPerUnitWithProfit = this.amountPerUnit;
            }
        }

        // Return all values in a map
        Map<String, Double> result = new HashMap<>();
        result.put("totalWithProfit", this.totalWithProfit);
        result.put("amountPerUnit", this.amountPerUnit);
        result.put("amountPerUnitWithProfit", this.amountPerUnitWithProfit); // New field added
        result.put("total", this.total);
        return result;
    }


    public static Double calculateTotalHeader(List<InvoiceMainItem> items) {
        return items.stream()
                .map(item -> item.calculateTotal().get("totalWithProfit"))  // Get the "totalWithProfit" from the map
                .filter(Objects::nonNull)
                .reduce(0.0, Double::sum);
    }

    public void generateUniqueId(String referenceSDDocument, String itemNumber) {
        this.uniqueId = referenceSDDocument + "-" + itemNumber;
    }

//    private boolean hasRole(String role) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth != null && auth.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
//    }
//
//    public String getCurrencyCode() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return currencyCode;
//        } else {
//            return null;
//        }
//    }
//
//    public void setCurrencyCode(String currencyCode) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.currencyCode = currencyCode;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Double getAmountPerUnit() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return amountPerUnit;
//        } else {
//            return null;
//        }
//    }
//
//    public void setAmountPerUnit(Double amountPerUnit) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.amountPerUnit = amountPerUnit;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Double getProfitMargin() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return profitMargin;
//        } else {
//            return null;
//        }
//    }
//
//    public void setProfitMargin(Double profitMargin) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.profitMargin = profitMargin;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Long getInvoiceMainItemCode() {
//        return invoiceMainItemCode;
//    }
//
//    public void setInvoiceMainItemCode(Long invoiceMainItemCode) {
//        this.invoiceMainItemCode = invoiceMainItemCode;
//    }
//
//    public Long getServiceNumberCode() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return serviceNumberCode;
//        } else {
//            return null;
//        }
//    }
//
//    public void setServiceNumberCode(Long serviceNumberCode) {
//        this.serviceNumberCode = serviceNumberCode;
//    }
//
//    public String getUnitOfMeasurementCode() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return unitOfMeasurementCode;
//        } else {
//            return null;
//        }
//    }
//
//    public void setUnitOfMeasurementCode(String unitOfMeasurementCode) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.unitOfMeasurementCode = unitOfMeasurementCode;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public String getFormulaCode() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return formulaCode;
//        } else {
//            return null;
//        }
//    }
//
//    public void setFormulaCode(String formulaCode) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.formulaCode = formulaCode;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public String getDescription() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return description;
//        } else {
//            return null;
//        }
//    }
//
//    public void setDescription(String description) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.description= description;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Integer getQuantity() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return quantity;
//        } else {
//            return null;
//        }
//    }
//
//    public void setQuantity(Integer quantity) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.quantity= quantity;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Double getTotal() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return total;
//        } else {
//            return null;
//        }
//    }
//
//    public void setTotal(Double total) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.total= total;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Double getTotalHeader() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return totalHeader;
//        } else {
//            return null;
//        }
//    }
//
//    public void setTotalHeader(Double totalHeader) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.totalHeader= totalHeader;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Double getTotalWithProfit() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return totalWithProfit;
//        } else {
//            return null;
//        }
//    }
//
//    public void setTotalWithProfit(Double totalWithProfit) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.totalWithProfit= totalWithProfit;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }
//
//    public Boolean getDoNotPrint() {
//        return doNotPrint;
//    }
//
//    public void setDoNotPrint(Boolean doNotPrint) {
//        this.doNotPrint = doNotPrint;
//    }
//
//    public Double getAmountPerUnitWithProfit() {
//        if (hasRole("ROLE_VIEW") || hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            return amountPerUnitWithProfit;
//        } else {
//            return null;
//        }
//    }
//
//    public void setAmountPerUnitWithProfit(Double amountPerUnitWithProfit) {
//        if (hasRole("ROLE_FULL") || hasRole("ROLE_MODIFY")) {
//            this.amountPerUnitWithProfit= amountPerUnitWithProfit;
//        } else {
//            throw new SecurityException("Access denied to set unit of measurement code field");
//        }
//    }

}
