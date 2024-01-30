package com.example.btpsd.converters;

import com.example.btpsd.commands.FormulaCommand;
import com.example.btpsd.model.Formula;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@RequiredArgsConstructor
@Component
public class FormulaToFormulaCommand implements Converter<Formula, FormulaCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");

    @Synchronized
    @Nullable
    @Override
    public FormulaCommand convert(Formula source) {

        if (source == null) {
            return null;
        }

        final FormulaCommand formulaCommand = new FormulaCommand();
        formulaCommand.setFormulaCode(source.getFormulaCode());
        formulaCommand.setFormula(source.getFormula());
        formulaCommand.setDescription(source.getDescription());
        formulaCommand.setNumberOfParameters(source.getNumberOfParameters());
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formulaCommand.setParameterId(source.getParameterId());
        }
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formulaCommand.setParameterDescription(source.getParameterDescription());
        }
        formulaCommand.setFormulaLogic(source.getFormulaLogic());
        formulaCommand.setInsertParameters(source.getInsertParameters());
        formulaCommand.setInsertModifiers(source.getInsertModifiers());
        formulaCommand.setEnterLength(source.getEnterLength());
        formulaCommand.setEnterWidth(source.getEnterWidth());
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formulaCommand.setExpression("" + source.getParameterId() + "=" + source.getEnterLength() + ";" +
                    source.getParameterId() + "=" + source.getEnterWidth() + ";" + source.getFormulaLogic() + ";" + "");
        }
        try {
            formulaCommand.setResult((Double) scriptEngine.eval(formulaCommand.getExpression()));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        formulaCommand.setShowResults(source.getShowResults());
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0){
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> formulaCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        return formulaCommand;
    }
}
