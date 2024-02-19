package com.example.btpsd.controllers;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;
import com.example.btpsd.repositories.UnitOfMeasurementRepository;
import com.example.btpsd.services.UnitOfMeasurementService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class UnitOfMeasurementController {

    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

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
    UnitOfMeasurementCommand newUomCommand(@RequestBody UnitOfMeasurementCommand newUomCommand) {

        UnitOfMeasurementCommand savedCommand = unitOfMeasurementService.saveUnitOfMeasurementCommand(newUomCommand);
        return savedCommand;

    }

    @DeleteMapping("/measurements/{unitOfMeasurementCode}")
    void deleteUomCommand(@PathVariable Long unitOfMeasurementCode) {
        unitOfMeasurementService.deleteById(unitOfMeasurementCode);
    }

    @PutMapping
    @RequestMapping("/measurements/{unitOfMeasurementCode}")
    @Transactional
    UnitOfMeasurementCommand updateUomCommand(@RequestBody UnitOfMeasurementCommand newUomCommand, @PathVariable Long unitOfMeasurementCode) {

        UnitOfMeasurementCommand command = unitOfMeasurementToUnitOfMeasurementCommand.convert(unitOfMeasurementService.updateUnitOfMeasurement(newUomCommand, unitOfMeasurementCode));
        return command;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/measurements/search")
    @ResponseBody
    public List<UnitOfMeasurement> Search(@RequestParam String keyword) {

        return unitOfMeasurementRepository.search(keyword);
    }
}
