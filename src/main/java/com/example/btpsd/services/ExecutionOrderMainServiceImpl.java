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
    @Transactional
    public ExecutionOrderMain updateExecutionOrderMain(ExecutionOrderMainCommand newExecutionOrderMainCommand, Long l) {

        return executionOrderMainRepository.findById(l).map(oldExecutionOrderMain -> {
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