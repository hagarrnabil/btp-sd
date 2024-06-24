package com.example.btpsd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

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


    @ManyToOne
    private ServiceNumber serviceNumber;


    @ManyToOne
    private Invoice invoice;


    @ManyToOne
    private MainItem mainItem;

}
