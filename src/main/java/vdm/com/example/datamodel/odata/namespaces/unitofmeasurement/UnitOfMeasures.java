
/*
 * Generated by OData VDM code generator of SAP Cloud SDK in version 5.3.0
 */

package vdm.com.example.datamodel.odata.namespaces.unitofmeasurement;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;
import com.google.gson.annotations.JsonAdapter;
import com.sap.cloud.sdk.datamodel.odata.client.request.ODataEntityKey;
import com.sap.cloud.sdk.datamodel.odatav4.core.SimpleProperty;
import com.sap.cloud.sdk.datamodel.odatav4.core.VdmEntity;
import com.sap.cloud.sdk.datamodel.odatav4.core.VdmEntitySet;
import com.sap.cloud.sdk.result.ElementName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import vdm.com.example.datamodel.odata.services.UnitofMeasurementService;


/**
 * <p>Original entity name from the Odata EDM: <b>UnitOfMeasures</b></p>
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
public class UnitOfMeasures
    extends VdmEntity<UnitOfMeasures>
    implements VdmEntitySet
{

    @Getter
    private final java.lang.String odataType = "MasterDataService.UnitOfMeasures";
    /**
     * Selector for all available fields of UnitOfMeasures.
     * 
     */
    public final static SimpleProperty<UnitOfMeasures> ALL_FIELDS = all();
    /**
     * (Key Field) Constraints: Not nullable<p>Original property name from the Odata EDM: <b>code</b></p>
     * 
     * @return
     *     The code contained in this {@link VdmEntity}.
     */
    @Nullable
    @ElementName("code")
    private java.lang.String code;
    public final static SimpleProperty.String<UnitOfMeasures> CODE = new SimpleProperty.String<UnitOfMeasures>(UnitOfMeasures.class, "code");
    /**
     * Constraints: Nullable<p>Original property name from the Odata EDM: <b>name</b></p>
     * 
     * @return
     *     The name contained in this {@link VdmEntity}.
     */
    @Nullable
    @ElementName("name")
    private java.lang.String name;
    public final static SimpleProperty.String<UnitOfMeasures> NAME = new SimpleProperty.String<UnitOfMeasures>(UnitOfMeasures.class, "name");

    @Nonnull
    @Override
    public Class<UnitOfMeasures> getType() {
        return UnitOfMeasures.class;
    }

    /**
     * (Key Field) Constraints: Not nullable<p>Original property name from the Odata EDM: <b>code</b></p>
     * 
     * @param code
     *     The code to set.
     */
    public void setCode(
        @Nullable
        final java.lang.String code) {
        rememberChangedField("code", this.code);
        this.code = code;
    }

    /**
     * Constraints: Nullable<p>Original property name from the Odata EDM: <b>name</b></p>
     * 
     * @param name
     *     The name to set.
     */
    public void setName(
        @Nullable
        final java.lang.String name) {
        rememberChangedField("name", this.name);
        this.name = name;
    }

    @Override
    protected java.lang.String getEntityCollection() {
        return "UnitOfMeasures";
    }

    @Nonnull
    @Override
    protected ODataEntityKey getKey() {
        final ODataEntityKey entityKey = super.getKey();
        entityKey.addKeyProperty("code", getCode());
        return entityKey;
    }

    @Nonnull
    @Override
    protected Map<java.lang.String, Object> toMapOfFields() {
        final Map<java.lang.String, Object> values = super.toMapOfFields();
        values.put("code", getCode());
        values.put("name", getName());
        return values;
    }

    @Override
    protected void fromMap(final Map<java.lang.String, Object> inputValues) {
        final Map<java.lang.String, Object> values = Maps.newHashMap(inputValues);
        // simple properties
        {
            if (values.containsKey("code")) {
                final Object value = values.remove("code");
                if ((value == null)||(!value.equals(getCode()))) {
                    setCode(((java.lang.String) value));
                }
            }
            if (values.containsKey("name")) {
                final Object value = values.remove("name");
                if ((value == null)||(!value.equals(getName()))) {
                    setName(((java.lang.String) value));
                }
            }
        }
        // structured properties
        {
        }
        // navigation properties
        {
        }
        super.fromMap(values);
    }

    @Override
    protected java.lang.String getDefaultServicePath() {
        return UnitofMeasurementService.DEFAULT_SERVICE_PATH;
    }

}
