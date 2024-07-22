package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.converters.InvoiceSubItemCommandToInvoiceSubItem;
import com.example.btpsd.converters.InvoiceSubItemToInvoiceSubItemCommand;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.InvoiceSubItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.InvoiceSubItemRepository;
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
public class InvoiceSubItemServiceImpl implements InvoiceSubItemService {

    private final InvoiceSubItemRepository invoiceSubItemRepository;
    private final InvoiceSubItemCommandToInvoiceSubItem invoiceSubItemCommandToInvoiceSubItem;
    private final InvoiceSubItemToInvoiceSubItemCommand invoiceSubItemToInvoiceSubItemCommand;

    @Override
    @Transactional
    public Set<InvoiceSubItemCommand> getSubItemCommands() {

        return StreamSupport.stream(invoiceSubItemRepository.findAll()
                        .spliterator(), false)
                .map(invoiceSubItemToInvoiceSubItemCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public InvoiceSubItem findById(Long l) {

        Optional<InvoiceSubItem> subItemOptional = invoiceSubItemRepository.findById(l);

        if (!subItemOptional.isPresent()) {
            throw new RuntimeException("Sub Item Not Found!");
        }

        return subItemOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        invoiceSubItemRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public InvoiceSubItemCommand saveSubItemCommand(InvoiceSubItemCommand command) {

        InvoiceSubItem detachedSubItem = invoiceSubItemCommandToInvoiceSubItem.convert(command);
        InvoiceSubItem savedSubItem = invoiceSubItemRepository.save(detachedSubItem);
        log.debug("Saved InvoiceSubItem Id:" + savedSubItem.getInvoiceSubItemCode());
        return invoiceSubItemToInvoiceSubItemCommand.convert(savedSubItem);

    }

    @Override
    public InvoiceSubItem updateSubItem(InvoiceSubItemCommand newSubItemCommand, Long l) {

        return invoiceSubItemRepository.findById(l).map(oldSubItem -> {
            if (newSubItemCommand.getCurrencyCode() != oldSubItem.getCurrencyCode())
                oldSubItem.setCurrencyCode(newSubItemCommand.getCurrencyCode());
            if (newSubItemCommand.getFormulaCode() != oldSubItem.getFormulaCode())
                oldSubItem.setFormulaCode(newSubItemCommand.getFormulaCode());
            if (newSubItemCommand.getUnitOfMeasurementCode() != oldSubItem.getUnitOfMeasurementCode())
                oldSubItem.setUnitOfMeasurementCode(newSubItemCommand.getUnitOfMeasurementCode());
            if (newSubItemCommand.getAmountPerUnit() != oldSubItem.getAmountPerUnit())
                oldSubItem.setAmountPerUnit(newSubItemCommand.getAmountPerUnit());
            if (newSubItemCommand.getDescription() != oldSubItem.getDescription())
                oldSubItem.setDescription(newSubItemCommand.getDescription());
            if (newSubItemCommand.getQuantity() != oldSubItem.getQuantity())
                oldSubItem.setQuantity(newSubItemCommand.getQuantity());
            if (newSubItemCommand.getTotal() != oldSubItem.getTotal())
                oldSubItem.setTotal(newSubItemCommand.getTotal());
            if (newSubItemCommand.getServiceNumberCode() != null) {
                ServiceNumber serviceNumber = new ServiceNumber();
                serviceNumber.setServiceNumberCode(newSubItemCommand.getServiceNumberCode());
                oldSubItem.setServiceNumber(serviceNumber);
                serviceNumber.addSubItem(oldSubItem);
            }
            if (newSubItemCommand.getInvoiceMainItemCode() != null) {
                InvoiceMainItem mainItem = new InvoiceMainItem();
                mainItem.setInvoiceMainItemCode(newSubItemCommand.getInvoiceMainItemCode());
                oldSubItem.setMainItem(mainItem);
                mainItem.addSubItem(oldSubItem);
            }
            return invoiceSubItemRepository.save(oldSubItem);
        }).orElseThrow(() -> new RuntimeException("Sub Item not found"));
    }

    @Override
    @Transactional
    public InvoiceSubItemCommand findSubItemCommandById(Long l) {

        return invoiceSubItemToInvoiceSubItemCommand.convert(findById(l));

    }
}
