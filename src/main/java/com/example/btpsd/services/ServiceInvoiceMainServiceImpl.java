package com.example.btpsd.services;

import com.example.btpsd.commands.InvoiceMainItemCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ServiceInvoiceMain;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.LineTypeRepository;
import com.example.btpsd.repositories.ServiceInvoiceMainRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class ServiceInvoiceMainServiceImpl implements ServiceInvoiceMainService {

    private final ServiceInvoiceMainRepository serviceInvoiceMainRepository;
    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private ServiceInvoiceMainService serviceInvoiceMainService;
    private final ExecutionOrderMainService executionOrderMainService;
    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;
    private final LineTypeRepository lineTypeRepository;
    private final ServiceInvoiceCommandToServiceInvoice serviceInvoiceCommandToServiceInvoice;
    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

    @Override
    @Transactional
    public Set<ServiceInvoiceMainCommand> getServiceInvoiceMainCommands() {

        Double totalHeader = getTotalHeader();  // Call the method directly
        return StreamSupport.stream(serviceInvoiceMainRepository.findAll().spliterator(), false)
                .map(serviceInvoiceMain -> {
                    ServiceInvoiceMainCommand command = serviceInvoiceToServiceInvoiceCommand.convert(serviceInvoiceMain);
                    command.setTotalHeader(totalHeader);
                    return command;
                })
                .collect(Collectors.toSet());

    }

    public Double getTotalHeader() {
        List<ServiceInvoiceMain> allItems = (List<ServiceInvoiceMain>) serviceInvoiceMainRepository.findAll();
        Double totalHeader = 0.0;

        for (ServiceInvoiceMain item : allItems) {
            log.debug("Item ID: " + item.getServiceInvoiceCode() + ", total: " + item.getTotal());
            totalHeader += item.getTotal();
        }

        log.debug("Final Total Header: " + totalHeader);

        return totalHeader;
    }

    public ServiceInvoiceMainCommand getSrvMainItemWithTotalHeader(Long id) {
        ServiceInvoiceMain serviceInvoiceMain = serviceInvoiceMainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Double totalHeader = serviceInvoiceMainService.getTotalHeader();
        log.debug("Total Header set in command: " + totalHeader);

        ServiceInvoiceMainCommand command = serviceInvoiceToServiceInvoiceCommand.convert(serviceInvoiceMain);
        command.setTotalHeader(totalHeader);

        return command;
    }

    @Override
    public ServiceInvoiceMain findById(Long l) {

        Optional<ServiceInvoiceMain> serviceInvoiceMainOptional = serviceInvoiceMainRepository.findById(l);

        if (!serviceInvoiceMainOptional.isPresent()) {
            throw new RuntimeException("Service Invoice Main Not Found!");
        }

        return serviceInvoiceMainOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        serviceInvoiceMainRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand saveServiceInvoiceMainCommand(ServiceInvoiceMainCommand command) {
        // Convert the command to the ServiceInvoiceMain entity
        ServiceInvoiceMain newServiceInvoice = serviceInvoiceCommandToServiceInvoice.convert(command);

        // Handle logic based on existence of execution order
        if (newServiceInvoice.getExecutionOrderMain() == null) {
            newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
            newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
            newServiceInvoice.setActualPercentage(
                    (int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100)
            );
        } else {
            Optional<ServiceInvoiceMain> lastInvoiceOpt = serviceInvoiceMainRepository
                    .findTopByExecutionOrderMainOrderByServiceInvoiceCodeDesc(newServiceInvoice.getExecutionOrderMain());

            if (lastInvoiceOpt.isPresent()) {
                ServiceInvoiceMain lastInvoice = lastInvoiceOpt.get();
                if (!lastInvoice.getServiceInvoiceCode().equals(newServiceInvoice.getServiceInvoiceCode())) {
                    newServiceInvoice.setActualQuantity(lastInvoice.getActualQuantity() + newServiceInvoice.getQuantity());
                    newServiceInvoice.setRemainingQuantity(lastInvoice.getRemainingQuantity() - newServiceInvoice.getQuantity());
                } else {
                    newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
                    newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
                }
            } else {
                newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
                newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
            }
            newServiceInvoice.setActualPercentage(
                    (int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100)
            );
        }

        // Save the ServiceInvoiceMain entity
        ServiceInvoiceMain savedServiceInvoice = serviceInvoiceMainRepository.save(newServiceInvoice);

        // Recalculate totalHeader and update the saved item
        Double totalHeader = getTotalHeader();
        savedServiceInvoice.setTotalHeader(totalHeader);
        savedServiceInvoice = serviceInvoiceMainRepository.save(savedServiceInvoice);

        log.debug("Total Header after save: " + totalHeader);

        return serviceInvoiceToServiceInvoiceCommand.convert(savedServiceInvoice);
    }

    @Override
    @Transactional
    public ServiceInvoiceMain updateServiceInvoiceMain(ServiceInvoiceMain updatedInvoice, Long id) {
        // Fetch the existing ServiceInvoiceMain entity
        ServiceInvoiceMain existingInvoice = serviceInvoiceMainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceInvoiceMain not found with ID: " + id));

        // Update non-null fields in the existing entity
        updateNonNullFields(updatedInvoice, existingInvoice);

        // Handle execution order logic and recalculate AQ, AP, and RQ
        if (existingInvoice.getExecutionOrderMain() != null) {
            Optional<ServiceInvoiceMain> lastInvoiceOpt = serviceInvoiceMainRepository
                    .findTopByExecutionOrderMainOrderByServiceInvoiceCodeDesc(existingInvoice.getExecutionOrderMain());

            if (lastInvoiceOpt.isPresent()) {
                ServiceInvoiceMain lastInvoice = lastInvoiceOpt.get();
                if (lastInvoice.getServiceInvoiceCode().equals(existingInvoice.getServiceInvoiceCode())) {
                    Integer lastActualQuantity = lastInvoice.getActualQuantity() != null ? lastInvoice.getActualQuantity() : 0;
                    existingInvoice.setActualQuantity(lastActualQuantity + existingInvoice.getQuantity());

                    Integer lastRemainingQuantity = lastInvoice.getRemainingQuantity() != null ?
                            lastInvoice.getRemainingQuantity() : lastInvoice.getTotalQuantity();
                    existingInvoice.setRemainingQuantity(lastRemainingQuantity - existingInvoice.getQuantity());

                    existingInvoice.setActualPercentage((int) ((existingInvoice.getActualQuantity() /
                            (float) lastInvoice.getTotalQuantity()) * 100));
                } else {
                    existingInvoice.setActualQuantity(existingInvoice.getQuantity());
                    existingInvoice.setRemainingQuantity(existingInvoice.getTotalQuantity() - existingInvoice.getQuantity());
                    existingInvoice.setActualPercentage(
                            (int) ((existingInvoice.getActualQuantity() / (float) existingInvoice.getTotalQuantity()) * 100)
                    );
                }
            } else {
                existingInvoice.setActualQuantity(existingInvoice.getQuantity());
                existingInvoice.setRemainingQuantity(existingInvoice.getTotalQuantity() - existingInvoice.getQuantity());
                existingInvoice.setActualPercentage(
                        (int) ((existingInvoice.getActualQuantity() / (float) existingInvoice.getTotalQuantity()) * 100)
                );
            }
        }

        // Save the updated entity and return
        return serviceInvoiceMainRepository.save(existingInvoice);
    }

    @Override
    @Transactional
    public ServiceInvoiceMainCommand findServiceInvoiceMainCommandById(Long l) {

        return serviceInvoiceToServiceInvoiceCommand.convert(findById(l));
    }
}