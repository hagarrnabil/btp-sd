
/*
 * Generated by OData VDM code generator of SAP Cloud SDK in version 5.6.0
 */

package com.example.datamodel.odata.namespaces.maintenancelookuptabledata;

import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.example.datamodel.odata.services.MaintenanceLookupTableDataService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.JsonAdapter;
import com.sap.cloud.sdk.datamodel.odata.client.request.ODataEntityKey;
import com.sap.cloud.sdk.datamodel.odatav4.core.SimpleProperty;
import com.sap.cloud.sdk.datamodel.odatav4.core.VdmEntity;
import com.sap.cloud.sdk.datamodel.odatav4.core.VdmEntitySet;
import com.sap.cloud.sdk.result.ElementName;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * <p>Original entity name from the Odata EDM: <b>LookupTableRowsParameters</b></p>
 * 
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true, callSuper = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
@JsonAdapter(com.sap.cloud.sdk.datamodel.odatav4.adapter.GsonVdmAdapterFactory.class)
@JsonSerialize(using = com.sap.cloud.sdk.datamodel.odatav4.adapter.JacksonVdmObjectSerializer.class)
@JsonDeserialize(using = com.sap.cloud.sdk.datamodel.odatav4.adapter.JacksonVdmObjectDeserializer.class)
public class LookupTableRowsParameters
    extends VdmEntity<LookupTableRowsParameters>
    implements VdmEntitySet
{

    @Getter
    private final java.lang.String odataType = "MaintenanceLookupTableDataService.LookupTableRowsParameters";
    /**
     * Selector for all available fields of LookupTableRowsParameters.
     * 
     */
    public final static SimpleProperty<LookupTableRowsParameters> ALL_FIELDS = all();
    /**
     * (Key Field) Constraints: Not nullable, Maximum length: 20 <p>Original property name from the Odata EDM: <b>lookupTableCode</b></p>
     * 
     * @return
     *     The lookupTableCode contained in this {@link VdmEntity}.
     */
    @Nullable
    @ElementName("lookupTableCode")
    private java.lang.String lookupTableCode;
    public final static SimpleProperty.String<LookupTableRowsParameters> LOOKUP_TABLE_CODE = new SimpleProperty.String<LookupTableRowsParameters>(LookupTableRowsParameters.class, "lookupTableCode");
    /**
     * Navigation property <b>Set</b> for <b>LookupTableRowsParameters</b> to multiple <b>LookupTableRows</b>.
     * 
     */
    @ElementName("Set")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<LookupTableRows> toSet;
    /**
     * Use with available request builders to apply the <b>Set</b> navigation property to query operations.
     * 
     */
    public final static com.sap.cloud.sdk.datamodel.odatav4.core.NavigationProperty.Collection<LookupTableRowsParameters, LookupTableRows> TO_SET = new com.sap.cloud.sdk.datamodel.odatav4.core.NavigationProperty.Collection<LookupTableRowsParameters, LookupTableRows>(LookupTableRowsParameters.class, "Set", LookupTableRows.class);

    @Nonnull
    @Override
    public Class<LookupTableRowsParameters> getType() {
        return LookupTableRowsParameters.class;
    }

    /**
     * (Key Field) Constraints: Not nullable, Maximum length: 20 <p>Original property name from the Odata EDM: <b>lookupTableCode</b></p>
     * 
     * @param lookupTableCode
     *     The lookupTableCode to set.
     */
    public void setLookupTableCode(
        @Nullable
        final java.lang.String lookupTableCode) {
        rememberChangedField("lookupTableCode", this.lookupTableCode);
        this.lookupTableCode = lookupTableCode;
    }

    @Override
    protected java.lang.String getEntityCollection() {
        return "LookupTableRows";
    }

    @Nonnull
    @Override
    protected ODataEntityKey getKey() {
        final ODataEntityKey entityKey = super.getKey();
        entityKey.addKeyProperty("lookupTableCode", getLookupTableCode());
        return entityKey;
    }

    @Nonnull
    @Override
    protected Map<java.lang.String, Object> toMapOfFields() {
        final Map<java.lang.String, Object> values = super.toMapOfFields();
        values.put("lookupTableCode", getLookupTableCode());
        return values;
    }

    @Override
    protected void fromMap(final Map<java.lang.String, Object> inputValues) {
        final Map<java.lang.String, Object> values = Maps.newHashMap(inputValues);
        // simple properties
        {
            if (values.containsKey("lookupTableCode")) {
                final Object value = values.remove("lookupTableCode");
                if ((value == null)||(!value.equals(getLookupTableCode()))) {
                    setLookupTableCode(((java.lang.String) value));
                }
            }
        }
        // structured properties
        {
        }
        // navigation properties
        {
            if ((values).containsKey("Set")) {
                final Object value = (values).remove("Set");
                if (value instanceof Iterable) {
                    if (toSet == null) {
                        toSet = Lists.newArrayList();
                    } else {
                        toSet = Lists.newArrayList(toSet);
                    }
                    int i = 0;
                    for (Object item: ((Iterable<?> ) value)) {
                        if (!(item instanceof Map)) {
                            continue;
                        }
                        LookupTableRows entity;
                        if (toSet.size()>i) {
                            entity = toSet.get(i);
                        } else {
                            entity = new LookupTableRows();
                            toSet.add(entity);
                        }
                        i = (i + 1);
                        @SuppressWarnings("unchecked")
                        final Map<java.lang.String, Object> inputMap = ((Map<java.lang.String, Object> ) item);
                        entity.fromMap(inputMap);
                    }
                }
            }
        }
        super.fromMap(values);
    }

    @Override
    protected java.lang.String getDefaultServicePath() {
        return MaintenanceLookupTableDataService.DEFAULT_SERVICE_PATH;
    }

    @Nonnull
    @Override
    protected Map<java.lang.String, Object> toMapOfNavigationProperties() {
        final Map<java.lang.String, Object> values = super.toMapOfNavigationProperties();
        if (toSet!= null) {
            (values).put("Set", toSet);
        }
        return values;
    }

    /**
     * Retrieval of associated <b>LookupTableRows</b> entities (one to many). This corresponds to the OData navigation property <b>Set</b>.
     * <p>
     * If the navigation property for an entity <b>LookupTableRowsParameters</b> has not been resolved yet, this method will <b>not query</b> further information. Instead its <code>Option</code> result state will be <code>empty</code>.
     * 
     * @return
     *     If the information for navigation property <b>Set</b> is already loaded, the result will contain the <b>LookupTableRows</b> entities. If not, an <code>Option</code> with result state <code>empty</code> is returned.
     */
    @Nonnull
    public Option<List<LookupTableRows>> getSetIfPresent() {
        return Option.of(toSet);
    }

    /**
     * Overwrites the list of associated <b>LookupTableRows</b> entities for the loaded navigation property <b>Set</b>.
     * <p>
     * If the navigation property <b>Set</b> of a queried <b>LookupTableRowsParameters</b> is operated lazily, an <b>ODataException</b> can be thrown in case of an OData query error.
     * <p>
     * Please note: <i>Lazy</i> loading of OData entity associations is the process of asynchronous retrieval and persisting of items from a navigation property. If a <i>lazy</i> property is requested by the application for the first time and it has not yet been loaded, an OData query will be run in order to load the missing information and its result will get cached for future invocations.
     * 
     * @param value
     *     List of <b>LookupTableRows</b> entities.
     */
    public void setSet(
        @Nonnull
        final List<LookupTableRows> value) {
        if (toSet == null) {
            toSet = Lists.newArrayList();
        }
        toSet.clear();
        toSet.addAll(value);
    }

    /**
     * Adds elements to the list of associated <b>LookupTableRows</b> entities. This corresponds to the OData navigation property <b>Set</b>.
     * <p>
     * If the navigation property <b>Set</b> of a queried <b>LookupTableRowsParameters</b> is operated lazily, an <b>ODataException</b> can be thrown in case of an OData query error.
     * <p>
     * Please note: <i>Lazy</i> loading of OData entity associations is the process of asynchronous retrieval and persisting of items from a navigation property. If a <i>lazy</i> property is requested by the application for the first time and it has not yet been loaded, an OData query will be run in order to load the missing information and its result will get cached for future invocations.
     * 
     * @param entity
     *     Array of <b>LookupTableRows</b> entities.
     */
    public void addSet(LookupTableRows... entity) {
        if (toSet == null) {
            toSet = Lists.newArrayList();
        }
        toSet.addAll(Lists.newArrayList(entity));
    }


    /**
     * Helper class to allow for fluent creation of LookupTableRowsParameters instances.
     * 
     */
    public final static class LookupTableRowsParametersBuilder {

        private List<LookupTableRows> toSet = Lists.newArrayList();

        private LookupTableRowsParameters.LookupTableRowsParametersBuilder toSet(final List<LookupTableRows> value) {
            toSet.addAll(value);
            return this;
        }

        /**
         * Navigation property <b>Set</b> for <b>LookupTableRowsParameters</b> to multiple <b>LookupTableRows</b>.
         * 
         * @param value
         *     The LookupTableRowss to build this LookupTableRowsParameters with.
         * @return
         *     This Builder to allow for a fluent interface.
         */
        @Nonnull
        public LookupTableRowsParameters.LookupTableRowsParametersBuilder set(LookupTableRows... value) {
            return toSet(Lists.newArrayList(value));
        }

    }

}
