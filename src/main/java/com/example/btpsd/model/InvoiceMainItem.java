package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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

    @Column(nullable = false, unique = true)
    private String uniqueId;

    private String referenceSDDocument;

    private String salesQuotationItem;

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

}
