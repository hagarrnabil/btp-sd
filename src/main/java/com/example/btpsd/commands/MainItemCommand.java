package com.example.btpsd.commands;

import com.example.btpsd.model.SubItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MainItemCommand implements Serializable {

    private Long mainItemCode;

    private Long serviceNumberCode;

    private String unitOfMeasurementCode;

    private String currencyCode;

    private String formulaCode;

    private Integer quantity;

    private Double amountPerUnit;

    private Double total;

    private Double profitMargin;

    private Double totalWithProfit;

    @JsonProperty("subItems")
    private List<SubItemCommand> subItems = new ArrayList<>();

}
