
/*
 * Generated by OData VDM code generator of SAP Cloud SDK in version 5.6.0
 */

package com.example.datamodel.odata.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.LookupTableInputs;
import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.LookupTableOutputs;
import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.LookupTableRowsParameters;
import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.LookupTables;
import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.MaintainLookupTableRow;
import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.MaintainRowResult;
import com.example.datamodel.odata.namespaces.maintenancelookuptabledata.PriceConditionRanges;
import com.sap.cloud.sdk.datamodel.odatav4.core.BatchRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.CollectionValueActionRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.CountRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.CreateRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.DeleteRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.GetAllRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.GetByKeyRequestBuilder;
import com.sap.cloud.sdk.datamodel.odatav4.core.ServiceWithNavigableEntities;
import com.sap.cloud.sdk.datamodel.odatav4.core.UpdateRequestBuilder;
import lombok.Getter;


/**
 * <h3>Details:</h3><table summary='Details'><tr><td align='right'>OData Service:</td><td>MaintenanceLookupTableDataService</td></tr></table>
 * 
 */
public class DefaultMaintenanceLookupTableDataService
    implements MaintenanceLookupTableDataService, ServiceWithNavigableEntities
{

    @Nonnull
    @Getter
    private final String servicePath;

    /**
     * Creates a service using {@link MaintenanceLookupTableDataService#DEFAULT_SERVICE_PATH} to send the requests.
     * 
     */
    public DefaultMaintenanceLookupTableDataService() {
        servicePath = MaintenanceLookupTableDataService.DEFAULT_SERVICE_PATH;
    }

    /**
     * Creates a service using the provided service path to send the requests.
     * <p>
     * Used by the fluent {@link #withServicePath(String)} method.
     * 
     */
    private DefaultMaintenanceLookupTableDataService(
        @Nonnull
        final String servicePath) {
        this.servicePath = servicePath;
    }

    @Override
    @Nonnull
    public DefaultMaintenanceLookupTableDataService withServicePath(
        @Nonnull
        final String servicePath) {
        return new DefaultMaintenanceLookupTableDataService(servicePath);
    }

    @Override
    @Nonnull
    public BatchRequestBuilder batch() {
        return new BatchRequestBuilder(servicePath);
    }

    @Override
    @Nonnull
    public GetAllRequestBuilder<LookupTables> getAllLookupTables() {
        return new GetAllRequestBuilder<LookupTables>(servicePath, LookupTables.class, "LookupTables");
    }

    @Override
    @Nonnull
    public CountRequestBuilder<LookupTables> countLookupTables() {
        return new CountRequestBuilder<LookupTables>(servicePath, LookupTables.class, "LookupTables");
    }

    @Override
    @Nonnull
    public GetByKeyRequestBuilder<LookupTables> getLookupTablesByKey(final String code) {
        final Map<String, Object> key = new HashMap<String, Object>();
        key.put("code", code);
        return new GetByKeyRequestBuilder<LookupTables>(servicePath, LookupTables.class, key, "LookupTables");
    }

    @Override
    @Nonnull
    public GetAllRequestBuilder<LookupTableInputs> getAllLookupTableInputs() {
        return new GetAllRequestBuilder<LookupTableInputs>(servicePath, LookupTableInputs.class, "LookupTableInputs");
    }

    @Override
    @Nonnull
    public CountRequestBuilder<LookupTableInputs> countLookupTableInputs() {
        return new CountRequestBuilder<LookupTableInputs>(servicePath, LookupTableInputs.class, "LookupTableInputs");
    }

    @Override
    @Nonnull
    public GetByKeyRequestBuilder<LookupTableInputs> getLookupTableInputsByKey(final String code) {
        final Map<String, Object> key = new HashMap<String, Object>();
        key.put("code", code);
        return new GetByKeyRequestBuilder<LookupTableInputs>(servicePath, LookupTableInputs.class, key, "LookupTableInputs");
    }

    @Override
    @Nonnull
    public GetAllRequestBuilder<LookupTableOutputs> getAllLookupTableOutputs() {
        return new GetAllRequestBuilder<LookupTableOutputs>(servicePath, LookupTableOutputs.class, "LookupTableOutputs");
    }

    @Override
    @Nonnull
    public CountRequestBuilder<LookupTableOutputs> countLookupTableOutputs() {
        return new CountRequestBuilder<LookupTableOutputs>(servicePath, LookupTableOutputs.class, "LookupTableOutputs");
    }

    @Override
    @Nonnull
    public GetByKeyRequestBuilder<LookupTableOutputs> getLookupTableOutputsByKey(final String code) {
        final Map<String, Object> key = new HashMap<String, Object>();
        key.put("code", code);
        return new GetByKeyRequestBuilder<LookupTableOutputs>(servicePath, LookupTableOutputs.class, key, "LookupTableOutputs");
    }

    @Override
    @Nonnull
    public GetByKeyRequestBuilder<PriceConditionRanges> getPriceConditionRangesByKey() {
        final Map<String, Object> key = new HashMap<String, Object>();
        return new GetByKeyRequestBuilder<PriceConditionRanges>(servicePath, PriceConditionRanges.class, key, "PriceConditionRanges");
    }

    @Override
    @Nonnull
    public GetAllRequestBuilder<LookupTableRowsParameters> getAllLookupTableRows() {
        return new GetAllRequestBuilder<LookupTableRowsParameters>(servicePath, LookupTableRowsParameters.class, "LookupTableRows");
    }

    @Override
    @Nonnull
    public CountRequestBuilder<LookupTableRowsParameters> countLookupTableRows() {
        return new CountRequestBuilder<LookupTableRowsParameters>(servicePath, LookupTableRowsParameters.class, "LookupTableRows");
    }

    @Override
    @Nonnull
    public GetByKeyRequestBuilder<LookupTableRowsParameters> getLookupTableRowsByKey(final String lookupTableCode) {
        final Map<String, Object> key = new HashMap<String, Object>();
        key.put("lookupTableCode", lookupTableCode);
        return new GetByKeyRequestBuilder<LookupTableRowsParameters>(servicePath, LookupTableRowsParameters.class, key, "LookupTableRows");
    }

    @Override
    @Nonnull
    public CreateRequestBuilder<LookupTableRowsParameters> createLookupTableRows(
        @Nonnull
        final LookupTableRowsParameters lookupTableRowsParameters) {
        return new CreateRequestBuilder<LookupTableRowsParameters>(servicePath, lookupTableRowsParameters, "LookupTableRows");
    }

    @Override
    @Nonnull
    public UpdateRequestBuilder<LookupTableRowsParameters> updateLookupTableRows(
        @Nonnull
        final LookupTableRowsParameters lookupTableRowsParameters) {
        return new UpdateRequestBuilder<LookupTableRowsParameters>(servicePath, lookupTableRowsParameters, "LookupTableRows");
    }

    @Override
    @Nonnull
    public DeleteRequestBuilder<LookupTableRowsParameters> deleteLookupTableRows(
        @Nonnull
        final LookupTableRowsParameters lookupTableRowsParameters) {
        return new DeleteRequestBuilder<LookupTableRowsParameters>(servicePath, lookupTableRowsParameters, "LookupTableRows");
    }

    @Override
    @Nonnull
    public CollectionValueActionRequestBuilder<MaintainRowResult> maintainRows(
        @Nonnull
        final Collection<MaintainLookupTableRow> rows) {
        final LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
        parameters.put("rows", rows);
        return new CollectionValueActionRequestBuilder<MaintainRowResult>(servicePath, "maintainRows", parameters, MaintainRowResult.class);
    }

}
