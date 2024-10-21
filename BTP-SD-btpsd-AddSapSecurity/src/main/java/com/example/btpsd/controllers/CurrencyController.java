package com.example.btpsd.controllers;

import com.example.btpsd.commands.CurrencyCommand;
import com.example.btpsd.converters.CurrencyToCurrencyCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.repositories.CurrencyRepository;
import com.example.btpsd.services.CurrencyService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class CurrencyController {

    private final CurrencyRepository currencyRepository;

    private final CurrencyService currencyService;

    private final CurrencyToCurrencyCommand currencyToCurrencyCommand;

    @GetMapping("/currencies")
    Set<CurrencyCommand> all() {
        return currencyService.getCurrencyCommands();
    }

    @GetMapping("/currencies/{currencyCode}")
    public Optional<CurrencyCommand> findByIds(@PathVariable @NotNull Long currencyCode) {

        return Optional.ofNullable(currencyService.findCurrencyCommandById(currencyCode));
    }

    @PostMapping("/currencies")
    CurrencyCommand newCurrencyCommand(@RequestBody CurrencyCommand newCurrencyCommand) {

        CurrencyCommand savedCommand = currencyService.saveCurrencyCommand(newCurrencyCommand);
        return savedCommand;

    }

    @DeleteMapping("/currencies/{currencyCode}")
    void deleteCurrencyCommand(@PathVariable Long currencyCode) {
        currencyService.deleteById(currencyCode);
    }

    @PatchMapping
    @RequestMapping("/currencies/{currencyCode}")
    @Transactional
    CurrencyCommand updateCurrencyCommand(@RequestBody CurrencyCommand newCurrencyCommand, @PathVariable Long currencyCode) {

        CurrencyCommand command = currencyToCurrencyCommand.convert(currencyService.updateCurrency(newCurrencyCommand, currencyCode));
        return command;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/currencies/search")
    @ResponseBody
    public List<Currency> Search(@RequestParam String keyword) {

        return currencyRepository.search(keyword);
    }
}
