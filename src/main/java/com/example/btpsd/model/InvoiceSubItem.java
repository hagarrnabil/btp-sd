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
@Table(name = "invoiceSubItem")
public class InvoiceSubItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceSubItemCode;

    private Long invoiceMainItemCode;

    private Long serviceNumberCode;

//    @NotNull
    private String unitOfMeasurementCode;

//    @NotNull
    private String currencyCode;

    private String formulaCode;

    private String description;

//    @NotNull
    private Integer quantity;

//    @NotNull
    private Double amountPerUnit;

    private Double total;


    @ManyToOne
    @JoinColumn(name = "main_item_id")
    private InvoiceMainItem mainItem;


    @ManyToOne
    private ServiceNumber serviceNumber;


}
