package com.example.btpsd.converters;

import com.example.btpsd.commands.MainItemCommand;
import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
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
        mainItem.setQuantity(source.getQuantity());
        mainItem.setAmountPerUnit(source.getAmountPerUnit());
        mainItem.setTotal(source.getQuantity() * source.getAmountPerUnit());
        mainItem.setProfitMargin(source.getProfitMargin());
        mainItem.setTotalWithProfit( (mainItem.getProfitMargin()/100) * mainItem.getTotal() );
        if (source.getServiceNumberCode() != null) {
            ServiceNumber serviceNumber = new ServiceNumber();
            serviceNumber.setServiceNumberCode(source.getServiceNumberCode());
            mainItem.setServiceNumber(serviceNumber);
            serviceNumber.addMainItem(mainItem);
        }
        if (source.getSubItems() != null && !source.getSubItems().isEmpty()) {
            source.getSubItems().forEach(subItemCommand -> {
                SubItem subItem = subItemConverter.convert(subItemCommand);
                if (subItem != null) {
                    subItem.setMainItem(mainItem);  // Ensure bi-directional relationship
                    mainItem.addSubItem(subItem);
                }
            });
        }
        return mainItem;
    }
}
