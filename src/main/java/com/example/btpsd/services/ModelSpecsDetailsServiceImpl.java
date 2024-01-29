package com.example.btpsd.services;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.converters.ModelSpecDetailsCommandToModelSpecDetails;
import com.example.btpsd.converters.ModelSpecDetailsToModelSpecDetailsCommand;
import com.example.btpsd.model.*;
import com.example.btpsd.repositories.ModelSpecificationsDetailsRepository;
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
public class ModelSpecsDetailsServiceImpl implements ModelSpecsDetailsService{

    private final ModelSpecificationsDetailsRepository modelSpecificationsDetailsRepository;
    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsToModelSpecDetailsCommand;
    private final ModelSpecDetailsCommandToModelSpecDetails modelSpecDetailsCommandToModelSpecDetails;


    @Override
    @Transactional
    public Set<ModelSpecificationsDetailsCommand> getModelSpecDetailsCommands() {

        return StreamSupport.stream(modelSpecificationsDetailsRepository.findAll()
                        .spliterator(), false)
                .map(modelSpecDetailsToModelSpecDetailsCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public ModelSpecificationsDetails findById(Long l) {

        Optional<ModelSpecificationsDetails> modelSpecificationsDetailsOptional = modelSpecificationsDetailsRepository.findById(l);

        if (!modelSpecificationsDetailsOptional.isPresent()) {
            throw new RuntimeException("Model Spec Details Not Found!");
        }

        return modelSpecificationsDetailsOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        modelSpecificationsDetailsRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public ModelSpecificationsDetailsCommand saveModelSpecDetailsCommand(ModelSpecificationsDetailsCommand command) {

        ModelSpecificationsDetails detachedModelSpecDetails = modelSpecDetailsCommandToModelSpecDetails.convert(command);
        ModelSpecificationsDetails savedModelSpecDetails = modelSpecificationsDetailsRepository.save(detachedModelSpecDetails);
        log.debug("Saved Model Spec Details Id:" + savedModelSpecDetails.getModelSpecDetailsCode());
        return modelSpecDetailsToModelSpecDetailsCommand.convert(savedModelSpecDetails);


    }

    @Override
    public ModelSpecificationsDetails updateModelSpecDetails(ModelSpecificationsDetails newModelSpecDetails, Long modelSpecDetailsCode) {

        return modelSpecificationsDetailsRepository.findById(modelSpecDetailsCode).map(oldModelSpecDetails -> {
            if (newModelSpecDetails.getSelectionCheckBox() != oldModelSpecDetails.getSelectionCheckBox())
                oldModelSpecDetails.setSelectionCheckBox(newModelSpecDetails.getSelectionCheckBox());
            if (newModelSpecDetails.getLineIndex() != oldModelSpecDetails.getLineIndex())
                oldModelSpecDetails.setLineIndex(newModelSpecDetails.getLineIndex());
            if (newModelSpecDetails.getDeletionIndicator() != oldModelSpecDetails.getDeletionIndicator())
                oldModelSpecDetails.setDeletionIndicator(newModelSpecDetails.getDeletionIndicator());
            if (newModelSpecDetails.getShortText() != oldModelSpecDetails.getShortText())
                oldModelSpecDetails.setShortText(newModelSpecDetails.getShortText());
            if (newModelSpecDetails.getQuantity() != oldModelSpecDetails.getQuantity())
                oldModelSpecDetails.setQuantity(newModelSpecDetails.getQuantity());
            if (newModelSpecDetails.getGrossPrice() != oldModelSpecDetails.getGrossPrice())
                oldModelSpecDetails.setGrossPrice(newModelSpecDetails.getGrossPrice());
            if (newModelSpecDetails.getOverFulfilmentPercentage() != oldModelSpecDetails.getOverFulfilmentPercentage())
                oldModelSpecDetails.setOverFulfilmentPercentage(newModelSpecDetails.getOverFulfilmentPercentage());
            if (newModelSpecDetails.getPriceChangedAllowed() != oldModelSpecDetails.getPriceChangedAllowed())
                oldModelSpecDetails.setPriceChangedAllowed(newModelSpecDetails.getPriceChangedAllowed());
            if (newModelSpecDetails.getUnlimitedOverFulfillment() != oldModelSpecDetails.getUnlimitedOverFulfillment())
                oldModelSpecDetails.setUnlimitedOverFulfillment(newModelSpecDetails.getUnlimitedOverFulfillment());
            if (newModelSpecDetails.getPricePerUnitOfMeasurement() != oldModelSpecDetails.getPricePerUnitOfMeasurement())
                oldModelSpecDetails.setPricePerUnitOfMeasurement(newModelSpecDetails.getPricePerUnitOfMeasurement());
            if (newModelSpecDetails.getExternalServiceNumber() != oldModelSpecDetails.getExternalServiceNumber())
                oldModelSpecDetails.setExternalServiceNumber(newModelSpecDetails.getExternalServiceNumber());
            if (newModelSpecDetails.getServiceText() != oldModelSpecDetails.getServiceText())
                oldModelSpecDetails.setServiceText(newModelSpecDetails.getServiceText());
            if (newModelSpecDetails.getNetValue() != oldModelSpecDetails.getNetValue())
                oldModelSpecDetails.setNetValue(newModelSpecDetails.getNetValue());
            if (newModelSpecDetails.getLineText() != oldModelSpecDetails.getLineText())
                oldModelSpecDetails.setLineText(newModelSpecDetails.getLineText());
            if (newModelSpecDetails.getFormula() != oldModelSpecDetails.getFormula())
                oldModelSpecDetails.setFormula(newModelSpecDetails.getFormula());
            if (newModelSpecDetails.getLineNumber() != oldModelSpecDetails.getLineNumber())
                oldModelSpecDetails.setLineNumber(newModelSpecDetails.getLineNumber());
            if (newModelSpecDetails.getAlternatives() != oldModelSpecDetails.getAlternatives())
                oldModelSpecDetails.setAlternatives(newModelSpecDetails.getAlternatives());
            if (newModelSpecDetails.getBiddersLine() != oldModelSpecDetails.getBiddersLine())
                oldModelSpecDetails.setBiddersLine(newModelSpecDetails.getBiddersLine());
            if (newModelSpecDetails.getSupplementaryLine() != oldModelSpecDetails.getSupplementaryLine())
                oldModelSpecDetails.setSupplementaryLine(newModelSpecDetails.getSupplementaryLine());
            if (newModelSpecDetails.getLotSizeForCostingIsOne() != oldModelSpecDetails.getLotSizeForCostingIsOne())
                oldModelSpecDetails.setLotSizeForCostingIsOne(newModelSpecDetails.getLotSizeForCostingIsOne());
            if (newModelSpecDetails.getServiceNumberCode() != null) {
                ServiceNumber serviceNumber = new ServiceNumber();
                serviceNumber.setServiceNumberCode(newModelSpecDetails.getServiceNumberCode());
                oldModelSpecDetails.setServiceNumber(serviceNumber);
                serviceNumber.addModelSpecDetails(oldModelSpecDetails);
            }
            if (newModelSpecDetails.getCurrencyCode() != null) {
                Currency currency = new Currency();
                currency.setCurrencyCode(newModelSpecDetails.getCurrencyCode());
                oldModelSpecDetails.setCurrency(currency);
                currency.addModelSpecDetails(oldModelSpecDetails);
            }
            if (newModelSpecDetails.getPersonnelNumberCode() != null) {
                PersonnelNumber personnelNumber = new PersonnelNumber();
                personnelNumber.setPersonnelNumberCode(newModelSpecDetails.getPersonnelNumberCode());
                oldModelSpecDetails.setPersonnelNumber(personnelNumber);
                personnelNumber.addModelSpecDetails(oldModelSpecDetails);
            }
            if (newModelSpecDetails.getServiceTypeCode() != null) {
                ServiceType serviceType = new ServiceType();
                serviceType.setServiceTypeCode(newModelSpecDetails.getServiceNumberCode());
                oldModelSpecDetails.setServiceType(serviceType);
                serviceType.addModelSpecDetails(oldModelSpecDetails);
            }
            if (newModelSpecDetails.getMaterialGroupCode() != null) {
                MaterialGroup materialGroup = new MaterialGroup();
                materialGroup.setMaterialGroupCode(newModelSpecDetails.getMaterialGroupCode());
                oldModelSpecDetails.setMaterialGroup(materialGroup);
                materialGroup.addModelSpecDetails(oldModelSpecDetails);
            }
            if (newModelSpecDetails.getUnitOfMeasurementCode() != null) {
                UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
                unitOfMeasurement.setUnitOfMeasurementCode(newModelSpecDetails.getUnitOfMeasurementCode());
                oldModelSpecDetails.setUnitOfMeasurement(unitOfMeasurement);
                unitOfMeasurement.addModelSpecDetails(oldModelSpecDetails);
            }
            return modelSpecificationsDetailsRepository.save(oldModelSpecDetails);
        }).orElseThrow(() -> new RuntimeException("Model Spec Details not found"));
    }


    @Override
    @Transactional
    public ModelSpecificationsDetailsCommand findModelSpecDetailsCommandById(Long l) {

        return modelSpecDetailsToModelSpecDetailsCommand.convert(findById(l));

    }
}
