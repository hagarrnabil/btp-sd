package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.commands.InvoiceSubItemCommand;
import com.example.btpsd.converters.InvoiceMainItemCommandToInvoiceMainItem;
import com.example.btpsd.converters.InvoiceMainItemToInvoiceMainItemCommand;
import com.example.btpsd.converters.InvoiceSubItemCommandToInvoiceSubItem;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.InvoiceSubItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.InvoiceMainItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvoiceMainItemServiceImpl implements InvoiceMainItemService {

    private final InvoiceMainItemRepository invoiceMainItemRepository;
    private InvoiceMainItemService invoiceMainItemService;
    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private final InvoiceMainItemCommandToInvoiceMainItem invoiceMainItemCommandToInvoiceMainItem;
    private final InvoiceMainItemToInvoiceMainItemCommand invoiceMainItemToInvoiceMainItemCommand;
    private final InvoiceSubItemCommandToInvoiceSubItem subItemConverter;


    @Override
    @Transactional
    public Set<InvoiceMainItemCommand> getMainItemCommands() {
        Double totalHeader = getTotalHeader();
        if (totalHeader == null) {
            totalHeader = 0.0;
        }
        Double finalTotalHeader = totalHeader;
        return StreamSupport.stream(invoiceMainItemRepository.findAll().spliterator(), false)
                .map(invoiceMainItem -> {
                    InvoiceMainItemCommand command = invoiceMainItemToInvoiceMainItemCommand.convert(invoiceMainItem);
                    command.setTotalHeader(finalTotalHeader);
                    return command;
                })
                .collect(Collectors.toSet());
    }


    public InvoiceMainItemCommand getInvoiceMainItemWithTotalHeader(Long id) {
        InvoiceMainItem mainItem = invoiceMainItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Double totalHeader = invoiceMainItemService.getTotalHeader();
        log.debug("Total Header set in command: " + totalHeader);

        InvoiceMainItemCommand command = invoiceMainItemToInvoiceMainItemCommand.convert(mainItem);
        command.setTotalHeader(totalHeader);

        return command;
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

//    private boolean hasRole(String role) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth != null && auth.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
//    }

    public Double getTotalHeader() {
        List<InvoiceMainItem> allItems = (List<InvoiceMainItem>) invoiceMainItemRepository.findAll();
        Double totalHeader = 0.0;

        for (InvoiceMainItem item : allItems) {
            Double profit = item.getTotalWithProfit();
            if (profit != null) {  // Only add non-null values
                log.debug("Item ID: " + item.getInvoiceMainItemCode() + ", totalWithProfit: " + profit);
                totalHeader += profit;
            } else {
                log.warn("Item ID: " + item.getInvoiceMainItemCode() + " has null totalWithProfit, excluding from totalHeader calculation.");
            }
        }

        log.debug("Final Total Header: " + totalHeader);
        return totalHeader;
    }


    @Override
    @Transactional
    public InvoiceMainItemCommand saveMainItemCommand(InvoiceMainItemCommand command) {

        // Convert command to entity
        InvoiceMainItem invoiceMainItem = invoiceMainItemCommandToInvoiceMainItem.convert(command);

        // Save the new invoice item to the repository (initial save)
        InvoiceMainItem savedItem = invoiceMainItemRepository.save(invoiceMainItem);

        // Calculate the totalHeader after the item is saved
        Double totalHeader = getTotalHeader();
        savedItem.setTotalHeader(totalHeader); // Update the saved item with the new totalHeader

        log.debug("Total Header after initial save: " + totalHeader);

        // Update the totalHeader in the saved item with a single save operation
        invoiceMainItemRepository.save(savedItem);

        // Convert back to command for return
        return invoiceMainItemToInvoiceMainItemCommand.convert(savedItem);
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
