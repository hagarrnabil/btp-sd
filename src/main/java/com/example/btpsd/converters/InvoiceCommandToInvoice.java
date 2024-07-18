//package com.example.btpsd.converters;
//
//import com.example.btpsd.commands.InvoiceCommand;
//import com.example.btpsd.model.Invoice;
//import com.example.btpsd.model.InvoiceMainItem;
//import com.example.btpsd.model.ServiceNumber;
//import com.example.btpsd.model.InvoiceSubItem;
//import io.micrometer.common.lang.Nullable;
//import lombok.RequiredArgsConstructor;
//import lombok.Synchronized;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class InvoiceCommandToInvoice implements Converter<InvoiceCommand, Invoice> {
//
//    @Synchronized
//    @Nullable
//    @Override
//    public Invoice convert(InvoiceCommand source) {
//
//        if (source == null) {
//            return null;
//        }
//
//        final Invoice invoice = new Invoice();
//        invoice.setInvoiceCode(source.getInvoiceCode());
//        invoice.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
//        invoice.setCurrencyCode(source.getCurrencyCode());
//        invoice.setFormulaCode(source.getFormulaCode());
//        invoice.setQuantity(source.getQuantity());
//        invoice.setAmountPerUnit(source.getAmountPerUnit());
//        invoice.setTotal(source.getQuantity() * source.getAmountPerUnit());
//        invoice.setProfitMargin(source.getProfitMargin());
//        invoice.setTotalWithProfit( (invoice.getProfitMargin()/100) * invoice.getTotal() );
//        if (source.getServiceNumberCode() != null) {
//            ServiceNumber serviceNumber = new ServiceNumber();
//            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
//            invoice.setServiceNumber(serviceNumber);
//            serviceNumber.addInvoice(invoice);
//        }
////        for (int i = 0; i < source.getSubItemCode().size(); i++) {
////            invoice.setSubItemCode(source.getSubItemCode());
////        }
////        for (int i = 0; i < source.getMainItemCode().size(); i++) {
////            invoice.setMainItemCode(source.getMainItemCode());
////        }
//        return invoice;
//    }
//}
