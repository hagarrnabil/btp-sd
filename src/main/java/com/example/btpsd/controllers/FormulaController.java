package com.example.btpsd.controllers;

import com.example.btpsd.commands.FormulaCommand;
import com.example.btpsd.converters.FormulaToFormulaCommand;
import com.example.btpsd.services.FormulaService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class FormulaController {

    private final FormulaService formulaService;

    private final FormulaToFormulaCommand formulaToFormulaCommand;

    @GetMapping("/formulas")
    Set<FormulaCommand> all() {
        return formulaService.getFormulaCommands();
    }

    @GetMapping("/formulas/{formulaCode}")
    public Optional<FormulaCommand> findByIds(@PathVariable @NotNull Long formulaCode) {

        return Optional.ofNullable(formulaService.findFormulaCommandById(formulaCode));
    }

    @PostMapping("/formulas")
    FormulaCommand newFormulaCommand(@RequestBody FormulaCommand newFormulaCommand) {

        FormulaCommand savedCommand = formulaService.saveFormulaCommand(newFormulaCommand);
        return savedCommand;

    }

    @DeleteMapping("/formulas/{formulaCode}")
    void deleteFormulaCommand(@PathVariable Long formulaCode) {
        formulaService.deleteById(formulaCode);
    }

    @PutMapping
    @RequestMapping("/formulas/{formulaCode}")
    @Transactional
    FormulaCommand updateFormulaCommand(@RequestBody FormulaCommand newFormulaCommand, @PathVariable Long formulaCode) {

        FormulaCommand command = formulaToFormulaCommand.convert(formulaService.updateFormula(newFormulaCommand, formulaCode));
        return command;
    }
}
