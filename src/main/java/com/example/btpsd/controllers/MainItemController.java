package com.example.btpsd.controllers;

import com.example.btpsd.commands.MainItemCommand;
import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.converters.MainItemToMainItemCommand;
import com.example.btpsd.repositories.MainItemRepository;
import com.example.btpsd.services.MainItemService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MainItemController {

    private final MainItemRepository mainItemRepository;

    private final MainItemService mainItemService;

    private final MainItemToMainItemCommand mainItemToMainItemCommand;

    @GetMapping("/mainitems")
    Set<MainItemCommand> all() {
        return mainItemService.getMainItemCommands();
    }

    @GetMapping("/mainitems/{mainItemCode}")
    public Optional<MainItemCommand> findByIds(@PathVariable @NotNull Long mainItemCode) {

        return Optional.ofNullable(mainItemService.findMainItemCommandById(mainItemCode));
    }

    @PostMapping("/mainitems")
    MainItemCommand newMainItemCommand(@RequestBody MainItemCommand newMainItemCommand) {

        MainItemCommand savedCommand = mainItemService.saveMainItemCommand(newMainItemCommand);
        return savedCommand;

    }

    @DeleteMapping("/mainitems/{mainItemCode}")
    void deleteMainItemCommand(@PathVariable Long mainItemCode) {
        mainItemService.deleteById(mainItemCode);
    }

    @PatchMapping
    @RequestMapping("/mainitems/{mainItemCode}")
    @Transactional
    MainItemCommand updateMainItemCommand(@RequestBody MainItemCommand newMainItemCommand, @PathVariable Long mainItemCode) {

        MainItemCommand command = mainItemToMainItemCommand.convert(mainItemService.updateMainItem(newMainItemCommand, mainItemCode));
        return command;
    }

}
