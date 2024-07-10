package com.example.btpsd.services;

import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.converters.SubItemCommandToSubItem;
import com.example.btpsd.converters.SubItemToSubItemCommand;
import com.example.btpsd.model.Invoice;
import com.example.btpsd.model.MainItem;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.model.SubItem;
import com.example.btpsd.repositories.SubItemRepository;
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
public class SubItemServiceImpl implements SubItemService {

    private final SubItemRepository subItemRepository;
    private final SubItemCommandToSubItem subItemCommandToSubItem;
    private final SubItemToSubItemCommand subItemToSubItemCommand;

    @Override
    @Transactional
    public Set<SubItemCommand> getSubItemCommands() {

        return StreamSupport.stream(subItemRepository.findAll()
                        .spliterator(), false)
                .map(subItemToSubItemCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public SubItem findById(Long l) {

        Optional<SubItem> subItemOptional = subItemRepository.findById(l);

        if (!subItemOptional.isPresent()) {
            throw new RuntimeException("Sub Item Not Found!");
        }

        return subItemOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        subItemRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public SubItemCommand saveSubItemCommand(SubItemCommand command) {

        SubItem detachedSubItem = subItemCommandToSubItem.convert(command);
        SubItem savedSubItem = subItemRepository.save(detachedSubItem);
        log.debug("Saved SubItem Id:" + savedSubItem.getSubItemCode());
        return subItemToSubItemCommand.convert(savedSubItem);

    }

    @Override
    public SubItem updateSubItem(SubItemCommand newSubItemCommand, Long l) {

        return subItemRepository.findById(l).map(oldSubItem -> {
            if (newSubItemCommand.getCurrencyCode() != oldSubItem.getCurrencyCode())
                oldSubItem.setCurrencyCode(newSubItemCommand.getCurrencyCode());
            if (newSubItemCommand.getFormulaCode() != oldSubItem.getFormulaCode())
                oldSubItem.setFormulaCode(newSubItemCommand.getFormulaCode());
            if (newSubItemCommand.getUnitOfMeasurementCode() != oldSubItem.getUnitOfMeasurementCode())
                oldSubItem.setUnitOfMeasurementCode(newSubItemCommand.getUnitOfMeasurementCode());
            if (newSubItemCommand.getAmountPerUnit() != oldSubItem.getAmountPerUnit())
                oldSubItem.setAmountPerUnit(newSubItemCommand.getAmountPerUnit());
            if (newSubItemCommand.getQuantity() != oldSubItem.getQuantity())
                oldSubItem.setQuantity(newSubItemCommand.getQuantity());
            if (newSubItemCommand.getTotal() != oldSubItem.getTotal())
                oldSubItem.setTotal(newSubItemCommand.getTotal());
            if (newSubItemCommand.getServiceNumberCode() != null) {
                ServiceNumber serviceNumber = new ServiceNumber();
                serviceNumber.setServiceNumberCode(newSubItemCommand.getServiceNumberCode());
                oldSubItem.setServiceNumber(serviceNumber);
                serviceNumber.addSubItem(oldSubItem);
            }
            return subItemRepository.save(oldSubItem);
        }).orElseThrow(() -> new RuntimeException("Sub Item not found"));
    }

    @Override
    @Transactional
    public SubItemCommand findSubItemCommandById(Long l) {

        return subItemToSubItemCommand.convert(findById(l));

    }
}
