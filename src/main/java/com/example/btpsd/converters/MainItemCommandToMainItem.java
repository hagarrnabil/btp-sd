package com.example.btpsd.converters;

import com.example.btpsd.commands.MainItemCommand;
import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.commands.SubItemCommand;
import com.example.btpsd.model.MainItem;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.model.ServiceNumber;
import com.example.btpsd.model.SubItem;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MainItemCommandToMainItem implements Converter<MainItemCommand, MainItem> {


    private final SubItemCommandToSubItem subItemConverter;

    @Synchronized
    @Nullable
    @Override
    public MainItem convert(MainItemCommand source) {

        if (source == null) {
            return null;
        }

        final MainItem mainItem = new MainItem();
        mainItem.setMainItemCode(source.getMainItemCode());
        mainItem.setUnitOfMeasurementCode(source.getUnitOfMeasurementCode());
        mainItem.setCurrencyCode(source.getCurrencyCode());
        mainItem.setFormulaCode(source.getFormulaCode());
        mainItem.setDescription(source.getDescription());
        mainItem.setQuantity(source.getQuantity());
        mainItem.setProfitMargin(source.getProfitMargin());
        mainItem.setDoNotPrint(source.getDoNotPrint());

        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            mainItem.setServiceNumber(serviceNumber);
            serviceNumber.addMainItem(mainItem);
        }

        if (source.getSubItems() != null && !source.getSubItems().isEmpty()) {
            double totalAmountPerUnitFromSubItems = 0.0;

            for (SubItemCommand subItemCommand : source.getSubItems()) {
                SubItem subItem = subItemConverter.convert(subItemCommand);
                if (subItem != null) {
                    totalAmountPerUnitFromSubItems += subItem.getAmountPerUnit();
                    subItem.setMainItem(mainItem);  // Ensure bi-directional relationship
                    mainItem.addSubItem(subItem);
                }
            }

            mainItem.setAmountPerUnit(totalAmountPerUnitFromSubItems);
        } else {
            // Use the manually entered amountPerUnit if no subItems are present
            mainItem.setAmountPerUnit(source.getAmountPerUnit());
        }

        mainItem.setTotal(mainItem.getQuantity() * mainItem.getAmountPerUnit());
        mainItem.setTotalWithProfit((mainItem.getProfitMargin() / 100) * mainItem.getTotal());
        mainItem.setAmountPerUnitWithProfit((mainItem.getProfitMargin() / 100) * mainItem.getAmountPerUnit());

        return mainItem;
    }
}
