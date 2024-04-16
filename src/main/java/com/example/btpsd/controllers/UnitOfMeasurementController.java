package com.example.btpsd.controllers;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.config.RestTemplateConfig;
import com.example.btpsd.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;
import com.example.btpsd.model.dCloud;
import com.example.btpsd.repositories.UnitOfMeasurementRepository;
//import com.example.btpsd.repositories.dCloudRepository;
import com.example.btpsd.services.UnitOfMeasurementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class UnitOfMeasurementController {

    @Autowired
    RestTemplateConfig restTemplateConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private dCloudRepository dCloudRepository;

    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    private final UnitOfMeasurementService unitOfMeasurementService;

    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;

    @GetMapping("/measurements")
    @ResponseBody
    public dCloud all() throws JsonProcessingException {

        final String uri = "http://localhost:8080/measurementsCloud";

        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);

        dCloud result = restTemplate.getForObject(uri, dCloud.class);

        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

//        List<dCloud> results = objectMapper.readValue(result.toString(), new TypeReference<List<dCloud>>(){});
//
//        // Save the list into a database
//        if(Objects.nonNull(results)) {
//            results.stream().filter(Objects::nonNull).forEach(element -> dCloudRepository.saveAndFlush(element));
//        }

//
//        System.out.println(result);

        return result;
    }


//    @PostMapping("/measurements")
//    public String post() {
//
//        final URI uri = URI.create("http://localhost:8080/measurementsCloud");
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.postForObject(uri, HttpMethod.POST, String.class);
//
//        System.out.println(result);
//
//        return result;
//
//
//    }


    //
//    HttpHeaders headers = new HttpHeaders();
//    final String uri = "http://localhost:8080/measurementsCloud";
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//    HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//    String result = restTemplateConfig.restTemplate().exchange(uri, HttpMethod.POST, entity, String.class);
//        return result;
//        final URI uri = URI.create("http://localhost:8080/measurementsCloud");
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.postForObject(uri, HttpMethod.POST, String.class);
//
//        System.out.println(result);
//
//        return result;
//    }

//    @GetMapping("/measurements")
//    Set<UnitOfMeasurementCommand> all() {
//        return unitOfMeasurementService.getUnitOfMeasurementCommands();
//    }

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
