package com.example.btpsd.services;

import com.example.btpsd.commands.ModelSpecificationsCommand;
import com.example.btpsd.converters.ModelSpecificationsCommandToModelSpecifications;
import com.example.btpsd.converters.ModelSpecificationsToModelSpecificationsCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ModelSpecifications;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.repositories.ModelSpecificationRepository;
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
public class ModelSpecsServiceImpl implements ModelSpecsService {

    private final ModelSpecificationRepository modelSpecificationRepository;
    private final ModelSpecificationsToModelSpecificationsCommand modelSpecificationsToModelSpecificationsCommand;
    private final ModelSpecificationsCommandToModelSpecifications modelSpecificationsCommandToModelSpecifications;


    @Override
    @Transactional
    public Set<ModelSpecificationsCommand> getModelSpecsCommands() {

        return StreamSupport.stream(modelSpecificationRepository.findAll()
                        .spliterator(), false)
                .map(modelSpecificationsToModelSpecificationsCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public ModelSpecifications findById(Long l) {

        Optional<ModelSpecifications> modelSpecificationsOptional = modelSpecificationRepository.findById(l);

        if (!modelSpecificationsOptional.isPresent()) {
            throw new RuntimeException("Model Specs Not Found!");
        }

        return modelSpecificationsOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        modelSpecificationRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public ModelSpecificationsCommand saveModelSpecsCommand(ModelSpecificationsCommand command) {

        ModelSpecifications detachedModelSpecs = modelSpecificationsCommandToModelSpecifications.convert(command);
        ModelSpecifications savedModelSpecs = modelSpecificationRepository.save(detachedModelSpecs);
        log.debug("Saved Model Specs Id:" + savedModelSpecs.getModelSpecCode());
        return modelSpecificationsToModelSpecificationsCommand.convert(savedModelSpecs);


    }

    @Override
    public ModelSpecifications updateModelSpecs(ModelSpecifications newModelSpecs, Long modelSpecCode) {

        return modelSpecificationRepository.findById(modelSpecCode).map(oldModelSpecs -> {
            if (newModelSpecs.getModelServSpec() != oldModelSpecs.getModelServSpec())
                oldModelSpecs.setModelServSpec(newModelSpecs.getModelServSpec());
            if (newModelSpecs.getBlockingIndicator() != oldModelSpecs.getBlockingIndicator())
                oldModelSpecs.setBlockingIndicator(newModelSpecs.getBlockingIndicator());
            if (newModelSpecs.getServiceSelection() != oldModelSpecs.getServiceSelection())
                oldModelSpecs.setServiceSelection(newModelSpecs.getServiceSelection());
            if (newModelSpecs.getDescription() != oldModelSpecs.getDescription())
                oldModelSpecs.setDescription(newModelSpecs.getDescription());
            if (newModelSpecs.getSearchTerm() != oldModelSpecs.getSearchTerm())
                oldModelSpecs.setSearchTerm(newModelSpecs.getSearchTerm());
            if (newModelSpecs.getModelSpecDetailsCode() != null) {
//                ModelSpecificationsDetails modelSpecificationsDetails = new ModelSpecificationsDetails();
//                modelSpecificationsDetails.setModelSpecDetailsCode(Long.parseLong(newModelSpecs.getModelSpecDetailsCode().toString()));
                oldModelSpecs.setModelSpecDetailsCode(newModelSpecs.getModelSpecDetailsCode());
//                modelSpecificationsDetails.addModelSpecifications(oldModelSpecs);
            }
            if (newModelSpecs.getCurrencyCode() != null) {
                Currency currency = new Currency();
                currency.setCurrencyCode(newModelSpecs.getCurrencyCode());
                oldModelSpecs.setCurrency(currency);
                currency.addModelSpecifications(oldModelSpecs);
            }
            return modelSpecificationRepository.save(oldModelSpecs);
        }).orElseThrow(() -> new RuntimeException("Model Specs not found"));
    }

    @Override
    @Transactional
    public ModelSpecificationsCommand findModelSpecsCommandById(Long l) {

        return modelSpecificationsToModelSpecificationsCommand.convert(findById(l));

    }
}
