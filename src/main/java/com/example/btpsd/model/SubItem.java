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
@Table(name = "subItem")
public class SubItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subItemCode;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    @NotNull
    private Integer quantity;

    private Double amountPerUnit;

    private Double total;


    @OneToMany(mappedBy = "subItem", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<MainItem> mainItemList = new ArrayList<>();


    @OneToMany(mappedBy = "subItem", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Invoice> invoiceList = new ArrayList<>();


    @ManyToOne
    private ServiceNumber serviceNumber;


    public SubItem addMainItem(MainItem mainItem){
        mainItem.setSubItem(this);
        this.mainItemList.add(mainItem);
        return this;
    }

    public SubItem addInvoice(Invoice invoice){
        invoice.setSubItem(this);
        this.invoiceList.add(invoice);
        return this;
    }

}
