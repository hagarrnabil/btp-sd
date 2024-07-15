package com.example.btpsd.controllers;

import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.converters.SubItemToSubItemCommand;
import com.example.btpsd.repositories.SubItemRepository;
import com.example.btpsd.services.SubItemService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class SubItemController {

    private final SubItemRepository subItemRepository;

    private final SubItemService subItemService;

    private final SubItemToSubItemCommand subItemToSubItemCommand;

    @GetMapping("/subitems")
    Set<SubItemCommand> all() {
        return subItemService.getSubItemCommands();
    }

    @GetMapping("/subitems/{subItemCode}")
    public Optional<SubItemCommand> findByIds(@PathVariable @NotNull Long subItemCode) {

        return Optional.ofNullable(subItemService.findSubItemCommandById(subItemCode));
    }

    @PostMapping("/subitems")
    SubItemCommand newSubItemCommand(@RequestBody SubItemCommand newSubItemCommand) {

        SubItemCommand savedCommand = subItemService.saveSubItemCommand(newSubItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/subitems/{subItemCode}")
    void deleteSubItemCommand(@PathVariable Long subItemCode) {
        subItemService.deleteById(subItemCode);
    }

    @PatchMapping
    @RequestMapping("/subitems/{subItemCode}")
    @Transactional
    SubItemCommand updateSubItemCommand(@RequestBody SubItemCommand newSubItemCommand, @PathVariable Long subItemCode) {

        SubItemCommand command = subItemToSubItemCommand.convert(subItemService.updateSubItem(newSubItemCommand, subItemCode));
        return command;
    }

}
