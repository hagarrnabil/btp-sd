
/*
 * Generated by OData VDM code generator of SAP Cloud SDK in version 5.3.0
 */

package vdm.com.example.datamodel.odata.services;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import com.sap.cloud.sdk.datamodel.odatav4.core.BatchRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.CountRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.CreateRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.DeleteRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.GetAllRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.GetByKeyRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.ServiceWithNavigableEntities;
import com.sap.cloud.sdk.datamodel.odatav4.core.UpdateRequestBuilder;
import lombok.Getter;
import vdm.com.example.datamodel.odata.namespaces.unitofmeasurement.UnitOfMeasures;


/**
 * <h3>Details:</h3><table summary='Details'><tr><td align='right'>OData Service:</td><td>UnitofMeasurement</td></tr></table>
 * 
 */
public class DefaultUnitofMeasurementService
    implements UnitofMeasurementService, ServiceWithNavigableEntities
{

    @Nonnull
    @Getter
    private final String servicePath;

    /**
     * Creates a service using {@link UnitofMeasurementService#DEFAULT_SERVICE_PATH} to send the requests.
     * 
     */
    public DefaultUnitofMeasurementService() {
        servicePath = UnitofMeasurementService.DEFAULT_SERVICE_PATH;
    }

    /**
     * Creates a service using the provided service path to send the requests.
     * <p>
     * Used by the fluent {@link #withServicePath(String)} method.
     * 
     */
    private DefaultUnitofMeasurementService(
        @Nonnull
        final String servicePath) {
        this.servicePath = servicePath;
    }

    @Override
    @Nonnull
    public DefaultUnitofMeasurementService withServicePath(
        @Nonnull
        final String servicePath) {
        return new DefaultUnitofMeasurementService(servicePath);
    }

    @Override
    @Nonnull
    public BatchRequestBuilder batch() {
        return new BatchRequestBuilder(servicePath);
    }

    @Override
    @Nonnull
    public GetAllRequestBuilder<UnitOfMeasures> getAllUnitOfMeasures() {
        return new GetAllRequestBuilder<UnitOfMeasures>(servicePath, UnitOfMeasures.class, "UnitOfMeasures");
    }

    @Override
    @Nonnull
    public CountRequestBuilder<UnitOfMeasures> countUnitOfMeasures() {
        return new CountRequestBuilder<UnitOfMeasures>(servicePath, UnitOfMeasures.class, "UnitOfMeasures");
    }

    @Override
    @Nonnull
    public GetByKeyRequestBuilder<UnitOfMeasures> getUnitOfMeasuresByKey(final String code) {
        final Map<String, Object> key = new HashMap<String, Object>();
        key.put("code", code);
        return new GetByKeyRequestBuilder<UnitOfMeasures>(servicePath, UnitOfMeasures.class, key, "UnitOfMeasures");
    }

    @Override
    @Nonnull
    public CreateRequestBuilder<UnitOfMeasures> createUnitOfMeasures(
        @Nonnull
        final UnitOfMeasures unitOfMeasures) {
        return new CreateRequestBuilder<UnitOfMeasures>(servicePath, unitOfMeasures, "UnitOfMeasures");
    }

    @Override
    @Nonnull
    public UpdateRequestBuilder<UnitOfMeasures> updateUnitOfMeasures(
        @Nonnull
        final UnitOfMeasures unitOfMeasures) {
        return new UpdateRequestBuilder<UnitOfMeasures>(servicePath, unitOfMeasures, "UnitOfMeasures");
    }

    @Override
    @Nonnull
    public DeleteRequestBuilder<UnitOfMeasures> deleteUnitOfMeasures(
        @Nonnull
        final UnitOfMeasures unitOfMeasures) {
        return new DeleteRequestBuilder<UnitOfMeasures>(servicePath, unitOfMeasures, "UnitOfMeasures");
    }

}
