package com.example.btpsd.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.Getter;
// import org.springframework.security.core.Authentication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// import org.springframework.security.core.context.SecurityContextHolder;

// @Getter
// @Setter
@NoArgsConstructor
public class InvoiceMainItemCommand implements Serializable {

    private Long invoiceMainItemCode;

    private String uniqueId;

    private String salesQuotationItem;

    private String referenceSDDocument;

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

    private String temporaryDeletion;

    // private IntermediateMainItemCommand intermediateMainItemCommand;

    @JsonProperty("subItems")
    private List<InvoiceSubItemCommand> subItems = new ArrayList<>();

    public Long getInvoiceMainItemCode() {
        return invoiceMainItemCode;
    }

    public void setInvoiceMainItemCode(Long invoiceMainItemCode) {
        this.invoiceMainItemCode = invoiceMainItemCode;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSalesQuotationItem() {
        return salesQuotationItem;
    }

    public void setSalesQuotationItem(String salesQuotationItem) {
        this.salesQuotationItem = salesQuotationItem;
    }

    public String getReferenceSDDocument() {
        return referenceSDDocument;
    }

    public void setReferenceSDDocument(String referenceSDDocument) {
        this.referenceSDDocument = referenceSDDocument;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Long getServiceNumberCode() {
        return serviceNumberCode;
    }

    public void setServiceNumberCode(Long serviceNumberCode) {
        this.serviceNumberCode = serviceNumberCode;
    }

    public String getUnitOfMeasurementCode() {
        return unitOfMeasurementCode;
    }

    public void setUnitOfMeasurementCode(String unitOfMeasurementCode) {
        this.unitOfMeasurementCode = unitOfMeasurementCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFormulaCode() {
        return formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmountPerUnit() {
        return amountPerUnit;
    }

    public void setAmountPerUnit(Double amountPerUnit) {
        this.amountPerUnit = amountPerUnit;
    }

    public Double getTotal() {
        return total;       
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalHeader() {
        return totalHeader;
    }

    public void setTotalHeader(Double totalHeader) {
        this.totalHeader = totalHeader;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getTotalWithProfit() {
        return totalWithProfit;
    }

    public void setTotalWithProfit(Double totalWithProfit) {
        this.totalWithProfit = totalWithProfit;
    }

    public Boolean getDoNotPrint() {
        return doNotPrint;
    }

    public void setDoNotPrint(Boolean doNotPrint) {
        this.doNotPrint = doNotPrint;
    }

    public Double getAmountPerUnitWithProfit() {
        return amountPerUnitWithProfit;
    }

    public void setAmountPerUnitWithProfit(Double amountPerUnitWithProfit) {
        this.amountPerUnitWithProfit = amountPerUnitWithProfit;
    }

    public String getTemporaryDeletion() {
        return temporaryDeletion;
    }

    public void setTemporaryDeletion(String temporaryDeletion) {
        this.temporaryDeletion = temporaryDeletion;
    }

    public List<InvoiceSubItemCommand> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<InvoiceSubItemCommand> subItems) {
        this.subItems = subItems;
    }

    // private boolean hasRole(String role) {
    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     return auth != null && auth.getAuthorities().stream()
    //             .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    // }
}
