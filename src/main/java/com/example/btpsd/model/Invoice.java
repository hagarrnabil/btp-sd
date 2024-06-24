package com.example.btpsd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceCode;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    @NotNull
    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    private Integer profitMargin;

    private Double totalWithProfit;


    @OneToMany(mappedBy = "invoice", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<MainItem> mainItemList = new ArrayList<>();


    @OneToMany(mappedBy = "invoice", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<SubItem> subItemList = new ArrayList<>();


    @ManyToOne
    private ServiceNumber serviceNumber;


    public Invoice addMainItem(MainItem mainItem){
        mainItem.setInvoice(this);
        this.mainItemList.add(mainItem);
        return this;
    }

    public Invoice addSubItem(SubItem subItem){
        subItem.setInvoice(this);
        this.subItemList.add(subItem);
        return this;
    }
}
