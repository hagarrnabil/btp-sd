package com.example.btpsd.services;

import com.example.btpsd.commands.ExecutionOrderSubCommand;
import com.example.btpsd.converters.ExecutionOrderSubCommandToExecutionOrderSub;
import com.example.btpsd.converters.ExecutionOrderSubToExecutionOrderSubCommand;
import com.example.btpsd.model.ExecutionOrderMain;
import com.example.btpsd.model.ExecutionOrderSub;
import com.example.btpsd.model.InvoiceMainItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.repositories.ExecutionOrderSubRepository;
import com.example.btpsd.repositories.LineTypeRepository;
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
    private final LineTypeRepository lineTypeRepository;
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
        log.debug("Saved Execution Order Sub Id:" + savedExecutionOrderSub.getExecutionOrderSubCode());
        return executionOrderSubToExecutionOrderSubCommand.convert(savedExecutionOrderSub);

    }

    @Override
    public ExecutionOrderSub updateExecutionOrderSub(ExecutionOrderSubCommand newExecutionOrderSubCommand, Long l) {

        return executionOrderSubRepository.findById(l).map(oldExecutionOrderMain -> {
            updateSubNonNullFiels(newExecutionOrderSubCommand, oldExecutionOrderMain);
            oldExecutionOrderMain.setLineTypeCode(lineTypeRepository.findLineTypeCodeByCode(newExecutionOrderSubCommand.getLineTypeCode()));
            return executionOrderSubRepository.save(oldExecutionOrderMain);
        }).orElseThrow(() -> new RuntimeException("Execution Order Main not found"));


    }

    @Override
    @Transactional
    public ExecutionOrderSubCommand findExecutionOrderSubCommandById(Long l) {

        return executionOrderSubToExecutionOrderSubCommand.convert(findById(l));


    }
}
