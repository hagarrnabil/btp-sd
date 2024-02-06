package com.example.btpsd.repositories;

import com.example.btpsd.model.ModelSpecificationsDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelSpecificationsDetailsRepository extends CrudRepository<ModelSpecificationsDetails, Long> {

    @Query("SELECT m FROM ModelSpecificationsDetails m WHERE CONCAT(m.unlimitedOverFulfillment, ' ', m.supplementaryLine, ' ', m.shortText,' ',m.selectionCheckBox" +
            ",' ',m.selectionCheckBox,' ',m.quantity,' ',m.pricePerUnitOfMeasurement,' ',m.pricePerUnitOfMeasurement,' ',m.priceChangedAllowed,' ',m.priceChangedAllowed" +
            ",' ',m.netValue,' ',m.lotSizeForCostingIsOne,' ',m.lineText,' ',m.lineNumber,' ',m.lineIndex,' ',m.externalServiceNumber,' ',m.biddersLine" +
            ",' ',m.alternatives,' ',m.formulaCode,' ',m.personnelNumberCode,' ',m.serviceNumberCode,' ',m.serviceNumberCode,' ',m.deletionIndicator,' ',m.currencyCode" +
            ",' ',m.modelSpecDetailsCode,' ',m.serviceTypeCode,' ',m.materialGroupCode,' ',m.grossPrice,' ',m.overFulfilmentPercentage,' ',m.serviceText,' ',m.unitOfMeasurementCode) LIKE %?1%")
    public List<ModelSpecificationsDetails> search(String keyword);
}
