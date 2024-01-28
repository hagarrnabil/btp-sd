package com.example.btpsd.controllers;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.example.btpsd.services.UnitOfMeasurementService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
@RequiredArgsConstructor
@RestController
public class UnitOfMeasurementController {

    private final UnitOfMeasurementService unitOfMeasurementService;

    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;

    @GetMapping("/measurements")
    Set<UnitOfMeasurementCommand> all() {
        return unitOfMeasurementService.getUnitOfMeasurementCommands();
    }

    @GetMapping("/measurements/{unitOfMeasurementCode}")
    public Optional<UnitOfMeasurementCommand> findByIds(@PathVariable @NotNull Long unitOfMeasurementCode) {

        return Optional.ofNullable(unitOfMeasurementService.findUnitOfMeasurementCommandById(unitOfMeasurementCode));
    }

    @PostMapping("/measurements")
    UnitOfMeasurementCommand newUOMCommand(@RequestBody UnitOfMeasurementCommand newUOMCommand) {

        UnitOfMeasurementCommand savedCommand = unitOfMeasurementService.saveUnitOfMeasurementCommand(newUOMCommand);
        return savedCommand;

    }

    @DeleteMapping("/measurements/{unitOfMeasurementCode}")
    void deleteUOMCommand(@PathVariable Long unitOfMeasurementCode) {
        unitOfMeasurementService.deleteById(unitOfMeasurementCode);
    }

    @PutMapping
    @RequestMapping("/measurements/{unitOfMeasurementCode}")
    @Transactional
    UnitOfMeasurementCommand updateUOMCommand(@RequestBody UnitOfMeasurementCommand newUOMCommand, @PathVariable Long unitOfMeasurementCode) {

        UnitOfMeasurementCommand command = unitOfMeasurementToUnitOfMeasurementCommand.convert(unitOfMeasurementService.updateUnitOfMeasurement(newUOMCommand, unitOfMeasurementCode));
        return command;
    }
}
