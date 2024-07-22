//package com.example.btpsd.commands;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class InvoiceCommand implements Serializable {
//
//    private Long invoiceCode;
//
//    private Long serviceNumberCode;
//
//    private List<Long> mainItemCode = new ArrayList<Long>();
//
//    private List<Long> subItemCode = new ArrayList<Long>();
//
//    private String unitOfMeasurementCode;
//
//    private String currencyCode;
//
//    private String formulaCode;
//
//    private Integer quantity;
//
//    private Double amountPerUnit;
//
//    private Double total;
//
//    private Double profitMargin;
//
//    private Double totalWithProfit;
//
////    @JsonIgnore
////    private InvoiceSubItemCommand subItemCommand;
////////
//}
