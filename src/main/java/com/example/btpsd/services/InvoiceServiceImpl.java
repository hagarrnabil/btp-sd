package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceCommand;
import com.example.btpsd.converters.InvoiceCommandToInvoice;
import com.example.btpsd.converters.InvoiceToInvoiceCommand;
import com.example.btpsd.model.Invoice;
import com.example.btpsd.model.MainItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.model.SubItem;
import com.example.btpsd.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceCommandToInvoice invoiceCommandToInvoice;
    private final InvoiceToInvoiceCommand invoiceToInvoiceCommand;


    @Override
    @Transactional
    public Set<InvoiceCommand> getInvoiceCommands() {

        return StreamSupport.stream(invoiceRepository.findAll()
                        .spliterator(), false)
                .map(invoiceToInvoiceCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public Invoice findById(Long l) {

        Optional<Invoice> invoiceOptional = invoiceRepository.findById(l);

        if (!invoiceOptional.isPresent()) {
            throw new RuntimeException("Invoice Not Found!");
        }

        return invoiceOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        invoiceRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public InvoiceCommand saveInvoiceCommand(InvoiceCommand command) {

        Invoice detachedInvoice = invoiceCommandToInvoice.convert(command);
        Invoice savedInvoice = invoiceRepository.save(detachedInvoice);
        log.debug("Saved Invoice Id:" + savedInvoice.getInvoiceCode());
        return invoiceToInvoiceCommand.convert(savedInvoice);


    }

    @Override
    public Invoice updateInvoice(InvoiceCommand newInvoiceCommand, Long l) {

        return invoiceRepository.findById(l).map(oldInvoice -> {
            if (newInvoiceCommand.getCurrencyCode() != oldInvoice.getCurrencyCode())
                oldInvoice.setCurrencyCode(newInvoiceCommand.getCurrencyCode());
            if (newInvoiceCommand.getFormulaCode() != oldInvoice.getFormulaCode())
                oldInvoice.setFormulaCode(newInvoiceCommand.getFormulaCode());
            if (newInvoiceCommand.getUnitOfMeasurementCode() != oldInvoice.getUnitOfMeasurementCode())
                oldInvoice.setUnitOfMeasurementCode(newInvoiceCommand.getUnitOfMeasurementCode());
            if (newInvoiceCommand.getAmountPerUnit() != oldInvoice.getAmountPerUnit())
                oldInvoice.setAmountPerUnit(newInvoiceCommand.getAmountPerUnit());
            if (newInvoiceCommand.getQuantity() != oldInvoice.getQuantity())
                oldInvoice.setQuantity(newInvoiceCommand.getQuantity());
            if (newInvoiceCommand.getProfitMargin() != oldInvoice.getProfitMargin())
                oldInvoice.setProfitMargin(newInvoiceCommand.getProfitMargin());
            if (newInvoiceCommand.getServiceNumberCode() != null) {
                ServiceNumber serviceNumber = new ServiceNumber();
                serviceNumber.setServiceNumberCode(newInvoiceCommand.getServiceNumberCode());
                oldInvoice.setServiceNumber(serviceNumber);
                serviceNumber.addInvoice(oldInvoice);
            }
            if (newInvoiceCommand.getMainItemCode() != null) {
                MainItem mainItem = new MainItem();
                mainItem.setMainItemCode(newInvoiceCommand.getMainItemCode());
                oldInvoice.setMainItem(mainItem);
                mainItem.addInvoice(oldInvoice);
            }
            if (newInvoiceCommand.getSubItemCode() != null) {
                oldInvoice.setSubItemCode(newInvoiceCommand.getSubItemCode());
            }
            return invoiceRepository.save(oldInvoice);
        }).orElseThrow(() -> new RuntimeException("Invoice not found"));
        }

    @Override
    @Transactional
    public InvoiceCommand findInvoiceCommandById(Long l) {

        return invoiceToInvoiceCommand.convert(findById(l));

    }
}
