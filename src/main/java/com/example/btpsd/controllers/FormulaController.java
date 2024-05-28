package com.example.btpsd.controllers;

import com.example.btpsd.commands.FormulaCommand;
import com.example.btpsd.converters.FormulaToFormulaCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.Formula;
import com.example.btpsd.repositories.FormulaRepository;
import com.example.btpsd.services.FormulaService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class FormulaController {

    private final FormulaRepository formulaRepository;

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

    @RequestMapping(method = RequestMethod.GET, value = "/formulas/search")
    @ResponseBody
    public List<Formula> Search(@RequestParam String keyword) {

        return formulaRepository.search(keyword);
    }
}
