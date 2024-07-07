package com.example.btpsd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mainItem")
public class MainItem implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mainItemCode;

    @ElementCollection
    private List<Long> subItemCode = new ArrayList<Long>();

    private Long serviceNumberCode;

    private Long invoiceCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    @NotNull
    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    @NotNull
    private Double profitMargin;

    private Double totalWithProfit;


    @OneToMany(mappedBy = "mainItem", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Invoice> invoiceList = new ArrayList<>();


    @ManyToOne
    private SubItem subItem;

    @ManyToOne
    private ServiceNumber serviceNumber;

    public MainItem addInvoice(Invoice invoice){
        invoice.setMainItem(this);
        this.invoiceList.add(invoice);
        return this;
    }
}
