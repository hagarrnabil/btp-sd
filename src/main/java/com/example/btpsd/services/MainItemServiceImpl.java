package com.example.btpsd.services;

import com.example.btpsd.commands.MainItemCommand;
import com.example.btpsd.converters.MainItemCommandToMainItem;
import com.example.btpsd.converters.MainItemToMainItemCommand;
import com.example.btpsd.model.Invoice;
import com.example.btpsd.model.MainItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.model.SubItem;
import com.example.btpsd.repositories.MainItemRepository;
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
public class MainItemServiceImpl implements MainItemService{

    private final MainItemRepository mainItemRepository;
    private final MainItemCommandToMainItem mainItemCommandToMainItem;
    private final MainItemToMainItemCommand mainItemToMainItemCommand;


    @Override
    @Transactional
    public Set<MainItemCommand> getMainItemCommands() {

        return StreamSupport.stream(mainItemRepository.findAll()
                        .spliterator(), false)
                .map(mainItemToMainItemCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public MainItem findById(Long l) {

        Optional<MainItem> mainItemOptional = mainItemRepository.findById(l);

        if (!mainItemOptional.isPresent()) {
            throw new RuntimeException("Main Item Not Found!");
        }

        return mainItemOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        mainItemRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public MainItemCommand saveMainItemCommand(MainItemCommand command) {

        MainItem detachedMainItem = mainItemCommandToMainItem.convert(command);
        MainItem savedMainItem = mainItemRepository.save(detachedMainItem);
        log.debug("Saved MainItem Id:" + savedMainItem.getMainItemCode());
        return mainItemToMainItemCommand.convert(savedMainItem);


    }

    @Override
    public MainItem updateMainItem(MainItemCommand newMainItemCommand, Long l) {

        return mainItemRepository.findById(l).map(oldMainItem -> {
            if (newMainItemCommand.getCurrencyCode() != oldMainItem.getCurrencyCode())
                oldMainItem.setCurrencyCode(newMainItemCommand.getCurrencyCode());
            if (newMainItemCommand.getFormulaCode() != oldMainItem.getFormulaCode())
                oldMainItem.setFormulaCode(newMainItemCommand.getFormulaCode());
            if (newMainItemCommand.getUnitOfMeasurementCode() != oldMainItem.getUnitOfMeasurementCode())
                oldMainItem.setUnitOfMeasurementCode(newMainItemCommand.getUnitOfMeasurementCode());
            if (newMainItemCommand.getAmountPerUnit() != oldMainItem.getAmountPerUnit())
                oldMainItem.setAmountPerUnit(newMainItemCommand.getAmountPerUnit());
            if (newMainItemCommand.getQuantity() != oldMainItem.getQuantity())
                oldMainItem.setQuantity(newMainItemCommand.getQuantity());
            if (newMainItemCommand.getProfitMargin() != oldMainItem.getProfitMargin())
                oldMainItem.setProfitMargin(newMainItemCommand.getProfitMargin());
            if (newMainItemCommand.getTotal() != oldMainItem.getTotal())
                oldMainItem.setTotal(newMainItemCommand.getTotal());
            if (newMainItemCommand.getServiceNumberCode() != null) {
                ServiceNumber serviceNumber = new ServiceNumber();
                serviceNumber.setServiceNumberCode(newMainItemCommand.getServiceNumberCode());
                oldMainItem.setServiceNumber(serviceNumber);
                serviceNumber.addMainItem(oldMainItem);
            }
            return mainItemRepository.save(oldMainItem);
        }).orElseThrow(() -> new RuntimeException("Main Item not found"));
    }

    @Override
    @Transactional
    public MainItemCommand findMainItemCommandById(Long l) {

        return mainItemToMainItemCommand.convert(findById(l));
    }
}
