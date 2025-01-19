package com.example.btpsd.services;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.converters.UnitOfMeasurementCommandToUnitOfMeasurement;
import com.example.btpsd.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;
import com.example.btpsd.model.UnitOfMeasurementResponse;
import com.example.btpsd.repositories.UnitOfMeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService{

//    private final UnitOfMeasurementRepository unitOfMeasurementRepository;
//    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;
//    private final UnitOfMeasurementCommandToUnitOfMeasurement unitOfMeasurementCommandToUnitOfMeasurement;

    private final WebClient webClient;

    // Constructor to initialize WebClient
    public UnitOfMeasurementServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("https://my418629.s4hana.cloud.sap/sap/opu/odata/sap/CO_FNDEI_UNITOFMEASUREMENT_RL")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodeCredentials("BTP_USER1", "yiVfheJbFolFxgkEwCBFcWvYkPzrQDENEArAXn5"))
                .build();
    }

    // Encode credentials for Basic Authentication
    private String encodeCredentials(String username, String password) {
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    // Fetch unit of measurements from the OData service
    public List<UnitOfMeasurement> fetchUnitOfMeasurements() {
        return webClient.get()
                .uri("/UnitOfMeasurementSet?$format=json") // OData endpoint
                .retrieve()
                .bodyToMono(UnitOfMeasurementResponse.class) // Map response to UnitOfMeasurementResponse
                .block() // Synchronous call
                .getUnitOfMeasurements(); // Extract the list from the response
    }

//    @Override
//    @Transactional
//    public Set<UnitOfMeasurementCommand> getUnitOfMeasurementCommands() {
//
//        return StreamSupport.stream(unitOfMeasurementRepository.findAll()
//                        .spliterator(), false)
//                .map(unitOfMeasurementToUnitOfMeasurementCommand::convert)
//                .collect(Collectors.toSet());
//
//    }
//
//    @Override
//    public UnitOfMeasurement findById(Long l) {
//
//        Optional<UnitOfMeasurement> unitOfMeasurementOptional = unitOfMeasurementRepository.findById(l);
//
//        if (!unitOfMeasurementOptional.isPresent()) {
//            throw new RuntimeException("Unit Of Measurement Not Found!");
//        }
//
//        return unitOfMeasurementOptional.get();
//
//    }
//
//    @Override
//    public void deleteById(Long idToDelete) {
//
//        unitOfMeasurementRepository.deleteById(idToDelete);
//
//    }
//
//    @Override
//    @Transactional
//    public UnitOfMeasurementCommand saveUnitOfMeasurementCommand(UnitOfMeasurementCommand command) {
//
//        UnitOfMeasurement detachedUOM = unitOfMeasurementCommandToUnitOfMeasurement.convert(command);
//        UnitOfMeasurement savedUOM = unitOfMeasurementRepository.save(detachedUOM);
//        log.debug("Saved Currency Id:" + savedUOM.getUnitOfMeasurementCode());
//        return unitOfMeasurementToUnitOfMeasurementCommand.convert(savedUOM);
//
//    }
//
////    @Override
////    public UnitOfMeasurement updateUnitOfMeasurement(UnitOfMeasurementCommand newUnitOfMeasurementCommand, Long l) {
////
////        return unitOfMeasurementRepository.findById(l).map(oldUOM -> {
////            if (newUnitOfMeasurementCommand.getUnitOfMeasureLongName() != oldUOM.getCode())
////                oldUOM.setCode(newUnitOfMeasurementCommand.getCode());
////            if (newUnitOfMeasurementCommand.getDescription() != oldUOM.getDescription())
////                oldUOM.setDescription(newUnitOfMeasurementCommand.getDescription());
////            return unitOfMeasurementRepository.save(oldUOM);
////        }).orElseThrow(() -> new RuntimeException("Unit Of Measurement not found"));
////
////    }
//
//    @Override
//    @Transactional
//    public UnitOfMeasurementCommand findUnitOfMeasurementCommandById(Long l) {
//
//        return unitOfMeasurementToUnitOfMeasurementCommand.convert(findById(l));
//
//    }
}