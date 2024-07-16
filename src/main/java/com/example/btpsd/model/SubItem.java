package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private Long mainItemCode;

    private Long serviceNumberCode;

//    @NotNull
    private String unitOfMeasurementCode;

//    @NotNull
    private String currencyCode;

    private String formulaCode;

    private String description;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double amountPerUnit;

    private Double total;


    @ManyToOne
    @JoinColumn(name = "main_item_id")
    private MainItem mainItem;


    @ManyToOne
    private ServiceNumber serviceNumber;


}
