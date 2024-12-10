package com.example.btpsd.commands;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

// @Getter
// @Setter
@NoArgsConstructor
public class InvoiceMainItemCommand implements Serializable {

    private Long invoiceMainItemCode;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private String description;

    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    private Double profitMargin;

    private Double totalWithProfit;

    private Boolean doNotPrint;

    private Double amountPerUnitWithProfit;

//    private IntermediateMainItemCommand intermediateMainItemCommand;

    @JsonProperty("subItems")
    private List<InvoiceSubItemCommand> subItems = new ArrayList<>();

    public Long getInvoiceMainItemCode() {
        return invoiceMainItemCode;
    }

    public void setInvoiceMainItemCode(Long invoiceMainItemCode) {
        this.invoiceMainItemCode = invoiceMainItemCode;
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
        if(hasRole("Read-All")){
        return description;
        }
        else{
            return "Access Denied";
        }
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

    public List<InvoiceSubItemCommand> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<InvoiceSubItemCommand> subItems) {
        this.subItems = subItems;
    }
    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }
}
