package com.example.btpsd.commands;

import com.example.btpsd.model.SubItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private Integer profitMargin;

    private Double totalWithProfit;

    @JsonIgnore
    private List<SubItemCommand> subItemCommandList = new ArrayList<>();

}
