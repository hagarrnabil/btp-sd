package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.converters.InvoiceMainItemCommandToInvoiceMainItem;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.converters.InvoiceSubItemCommandToInvoiceSubItem;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.InvoiceSubItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvoiceMainItemServiceImpl implements InvoiceMainItemService {

    private final InvoiceMainItemRepository invoiceMainItemRepository;
    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private final InvoiceMainItemCommandToInvoiceMainItem invoiceMainItemCommandToInvoiceMainItem;
    private final InvoiceMainItemToInvoiceMainItemCommand invoiceMainItemToInvoiceMainItemCommand;
    private final InvoiceSubItemCommandToInvoiceSubItem subItemConverter;


    @Override
    @Transactional
    public Set<InvoiceMainItemCommand> getMainItemCommands() {

        return StreamSupport.stream(invoiceMainItemRepository.findAll()
                        .spliterator(), false)
                .map(invoiceMainItemToInvoiceMainItemCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public InvoiceMainItem findById(Long l) {

        Optional<InvoiceMainItem> mainItemOptional = invoiceMainItemRepository.findById(l);

        if (!mainItemOptional.isPresent()) {
            throw new RuntimeException("Main Item Not Found!");
        }

        return mainItemOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        invoiceMainItemRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public InvoiceMainItemCommand saveMainItemCommand(InvoiceMainItemCommand command) {

        // Convert command to entity
        InvoiceMainItem detachedMainItem = invoiceMainItemCommandToInvoiceMainItem.convert(command);

        if (detachedMainItem != null) {
            // Save entity to the database
            InvoiceMainItem savedMainItem = invoiceMainItemRepository.save(detachedMainItem);
            // Convert the saved entity back to command and return
            return invoiceMainItemToInvoiceMainItemCommand.convert(savedMainItem);
        } else {
            // Handle conversion error (return null or throw an exception)
            return null;
        }
    }

    @Override
    @Transactional
    public InvoiceMainItem updateMainItem(InvoiceMainItemCommand newInvoiceMainItemCommand, Long l) {


        return invoiceMainItemRepository.findById(l).map(oldMainItem -> {
            if (newInvoiceMainItemCommand.getCurrencyCode() != oldMainItem.getCurrencyCode())
                oldMainItem.setCurrencyCode(newInvoiceMainItemCommand.getCurrencyCode());
            if (newInvoiceMainItemCommand.getFormulaCode() != oldMainItem.getFormulaCode())
                oldMainItem.setFormulaCode(newInvoiceMainItemCommand.getFormulaCode());
            if (newInvoiceMainItemCommand.getUnitOfMeasurementCode() != oldMainItem.getUnitOfMeasurementCode())
                oldMainItem.setUnitOfMeasurementCode(newInvoiceMainItemCommand.getUnitOfMeasurementCode());
            if (newInvoiceMainItemCommand.getQuantity() != oldMainItem.getQuantity())
                oldMainItem.setQuantity(newInvoiceMainItemCommand.getQuantity());
            if (newInvoiceMainItemCommand.getDescription() != oldMainItem.getDescription())
                oldMainItem.setDescription(newInvoiceMainItemCommand.getDescription());
            if (newInvoiceMainItemCommand.getProfitMargin() != oldMainItem.getProfitMargin())
                oldMainItem.setProfitMargin(newInvoiceMainItemCommand.getProfitMargin());
            if (newInvoiceMainItemCommand.getTotal() != oldMainItem.getTotal())
                oldMainItem.setTotal(newInvoiceMainItemCommand.getTotal());

            if (newInvoiceMainItemCommand.getSubItems() != null && !newInvoiceMainItemCommand.getSubItems().isEmpty()) {

                double totalFromSubItems = 0.0;

                oldMainItem.getSubItemList().clear();

                for (InvoiceSubItemCommand subItemCommand : newInvoiceMainItemCommand.getSubItems()) {
                    InvoiceSubItem subItem = subItemConverter.convert(subItemCommand);
                    if (subItem != null) {
                        totalFromSubItems += subItem.getTotal(); // Sum the total of each sub-item
                        oldMainItem.addSubItem(subItem);
                    }
                }

                // Set amountPerUnit to the total from sub-items divided by the quantity
                oldMainItem.setAmountPerUnit(totalFromSubItems);
            } else {
                // Use the manually entered amountPerUnit if no subItems are present
                oldMainItem.setAmountPerUnit(newInvoiceMainItemCommand.getAmountPerUnit());
            }

            if (newInvoiceMainItemCommand.getServiceNumberCode() != null) {
                ServiceNumber serviceNumber = new ServiceNumber();
                serviceNumber.setServiceNumberCode(newInvoiceMainItemCommand.getServiceNumberCode());
                oldMainItem.setServiceNumber(serviceNumber);
                serviceNumber.addMainItem(oldMainItem);

            }

            oldMainItem.setTotal(oldMainItem.getQuantity() * oldMainItem.getAmountPerUnit());

            if(oldMainItem.getProfitMargin() != null){
                oldMainItem.setTotalWithProfit(((oldMainItem.getProfitMargin() / 100) * oldMainItem.getTotal()) + oldMainItem.getTotal());
                oldMainItem.setAmountPerUnitWithProfit(((oldMainItem.getProfitMargin() / 100) * oldMainItem.getAmountPerUnit()) + oldMainItem.getAmountPerUnit());
                oldMainItem.setTotalWithProfit(new BigDecimal(oldMainItem.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                oldMainItem.setAmountPerUnitWithProfit(new BigDecimal(oldMainItem.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
            }
            else {
                oldMainItem.setTotalWithProfit(((0 / 100) * oldMainItem.getTotal()) + oldMainItem.getTotal());
                oldMainItem.setAmountPerUnitWithProfit(((0 / 100) * oldMainItem.getAmountPerUnit()) + oldMainItem.getAmountPerUnit());
                oldMainItem.setTotalWithProfit(new BigDecimal(oldMainItem.getTotalWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                oldMainItem.setAmountPerUnitWithProfit(new BigDecimal(oldMainItem.getAmountPerUnitWithProfit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
            }


            // Update the corresponding ExecutionOrderMain
            if (oldMainItem.getExecutionOrderMain() != null) {
                oldMainItem.getExecutionOrderMain().updateFromInvoiceMainItem(oldMainItem);
            }

            return invoiceMainItemRepository.save(oldMainItem);
        }).orElseThrow(() -> new RuntimeException("Main Item not found"));
    }

    @Override
    @Transactional
    public InvoiceMainItemCommand findMainItemCommandById(Long l) {

        return invoiceMainItemToInvoiceMainItemCommand.convert(findById(l));
    }
}
