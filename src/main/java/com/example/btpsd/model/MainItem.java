package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @NotNull
    private Double profitMargin;

    private Double totalWithProfit;


    @OneToMany(mappedBy = "mainItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("subItemList")
    private List<SubItem> subItemList = new ArrayList<>();

    @ManyToOne
    private ServiceNumber serviceNumber;


    public MainItem addSubItem(SubItem subItem) {
        if (!this.subItemList.contains(subItem)) {
            subItem.setMainItem(this);
            this.subItemList.add(subItem);
        }
        return this;
    }


}
