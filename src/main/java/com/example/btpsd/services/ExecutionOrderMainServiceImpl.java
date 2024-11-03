package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainCommandToExecutionOrderMain;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.model.*;
import com.example.btpsd.repositories.ExecutionOrderMainRepository;
import com.example.btpsd.repositories.LineTypeRepository;
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
public class ExecutionOrderMainServiceImpl implements ExecutionOrderMainService {

    private final ExecutionOrderMainRepository executionOrderMainRepository;
    private final LineTypeRepository lineTypeRepository;
    private final ExecutionOrderMainToExecutionOrderMainCommand executionOrderMainToExecutionOrderMainCommand;
    private final ExecutionOrderMainCommandToExecutionOrderMain executionOrderMainCommandToExecutionOrderMain;


    @Override
    @Transactional
    public Set<ExecutionOrderMainCommand> getExecutionOrderMainCommands() {
        Double totalHeader = getTotalHeader();  // Call the method directly
        return StreamSupport.stream(executionOrderMainRepository.findAll().spliterator(), false)
                .map(invoiceMainItem -> {
                    ExecutionOrderMainCommand command = executionOrderMainToExecutionOrderMainCommand.convert(invoiceMainItem);
                    command.setTotalHeader(totalHeader);
                    return command;
                })
                .collect(Collectors.toSet());
    }

    public ExecutionOrderMainCommand getExcOrderWithTotalHeader(Long id) {
        ExecutionOrderMain executionOrderMain = executionOrderMainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Execution not found"));

        Double totalHeader = getTotalHeader();
        log.debug("Total Header set in command: " + totalHeader);

        ExecutionOrderMainCommand command = executionOrderMainToExecutionOrderMainCommand.convert(executionOrderMain);
        command.setTotalHeader(totalHeader);

        return command;
    }

    @Override
    public Double getTotalHeader() {
        List<ExecutionOrderMain> allItems = (List<ExecutionOrderMain>) executionOrderMainRepository.findAll();
        Double totalHeader = 0.0;

        for (ExecutionOrderMain item : allItems) {
            log.debug("Item ID: " + item.getExecutionOrderMainCode() + ", total: " + item.getTotal());
            totalHeader += item.getTotal();
        }

        log.debug("Final Total Header: " + totalHeader);

        return totalHeader;
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
        ExecutionOrderMain executionOrderMain = executionOrderMainCommandToExecutionOrderMain.convert(command);
        ExecutionOrderMain savedItem = executionOrderMainRepository.save(executionOrderMain);
        Double totalHeader = getTotalHeader();
        savedItem.setTotalHeader(totalHeader);
        savedItem = executionOrderMainRepository.save(savedItem);
        log.debug("Total Header after save: " + totalHeader);
        return executionOrderMainToExecutionOrderMainCommand.convert(savedItem);
    }

    @Override
    @Transactional
    public ExecutionOrderMain updateExecutionOrderMain(ExecutionOrderMainCommand newExecutionOrderMainCommand, Long id) {
        return executionOrderMainRepository.findById(id).map(oldExecutionOrderMain -> {
            updateNonNullFields(newExecutionOrderMainCommand, oldExecutionOrderMain);
            oldExecutionOrderMain.setLineTypeCode(lineTypeRepository.findLineTypeCodeByCode(newExecutionOrderMainCommand.getLineTypeCode()));
            return executionOrderMainRepository.save(oldExecutionOrderMain);
        }).orElseThrow(() -> new RuntimeException("Execution Order Main not found"));
    }

    @Override
    @Transactional
    public ExecutionOrderMainCommand findExecutionOrderMainCommandById(Long l) {

        return executionOrderMainToExecutionOrderMainCommand.convert(findById(l));

    }
}