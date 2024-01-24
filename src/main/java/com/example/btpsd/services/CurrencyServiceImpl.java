package com.example.btpsd.services;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.converters.CurrencyCommandToCurrency;
import com.example.btpsd.converters.CurrencyToCurrencyCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.repositories.CurrencyRepository;
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
public class CurrencyServiceImpl implements CurrencyService{

    private final CurrencyRepository currencyRepository;
    private final CurrencyToCurrencyCommand currencyToCurrencyCommand;
    private final CurrencyCommandToCurrency currencyCommandToCurrency;

    @Override
    @Transactional
    public Set<CurrencyCommand> getCurrencyCommands() {

        return StreamSupport.stream(currencyRepository.findAll()
                        .spliterator(), false)
                .map(currencyToCurrencyCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public Currency findById(Long l) {

        Optional<Currency> currencyOptional = currencyRepository.findById(l);

        if (!currencyOptional.isPresent()) {
            throw new RuntimeException("Currency Not Found!");
        }

        return currencyOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        currencyRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public CurrencyCommand saveCurrencyCommand(CurrencyCommand command) {

        Currency detachedCurrency = currencyCommandToCurrency.convert(command);
        Currency savedCurrency = currencyRepository.save(detachedCurrency);
        log.debug("Saved Currency Id:" + savedCurrency.getCurrency());
        return currencyToCurrencyCommand.convert(savedCurrency);

    }

    @Override
    public Currency updateCurrency(CurrencyCommand newCurrencyCommand, Long l) {

        return currencyRepository.findById(l).map(oldCurrency -> {
            if (newCurrencyCommand.getCode() != oldCurrency.getCode()) oldCurrency.setCode(newCurrencyCommand.getCode());
            if (newCurrencyCommand.getDescription() != oldCurrency.getDescription()) oldCurrency.setDescription(newCurrencyCommand.getDescription());
            return currencyRepository.save(oldCurrency);
        }).orElseThrow(() -> new RuntimeException("Currency not found"));


    }

    @Override
    @Transactional
    public CurrencyCommand findCurrencyCommandById(Long l) {

        return currencyToCurrencyCommand.convert(findById(l));

    }
}
