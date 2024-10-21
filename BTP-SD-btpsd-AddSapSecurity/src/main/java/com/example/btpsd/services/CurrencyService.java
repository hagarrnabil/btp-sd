package com.example.btpsd.services;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.model.Currency;

import java.util.Set;

public interface CurrencyService {

    Set<CurrencyCommand> getCurrencyCommands();

    Currency findById(Long l);

    void deleteById(Long idToDelete);

    CurrencyCommand saveCurrencyCommand(CurrencyCommand command);
    Currency updateCurrency(CurrencyCommand newCurrencyCommand, Long l);

    CurrencyCommand findCurrencyCommandById(Long l);

}
