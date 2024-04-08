package com.example.btpsd.converters;

import com.example.btpsd.commands.FormulaCommand;
import com.example.btpsd.model.Formula;
import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@RequiredArgsConstructor
@Component
public class FormulaToFormulaCommand implements Converter<Formula, FormulaCommand> {

    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsConverter;

    private final ServiceNumberToServiceNumberCommand serviceNumberConverter;

    ScriptEngine engine = GraalJSScriptEngine.create(null,
            Context.newBuilder("js")
                    .allowHostAccess(HostAccess.ALL)
                    .allowHostClassLookup(s -> true)
                    .option("js.ecmascript-version", "2022"));

//    ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");

//    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//    ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");


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
        for (int i = 0; i < source.getParameterIds().size(); i++) {
            formulaCommand.setParameterIds(source.getParameterIds());
        }
        for (int i = 0; i < source.getParameterDescriptions().size(); i++) {
            formulaCommand.setParameterDescriptions(source.getParameterDescriptions());
        }
        formulaCommand.setFormulaLogic(source.getFormulaLogic());
//        for (int i = 0; i < source.getFormulaLogic().length(); i++)
//        {
//            if (source.getFormulaLogic().contains("π"))
//            {
//                formulaCommand.setFormulaLogic(String.valueOf(22/7));
//            }
//            else formulaCommand.setFormulaLogic(source.getFormulaLogic());
//        }
        for (int i = 0; i < source.getTestParameters().size(); i++) {
            formulaCommand.setTestParameters(source.getTestParameters());
        }
        for (int i = 0; i < source.getNumberOfParameters(); i++) {
            formulaCommand.setExpression("" + source.getParameterIds() + "=" + source.getTestParameters() + ";" + source.getFormulaLogic() + ";" + "");
        }
        try {
            formulaCommand.setResult(Double.parseDouble(engine.eval(formulaCommand.getExpression()).toString()));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        if (source.getServiceNumbers() != null && source.getServiceNumbers().size() > 0) {
            source.getServiceNumbers()
                    .forEach(serviceNumber -> formulaCommand.getServiceNumberCommands().add(serviceNumberConverter.convert(serviceNumber)));
        }
        if (source.getModelSpecificationsDetails() != null && source.getModelSpecificationsDetails().size() > 0) {
            source.getModelSpecificationsDetails()
                    .forEach(modelSpecificationsDetails -> formulaCommand.getModelSpecificationsDetailsCommands().add(modelSpecDetailsConverter.convert(modelSpecificationsDetails)));
        }
        return formulaCommand;
    }
}