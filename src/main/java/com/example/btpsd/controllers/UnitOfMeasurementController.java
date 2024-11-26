package com.example.btpsd.controllers;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
//import com.example.btpsd.config.RestTemplateConfig;
import com.example.btpsd.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;
import com.example.btpsd.repositories.UnitOfMeasurementRepository;
//import com.example.btpsd.repositories.dCloudRepository;
import com.example.btpsd.services.UnitOfMeasurementService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class UnitOfMeasurementController {

    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    private final UnitOfMeasurementService unitOfMeasurementService;

    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;


//    @GetMapping("/measurements")
//    @ResponseBody
//    public String All() throws JSONException, IOException, URISyntaxException {
//
//
//
//        JSONObject jsonFromURL = new JSONObject(IOUtils.toString(new URL("http://localhost:8080/measurementsCloud"), String.valueOf(Charset.forName("UTF-8"))));
//        JSONArray jsonObjectUnits = jsonFromURL.getJSONObject("d").getJSONArray("results");
//        JSONArray newJson = new JSONArray();
//
//        for (int index=0, size = jsonObjectUnits.length(); index < size; index++) {
//
//            JSONObject objectInArray = jsonObjectUnits.getJSONObject(index);
//            String[] elementNames = JSONObject.getNames(objectInArray);
//            for (String elementName : elementNames) {
//                if (elementName.contains("UnitOfMeasure_1") || elementName.equals("UnitOfMeasure") || elementName.contains("__metadata")) {
//                    objectInArray.remove(elementName);
//                }
//            }
//            newJson.put(objectInArray);
//        }
//
//        // saving into db
//
////        for (int index = 0; index < newJson.length(); index++)
////        {
////            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
////            JSONObject objectInsideArray = newJson.getJSONObject(index);
////            String[] elementNames = JSONObject.getNames(objectInsideArray);
////            for (String elementName : elementNames) {
////                if (elementName.equals("UnitOfMeasureSAPCode")) {
////                    unitOfMeasurement.setUnitOfMeasureSAPCode(objectInsideArray.getString("UnitOfMeasureSAPCode"));
////                } else if (elementName.equals("UnitOfMeasureLongName")) {
////                    unitOfMeasurement.setUnitOfMeasureLongName(objectInsideArray.getString("UnitOfMeasureLongName"));
////                }
////                else {
////                    unitOfMeasurement.setUnitOfMeasureName(objectInsideArray.getString("UnitOfMeasureName"));
////                }
////                index++;
////                unitOfMeasurementRepository.save(unitOfMeasurement);
////            }
////        }
//        return newJson.toString();
////        return unitOfMeasurementRepository.findAll();
//    }
//
//
//
//


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

//    @PutMapping
//    @RequestMapping("/measurements/{unitOfMeasurementCode}")
//    UnitOfMeasurementCommand updateUomCommand(@RequestBody UnitOfMeasurementCommand newUomCommand, @PathVariable Long unitOfMeasurementCode) {
//
//        UnitOfMeasurementCommand command = unitOfMeasurementToUnitOfMeasurementCommand.convert(unitOfMeasurementService.u(newUomCommand, unitOfMeasurementCode));
//        return command;
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/measurements/search")
//    @ResponseBody
//    public List<UnitOfMeasurement> Search(@RequestParam String keyword) {
//
//        return unitOfMeasurementRepository.search(keyword);
//    }
}