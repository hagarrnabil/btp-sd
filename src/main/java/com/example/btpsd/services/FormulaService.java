package com.example.btpsd.services;

import com.example.btpsd.commands.FormulaCommand;
import com.example.btpsd.model.Formula;

import java.util.Set;

public interface FormulaService {

    Set<FormulaCommand> getFormulaCommands();

    Formula findById(Long l);

    void deleteById(Long idToDelete);

    FormulaCommand saveFormulaCommand(FormulaCommand command);

    Formula updateFormula(FormulaCommand newFormulaCommand, Long l);

    FormulaCommand findFormulaCommandById(Long l);

}
