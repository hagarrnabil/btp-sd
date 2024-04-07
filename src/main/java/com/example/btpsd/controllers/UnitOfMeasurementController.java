package com.example.btpsd.controllers;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.config.RestTemplateConfig;
import com.example.btpsd.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;
import com.example.btpsd.model.UnitOfMeasurementCloud;
import com.example.btpsd.repositories.UnitOfMeasurementRepository;
import com.example.btpsd.services.UnitOfMeasurementService;
import com.owlike.genson.Genson;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.HttpMethod;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UnitOfMeasurementController {

    @Autowired
    RestTemplateConfig restTemplateConfig;

    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    private final UnitOfMeasurementService unitOfMeasurementService;

    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;

    @GetMapping("/measurements")
    @ResponseBody
    public String all() {

        final String uri = "http://localhost:8080/measurementsCloud";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);

        return result;
    }
//        String url = "http://localhost:8080/measurementsCloud";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//        HttpEntity<Iterable<UnitOfMeasurementCommand>> entity = new HttpEntity<Iterable<UnitOfMeasurementCommand>>(headers);
//        return restTemplateConfig.restTemplate().exchange(url, HttpMethod.GET, entity, Iterable.class).getBody();

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
    public String post() {

        final URI uri = URI.create("http://localhost:8080/measurementsCloud");

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, HttpMethod.POST, String.class);

        System.out.println(result);

        return result;


    }
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

//    UnitOfMeasurementCommand newUomCommand(@RequestBody UnitOfMeasurementCommand newUomCommand) {
//
//        UnitOfMeasurementCommand savedCommand = unitOfMeasurementService.saveUnitOfMeasurementCommand(newUomCommand);
//        return savedCommand;
//
//    }

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
