package com.example.btpsd.services;

import com.example.btpsd.commands.FormulaCommand;
import com.example.btpsd.converters.FormulaCommandToFormula;
import com.example.btpsd.converters.FormulaToFormulaCommand;
import com.example.btpsd.model.Formula;
import com.example.btpsd.model.UnitOfMeasurement;
import com.example.btpsd.repositories.FormulaRepository;
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
public class FormulaServiceImpl implements FormulaService{

    private final FormulaRepository formulaRepository;
    private final FormulaCommandToFormula formulaCommandToFormula;
    private final FormulaToFormulaCommand formulaToFormulaCommand;


    @Override
    @Transactional
    public Set<FormulaCommand> getFormulaCommands() {

        return StreamSupport.stream(formulaRepository.findAll()
                        .spliterator(), false)
                .map(formulaToFormulaCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public Formula findById(Long l) {

        Optional<Formula> formulaOptional = formulaRepository.findById(l);

        if (!formulaOptional.isPresent()) {
            throw new RuntimeException("Formula Not Found!");
        }

        return formulaOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        formulaRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public FormulaCommand saveFormulaCommand(FormulaCommand command) {

        Formula detachedFormula = formulaCommandToFormula.convert(command);
        Formula savedFormula = formulaRepository.save(detachedFormula);
        log.debug("Saved Formula Id:" + savedFormula.getFormulaCode());
        return formulaToFormulaCommand.convert(savedFormula);

    }

    @Override
    public Formula updateFormula(FormulaCommand newFormulaCommand, Long l) {

        return formulaRepository.findById(l).map(oldFormula -> {
            if (newFormulaCommand.getDescription() != oldFormula.getDescription())
                oldFormula.setDescription(newFormulaCommand.getDescription());
            if (newFormulaCommand.getParameterDescriptions() != oldFormula.getParameterDescriptions())
                oldFormula.setParameterDescriptions(newFormulaCommand.getParameterDescriptions());
            if (newFormulaCommand.getFormulaLogic() != oldFormula.getFormulaLogic())
                oldFormula.setFormulaLogic(newFormulaCommand.getFormulaLogic());
            if (newFormulaCommand.getTestParameters() != oldFormula.getTestParameters())
                oldFormula.setTestParameters(newFormulaCommand.getTestParameters());
            return formulaRepository.save(oldFormula);
        }).orElseThrow(() -> new RuntimeException("Formula not found"));

    }

    @Override
    @Transactional
    public FormulaCommand findFormulaCommandById(Long l) {

        return formulaToFormulaCommand.convert(findById(l));

    }
}