package com.example.btpsd.services;

import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceCommandToServiceInvoice;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.model.ExecutionOrderMain;
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
    private final ExecutionOrderMainService executionOrderMainService;
    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;
    private final LineTypeRepository lineTypeRepository;
    private final ServiceInvoiceCommandToServiceInvoice serviceInvoiceCommandToServiceInvoice;
    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

    @Override
    @Transactional
    public Set<ServiceInvoiceMainCommand> getServiceInvoiceMainCommands() {

        return StreamSupport.stream(serviceInvoiceMainRepository.findAll()
                        .spliterator(), false)
                .map(serviceInvoiceToServiceInvoiceCommand::convert)
                .collect(Collectors.toSet());

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
            // Case 1: No execution order, initialize values
            newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
            newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
            newServiceInvoice.setActualPercentage(
                    (int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100)
            );
        } else {
            // Case 2 & 3: Execution order exists
            Optional<ServiceInvoiceMain> lastInvoiceOpt = serviceInvoiceMainRepository
                    .findTopByExecutionOrderMainOrderByServiceInvoiceCodeDesc(newServiceInvoice.getExecutionOrderMain());

            if (lastInvoiceOpt.isPresent()) {
                ServiceInvoiceMain lastInvoice = lastInvoiceOpt.get();

                // Check if it's a different service invoice (for the same execution order)
                if (!lastInvoice.getServiceInvoiceCode().equals(newServiceInvoice.getServiceInvoiceCode())) {
                    // Case 3: Different service invoice, accumulate values
                    newServiceInvoice.setActualQuantity(lastInvoice.getActualQuantity() + newServiceInvoice.getQuantity());
                    newServiceInvoice.setRemainingQuantity(lastInvoice.getRemainingQuantity() - newServiceInvoice.getQuantity());
                } else {
                    // Case 2: Same service invoice code, no accumulation (handle separately if needed)
                    newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
                    newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
                }
            } else {
                // No previous invoice, initialize values for new execution order
                newServiceInvoice.setActualQuantity(newServiceInvoice.getQuantity());
                newServiceInvoice.setRemainingQuantity(newServiceInvoice.getTotalQuantity() - newServiceInvoice.getQuantity());
            }
            newServiceInvoice.setActualPercentage(
                    (int) ((newServiceInvoice.getActualQuantity() / (float) newServiceInvoice.getTotalQuantity()) * 100)
            );
        }

        // Save the new ServiceInvoiceMain entity
        ServiceInvoiceMain savedServiceInvoice = serviceInvoiceMainRepository.save(newServiceInvoice);
        log.debug("Saved Service Invoice Main Id: " + savedServiceInvoice.getServiceInvoiceCode());

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