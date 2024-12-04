package com.example.btpsd.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

@RequiredArgsConstructor
@RestController
public class SalesOrderController {

    @GetMapping("/salesorder")
    @ResponseBody
    public String All() throws JSONException, IOException, URISyntaxException {


        JSONObject jsonFromURL = new JSONObject(IOUtils.toString(new URL("http://localhost:8080/salesordercloud"), String.valueOf(Charset.forName("UTF-8"))));
        JSONArray jsonObjectUnits = jsonFromURL.getJSONObject("d").getJSONArray("results");
        JSONArray newJson = new JSONArray();

        for (int index = 0, size = jsonObjectUnits.length(); index < size; index++) {

            JSONObject objectInArray = jsonObjectUnits.getJSONObject(index);
            String[] elementNames = JSONObject.getNames(objectInArray);
            for (String elementName : elementNames) {

                if (elementName.equals("SalesOrderType") || elementName.equals("SalesOrderTypeInternalCode") || elementName.equals("SalesOrganization") || elementName.equals("DistributionChannel") ||
                        elementName.equals("OrganizationDivision") || elementName.equals("SalesGroup") || elementName.equals("SalesOffice") || elementName.equals("SalesDistrict") || elementName.equals("CreatedByUser")
                || elementName.equals("LastChangeDate") || elementName.equals("SenderBusinessSystemName") || elementName.equals("ExternalDocumentID") || elementName.equals("LastChangeDateTime") || elementName.equals("ExternalDocLastChangeDateTime")
                || elementName.equals("PurchaseOrderByCustomer") || elementName.equals("PurchaseOrderByShipToParty") || elementName.equals("CustomerPurchaseOrderType") || elementName.equals("CustomerPurchaseOrderDate")
                || elementName.equals("SalesOrderDate") || elementName.contains("Total") || elementName.contains("Overall") || elementName.contains("SDDocument") || elementName.contains("PricingDate") ||
                elementName.contains("to_") || elementName.equals("RequestedDeliveryDate") || elementName.contains("Shipping") || elementName.contains("CompleteDeliveryIsDefined") || elementName.contains("Incoterms") ||
                elementName.contains("Customer") || elementName.contains("PaymentMethod") || elementName.equals("FixedValueDate") || elementName.contains("AssignmentReference") || elementName.contains("Accounting") ||
                elementName.contains("Additional") || elementName.equals("SlsDocIsRlvtForProofOfDeliv") || elementName.contains("Country") || elementName.equals("SalesOrderApprovalReason") || elementName.contains("Price") ||
                elementName.equals("SalesDocApprovalStatus") || elementName.contains("ContractAccount") ||  elementName.contains("ServicesRenderedDate") || elementName.contains("Billing") || elementName.equals("__metadata") || elementName.contains("Delivery"))

                {

                    objectInArray.remove(elementName);

                }

            }
            newJson.put(objectInArray);
        }

        // saving into db

//        for (int index = 0; index < newJson.length(); index++)
//        {
//            UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
//            JSONObject objectInsideArray = newJson.getJSONObject(index);
//            String[] elementNames = JSONObject.getNames(objectInsideArray);
//            for (String elementName : elementNames) {
//                if (elementName.equals("UnitOfMeasureSAPCode")) {
//                    unitOfMeasurement.setUnitOfMeasureSAPCode(objectInsideArray.getString("UnitOfMeasureSAPCode"));
//                } else if (elementName.equals("UnitOfMeasureLongName")) {
//                    unitOfMeasurement.setUnitOfMeasureLongName(objectInsideArray.getString("UnitOfMeasureLongName"));
//                }
//                else {
//                    unitOfMeasurement.setUnitOfMeasureName(objectInsideArray.getString("UnitOfMeasureName"));
//                }
//                index++;
//                unitOfMeasurementRepository.save(unitOfMeasurement);
//            }
//        }
        return newJson.toString();
//        return unitOfMeasurementRepository.findAll();

    }
}
