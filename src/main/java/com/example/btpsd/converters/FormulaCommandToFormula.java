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

    private final ServiceNumberCommandToServiceNumber serviceNumberConverter;

//    ScriptEngine engine = GraalJSScriptEngine.create(null,
//            Context.newBuilder("js")
//                    .allowHostAccess(HostAccess.ALL)
//                    .allowHostClassLookup(s -> true)
//                    .option("js.ecmascript-version", "2022"));

    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");

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
        for (int i = 0; i < source.getParameterIds().size(); i++) {
            formula.setParameterIds(source.getParameterIds());
        }
        for (int i = 0; i < source.getParameterDescriptions().size(); i++) {
            formula.setParameterDescriptions(source.getParameterDescriptions());
        }
        formula.setFormulaLogic(source.getFormulaLogic());
        for (int i = 0; i < source.getTestParameters().size(); i++) {
            formula.setTestParameters(source.getTestParameters());
        }
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formula.setExpression("" + source.getParameterIds() + "=" + source.getTestParameters() + ";" + source.getFormulaLogic() + ";" + "");
        }
        try {
            formula.setResult(Double.parseDouble(engine.eval(formula.getExpression()).toString()));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        if (source.getModelSpecificationsDetailsCommands() != null && source.getModelSpecificationsDetailsCommands().size() > 0) {
            source.getModelSpecificationsDetailsCommands()
                    .forEach(modelSpecificationsDetailsCommand -> formula.getModelSpecificationsDetails().add(modelSpecDetailsConverter.convert(modelSpecificationsDetailsCommand)));
        }
        if (source.getServiceNumberCommands() != null && source.getServiceNumberCommands().size() > 0) {
            source.getServiceNumberCommands()
                    .forEach(serviceNumberCommand -> formula.getServiceNumbers().add(serviceNumberConverter.convert(serviceNumberCommand)));
        }
        return formula;
    }
}