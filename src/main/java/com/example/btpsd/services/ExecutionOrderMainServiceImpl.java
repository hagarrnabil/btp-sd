package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
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
public class ExecutionOrderMainServiceImpl implements ExecutionOrderMainService{

    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;
    private final ExecutionOrderMainCommandToExecutionOrderMain executionOrderMainCommandToExecutionOrderMain;

    @Override
    @Transactional
    public Set<ExecutionOrderMainCommand> getExecutionOrderMainCommands() {

        return StreamSupport.stream(executionOrderMainRepository.findAll()
                        .spliterator(), false)
                .map(executionOrderMainToExecutionOrderMainCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public ExecutionOrderMain findById(Long l) {

        Optional<ExecutionOrderMain> executionOrderMainOptional = executionOrderMainRepository.findById(l);

        if (!executionOrderMainOptional.isPresent()) {
            throw new RuntimeException("Execution Order Main Not Found!");
        }

        return executionOrderMainOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        executionOrderMainRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public ExecutionOrderMainCommand saveExecutionOrderMainCommand(ExecutionOrderMainCommand command) {

        ExecutionOrderMain detachedExecutionOrderMain = executionOrderMainCommandToExecutionOrderMain.convert(command);
        ExecutionOrderMain savedExecutionOrderMain = executionOrderMainRepository.save(detachedExecutionOrderMain);
        log.debug("Saved Execution Order Main Id:" + savedExecutionOrderMain.getExecutionOrderMainCode());
        return executionOrderMainToExecutionOrderMainCommand.convert(savedExecutionOrderMain);

    }

    @Override
    public ExecutionOrderMain updateExecutionOrderMain(ExecutionOrderMainCommand newExecutionOrderMainCommand, Long l) {

        return executionOrderMainRepository.findById(l).map(oldExecutionOrderMain -> {
            if (newExecutionOrderMainCommand.getCurrencyCode() != oldExecutionOrderMain.getCurrencyCode())
                oldExecutionOrderMain.setCurrencyCode(newExecutionOrderMainCommand.getCurrencyCode());
            if (newExecutionOrderMainCommand.getMaterialGroupCode() != oldExecutionOrderMain.getMaterialGroupCode())
                oldExecutionOrderMain.setMaterialGroupCode(newExecutionOrderMainCommand.getMaterialGroupCode());
            if (newExecutionOrderMainCommand.getLineTypeCode() != oldExecutionOrderMain.getLineTypeCode())
                oldExecutionOrderMain.setLineTypeCode(newExecutionOrderMainCommand.getLineTypeCode());
            if (newExecutionOrderMainCommand.getPersonnelNumberCode() != oldExecutionOrderMain.getPersonnelNumberCode())
                oldExecutionOrderMain.setPersonnelNumberCode(newExecutionOrderMainCommand.getPersonnelNumberCode());
            if (newExecutionOrderMainCommand.getUnitOfMeasurementCode() != oldExecutionOrderMain.getUnitOfMeasurementCode())
                oldExecutionOrderMain.setUnitOfMeasurementCode(newExecutionOrderMainCommand.getUnitOfMeasurementCode());
            if (newExecutionOrderMainCommand.getDescription() != oldExecutionOrderMain.getDescription())
                oldExecutionOrderMain.setDescription(newExecutionOrderMainCommand.getDescription());
            if (newExecutionOrderMainCommand.getTotalQuantity() != oldExecutionOrderMain.getTotalQuantity())
                oldExecutionOrderMain.setTotalQuantity(newExecutionOrderMainCommand.getTotalQuantity());
            if (newExecutionOrderMainCommand.getAmountPerUnit() != oldExecutionOrderMain.getAmountPerUnit())
                oldExecutionOrderMain.setAmountPerUnit(newExecutionOrderMainCommand.getAmountPerUnit());
            if (newExecutionOrderMainCommand.getTotal() != oldExecutionOrderMain.getTotal())
                oldExecutionOrderMain.setTotal(newExecutionOrderMainCommand.getTotal());
            if (newExecutionOrderMainCommand.getActualQuantity() != oldExecutionOrderMain.getActualQuantity())
                oldExecutionOrderMain.setActualQuantity(newExecutionOrderMainCommand.getActualQuantity());
            if (newExecutionOrderMainCommand.getActualPercentage() != oldExecutionOrderMain.getActualPercentage())
                oldExecutionOrderMain.setActualPercentage(newExecutionOrderMainCommand.getActualPercentage());
            if (newExecutionOrderMainCommand.getOverFulfillmentPercentage() != oldExecutionOrderMain.getOverFulfillmentPercentage())
                oldExecutionOrderMain.setOverFulfillmentPercentage(newExecutionOrderMainCommand.getOverFulfillmentPercentage());
            if (newExecutionOrderMainCommand.getUnlimitedOverFulfillment() != oldExecutionOrderMain.getUnlimitedOverFulfillment())
                oldExecutionOrderMain.setUnlimitedOverFulfillment(newExecutionOrderMainCommand.getUnlimitedOverFulfillment());
            if (newExecutionOrderMainCommand.getManualPriceEntryAllowed() != oldExecutionOrderMain.getManualPriceEntryAllowed())
                oldExecutionOrderMain.setManualPriceEntryAllowed(newExecutionOrderMainCommand.getManualPriceEntryAllowed());
            if (newExecutionOrderMainCommand.getExternalServiceNumber() != oldExecutionOrderMain.getExternalServiceNumber())
                oldExecutionOrderMain.setExternalServiceNumber(newExecutionOrderMainCommand.getExternalServiceNumber());
            if (newExecutionOrderMainCommand.getServiceText() != oldExecutionOrderMain.getServiceText())
                oldExecutionOrderMain.setServiceText(newExecutionOrderMainCommand.getServiceText());
            if (newExecutionOrderMainCommand.getLineText() != oldExecutionOrderMain.getLineText())
                oldExecutionOrderMain.setLineText(newExecutionOrderMainCommand.getLineText());
            if (newExecutionOrderMainCommand.getLineNumber() != oldExecutionOrderMain.getLineNumber())
                oldExecutionOrderMain.setLineNumber(newExecutionOrderMainCommand.getLineNumber());
            if (newExecutionOrderMainCommand.getBiddersLine() != oldExecutionOrderMain.getBiddersLine())
                oldExecutionOrderMain.setBiddersLine(newExecutionOrderMainCommand.getBiddersLine());
            if (newExecutionOrderMainCommand.getSupplementaryLine() != oldExecutionOrderMain.getSupplementaryLine())
                oldExecutionOrderMain.setSupplementaryLine(newExecutionOrderMainCommand.getSupplementaryLine());
            if (newExecutionOrderMainCommand.getLotCostOne() != oldExecutionOrderMain.getLotCostOne())
                oldExecutionOrderMain.setLotCostOne(newExecutionOrderMainCommand.getLotCostOne());
            if (newExecutionOrderMainCommand.getDoNotPrint() != oldExecutionOrderMain.getDoNotPrint())
                oldExecutionOrderMain.setDoNotPrint(newExecutionOrderMainCommand.getDoNotPrint());
            return executionOrderMainRepository.save(oldExecutionOrderMain);
        }).orElseThrow(() -> new RuntimeException("Execution Order Main not found"));

    }

    @Override
    @Transactional
    public ExecutionOrderMainCommand findExecutionOrderMainCommandById(Long l) {

        return executionOrderMainToExecutionOrderMainCommand.convert(findById(l));

    }
}
