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
public class FormulaCommandToFormula implements Converter<FormulaCommand, Formula> {

    private final ModelSpecDetailsCommandToModelSpecDetails modelSpecDetailsConverter;
    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");

    @Synchronized
    @Nullable
    @Override
    public Formula convert(FormulaCommand source) {

        if (source == null) {
            return null;
        }

        final Formula formula = new Formula();
        formula.setFormulaCode(source.getFormulaCode());
        formula.setFormula(source.getFormula());
        formula.setDescription(source.getDescription());
        formula.setNumberOfParameters(source.getNumberOfParameters());
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formula.setParameterId(source.getParameterId());
        }
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formula.setParameterDescription(source.getParameterId());
        }
        formula.setFormulaLogic(source.getFormulaLogic());
        formula.setInsertParameters(source.getInsertParameters());
        formula.setInsertModifiers(source.getInsertModifiers());
        formula.setEnterLength(source.getEnterLength());
        formula.setEnterWidth(source.getEnterWidth());
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formula.setExpression("" + source.getParameterId() + "=" + source.getEnterLength() + ";" +
                    source.getParameterId() + "=" + source.getEnterWidth() + ";" + source.getFormulaLogic() + ";" + "");
        }
        try {
            formula.setResult((Double) scriptEngine.eval(formula.getExpression()));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        formula.setShowResults(source.getShowResults());
        if (source.getModelSpecificationsDetailsCommands() != null && source.getModelSpecificationsDetailsCommands().size() > 0) {
            source.getModelSpecificationsDetailsCommands()
                    .forEach(modelSpecificationsDetailsCommand -> formula.getModelSpecificationsDetails().add(modelSpecDetailsConverter.convert(modelSpecificationsDetailsCommand)));
        }
        return formula;
    }
}
