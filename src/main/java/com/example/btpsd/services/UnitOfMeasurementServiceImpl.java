package com.example.btpsd.services;

import com.example.btpsd.commands.UnitOfMeasurementCommand;
import com.example.btpsd.converters.UnitOfMeasurementCommandToUnitOfMeasurement;
import com.example.btpsd.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.example.btpsd.model.UnitOfMeasurement;
import com.example.btpsd.repositories.UnitOfMeasurementRepository;
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
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService{

    private final UnitOfMeasurementRepository unitOfMeasurementRepository;
    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;
    private final UnitOfMeasurementCommandToUnitOfMeasurement unitOfMeasurementCommandToUnitOfMeasurement;


    @Override
    @Transactional
    public Set<UnitOfMeasurementCommand> getUnitOfMeasurementCommands() {

        return StreamSupport.stream(unitOfMeasurementRepository.findAll()
                        .spliterator(), false)
                .map(unitOfMeasurementToUnitOfMeasurementCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public UnitOfMeasurement findById(Long l) {

        Optional<UnitOfMeasurement> unitOfMeasurementOptional = unitOfMeasurementRepository.findById(l);

        if (!unitOfMeasurementOptional.isPresent()) {
            throw new RuntimeException("Unit Of Measurement Not Found!");
        }

        return unitOfMeasurementOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        unitOfMeasurementRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public UnitOfMeasurementCommand saveUnitOfMeasurementCommand(UnitOfMeasurementCommand command) {

        UnitOfMeasurement detachedUOM = unitOfMeasurementCommandToUnitOfMeasurement.convert(command);
        UnitOfMeasurement savedUOM = unitOfMeasurementRepository.save(detachedUOM);
        log.debug("Saved Currency Id:" + savedUOM.getUnitOfMeasurementCode());
        return unitOfMeasurementToUnitOfMeasurementCommand.convert(savedUOM);

    }

    @Override
    public UnitOfMeasurement updateUnitOfMeasurement(UnitOfMeasurementCommand newUnitOfMeasurementCommand, Long l) {

        return unitOfMeasurementRepository.findById(l).map(oldUOM -> {
            if (newUnitOfMeasurementCommand.getCode() != oldUOM.getCode())
                oldUOM.setCode(newUnitOfMeasurementCommand.getCode());
            if (newUnitOfMeasurementCommand.getDescription() != oldUOM.getDescription())
                oldUOM.setDescription(newUnitOfMeasurementCommand.getDescription());
            return unitOfMeasurementRepository.save(oldUOM);
        }).orElseThrow(() -> new RuntimeException("Unit Of Measurement not found"));

    }

    @Override
    @Transactional
    public UnitOfMeasurementCommand findUnitOfMeasurementCommandById(Long l) {

        return unitOfMeasurementToUnitOfMeasurementCommand.convert(findById(l));

    }
}
