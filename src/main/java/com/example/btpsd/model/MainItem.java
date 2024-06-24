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
@Table(name = "mainItem")
public class MainItem implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mainItemCode;

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


    @OneToMany(mappedBy = "mainItem", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<SubItem> subItemList = new ArrayList<>();


    @ManyToOne
    private ServiceNumber serviceNumber;

    @ManyToOne
    private Invoice invoice;


    public MainItem addSubItem(SubItem subItem){
        subItem.setMainItem(this);
        this.subItemList.add(subItem);
        return this;
    }
}
