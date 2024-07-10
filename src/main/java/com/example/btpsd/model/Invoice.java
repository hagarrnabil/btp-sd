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
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceCode;

    private Long serviceNumberCode;

    @ElementCollection
    private List<Long> mainItemCode = new ArrayList<Long>();

    @ElementCollection
    private List<Long> subItemCode = new ArrayList<Long>();

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


    @ManyToOne
    private MainItem mainItem;


    @ManyToOne
    private SubItem subItem;


    @ManyToOne
    private ServiceNumber serviceNumber;

}
