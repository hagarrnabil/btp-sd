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


    @ManyToOne
    private MainItem mainItem;


    @ManyToOne
    private SubItem subItem;


    @ManyToOne
    private Invoice invoice;


    @ManyToOne
    private ServiceNumber serviceNumber;

}
