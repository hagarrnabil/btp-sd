package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.converters.ExecutionOrderSubCommandToExecutionOrderSub;
import com.example.btpsd.converters.ExecutionOrderSubToExecutionOrderSubCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ExecutionOrderSub;
import com.example.btpsd.repositories.ExecutionOrderSubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutionOrderSubServiceImpl implements ExecutionOrderSubService{

    private final ExecutionOrderSubRepository executionOrderSubRepository;
    private final ExecutionOrderSubCommandToExecutionOrderSub executionOrderSubCommandToExecutionOrderSub;
    private final ExecutionOrderSubToExecutionOrderSubCommand executionOrderSubToExecutionOrderSubCommand;


    @Override
    @Transactional
    public Set<ExecutionOrderSubCommand> getExecutionOrderSubCommands() {

        return StreamSupport.stream(executionOrderSubRepository.findAll()
                        .spliterator(), false)
                .map(executionOrderSubToExecutionOrderSubCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public ExecutionOrderSub findById(Long l) {

        Optional<ExecutionOrderSub> executionOrderSubOptional = executionOrderSubRepository.findById(l);

        if (!executionOrderSubOptional.isPresent()) {
            throw new RuntimeException("Execution Order Sub Not Found!");
        }

        return executionOrderSubOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        executionOrderSubRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public ExecutionOrderSubCommand saveExecutionOrderSubCommand(ExecutionOrderSubCommand command) {

        ExecutionOrderSub detachedExecutionOrderSub = executionOrderSubCommandToExecutionOrderSub.convert(command);
        ExecutionOrderSub savedExecutionOrderSub = executionOrderSubRepository.save(detachedExecutionOrderSub);
        log.debug("Saved Execution Order Sub Id:" + savedExecutionOrderSub.getInvoiceMainItemCode());
        return executionOrderSubToExecutionOrderSubCommand.convert(savedExecutionOrderSub);

    }

    @Override
    public ExecutionOrderSub updateExecutionOrderSub(ExecutionOrderSubCommand newExecutionOrderSubCommand, Long l) {

        return executionOrderSubRepository.findById(l).map(oldExecutionOrderSub -> {
            if (newExecutionOrderSubCommand.getCurrencyCode() != oldExecutionOrderSub.getCurrencyCode())
                oldExecutionOrderSub.setCurrencyCode(newExecutionOrderSubCommand.getCurrencyCode());
            if (newExecutionOrderSubCommand.getMaterialGroupCode() != oldExecutionOrderSub.getMaterialGroupCode())
                oldExecutionOrderSub.setMaterialGroupCode(newExecutionOrderSubCommand.getMaterialGroupCode());
            if (newExecutionOrderSubCommand.getLineTypeCode() != oldExecutionOrderSub.getLineTypeCode())
                oldExecutionOrderSub.setLineTypeCode(newExecutionOrderSubCommand.getLineTypeCode());
            if (newExecutionOrderSubCommand.getPersonnelNumberCode() != oldExecutionOrderSub.getPersonnelNumberCode())
                oldExecutionOrderSub.setPersonnelNumberCode(newExecutionOrderSubCommand.getPersonnelNumberCode());
            if (newExecutionOrderSubCommand.getUnitOfMeasurementCode() != oldExecutionOrderSub.getUnitOfMeasurementCode())
                oldExecutionOrderSub.setUnitOfMeasurementCode(newExecutionOrderSubCommand.getUnitOfMeasurementCode());
            if (newExecutionOrderSubCommand.getDescription() != oldExecutionOrderSub.getDescription())
                oldExecutionOrderSub.setDescription(newExecutionOrderSubCommand.getDescription());
            if (newExecutionOrderSubCommand.getTotalQuantity() != oldExecutionOrderSub.getTotalQuantity())
                oldExecutionOrderSub.setTotalQuantity(newExecutionOrderSubCommand.getTotalQuantity());
            if (newExecutionOrderSubCommand.getAmountPerUnit() != oldExecutionOrderSub.getAmountPerUnit())
                oldExecutionOrderSub.setAmountPerUnit(newExecutionOrderSubCommand.getAmountPerUnit());
            if (newExecutionOrderSubCommand.getTotal() != oldExecutionOrderSub.getTotal())
                oldExecutionOrderSub.setTotal(newExecutionOrderSubCommand.getTotal());
            if (newExecutionOrderSubCommand.getExternalServiceNumber() != oldExecutionOrderSub.getExternalServiceNumber())
                oldExecutionOrderSub.setExternalServiceNumber(newExecutionOrderSubCommand.getExternalServiceNumber());
            if (newExecutionOrderSubCommand.getServiceText() != oldExecutionOrderSub.getServiceText())
                oldExecutionOrderSub.setServiceText(newExecutionOrderSubCommand.getServiceText());
            if (newExecutionOrderSubCommand.getLineText() != oldExecutionOrderSub.getLineText())
                oldExecutionOrderSub.setLineText(newExecutionOrderSubCommand.getLineText());
            if (newExecutionOrderSubCommand.getLineNumber() != oldExecutionOrderSub.getLineNumber())
                oldExecutionOrderSub.setLineNumber(newExecutionOrderSubCommand.getLineNumber());
            if (newExecutionOrderSubCommand.getBiddersLine() != oldExecutionOrderSub.getBiddersLine())
                oldExecutionOrderSub.setBiddersLine(newExecutionOrderSubCommand.getBiddersLine());
            if (newExecutionOrderSubCommand.getSupplementaryLine() != oldExecutionOrderSub.getSupplementaryLine())
                oldExecutionOrderSub.setSupplementaryLine(newExecutionOrderSubCommand.getSupplementaryLine());
            if (newExecutionOrderSubCommand.getLotCostOne() != oldExecutionOrderSub.getLotCostOne())
                oldExecutionOrderSub.setLotCostOne(newExecutionOrderSubCommand.getLotCostOne());
            if (newExecutionOrderSubCommand.getDoNotPrint() != oldExecutionOrderSub.getDoNotPrint())
                oldExecutionOrderSub.setDoNotPrint(newExecutionOrderSubCommand.getDoNotPrint());
            return executionOrderSubRepository.save(oldExecutionOrderSub);
        }).orElseThrow(() -> new RuntimeException("Execution Order Sub not found"));

    }

    @Override
    @Transactional
    public ExecutionOrderSubCommand findExecutionOrderSubCommandById(Long l) {

        return executionOrderSubToExecutionOrderSubCommand.convert(findById(l));


    }
}
