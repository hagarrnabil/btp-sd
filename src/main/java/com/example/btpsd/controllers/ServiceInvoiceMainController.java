package com.example.btpsd.controllers;

import com.example.btpsd.commands.ExecutionOrderMainCommand;
import com.example.btpsd.commands.ServiceInvoiceMainCommand;
import com.example.btpsd.converters.ExecutionOrderMainToExecutionOrderMainCommand;
import com.example.btpsd.converters.ServiceInvoiceToServiceInvoiceCommand;
import com.example.btpsd.services.ExecutionOrderMainService;
import com.example.btpsd.services.ServiceInvoiceMainService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ServiceInvoiceMainController {

    private final ServiceInvoiceMainService serviceInvoiceMainService;

    private final ServiceInvoiceToServiceInvoiceCommand serviceInvoiceToServiceInvoiceCommand;

    @GetMapping("/serviceinvoice")
    Set<ServiceInvoiceMainCommand> all() {
        return serviceInvoiceMainService.getServiceInvoiceMainCommands();
    }

    @GetMapping("/serviceinvoice/{serviceInvoiceCode}")
    public Optional<ServiceInvoiceMainCommand> findByIds(@PathVariable @NotNull Long serviceInvoiceCode) {

        return Optional.ofNullable(serviceInvoiceMainService.findServiceInvoiceMainCommandById(serviceInvoiceCode));
    }

    @PostMapping("/serviceinvoice")
    ServiceInvoiceMainCommand newServiceInvoiceCommand(@RequestBody ServiceInvoiceMainCommand newServiceInvoiceCommand) {

        ServiceInvoiceMainCommand savedCommand = serviceInvoiceMainService.saveServiceInvoiceMainCommand(newServiceInvoiceCommand);
        return savedCommand;

    }

    @DeleteMapping("/serviceinvoice/{serviceInvoiceCode}")
    void deleteServiceInvoiceCommand(@PathVariable Long serviceInvoiceCode) {
        serviceInvoiceMainService.deleteById(serviceInvoiceCode);
    }

    @PatchMapping
    @RequestMapping("/serviceinvoice/{serviceInvoiceCode}")
    @Transactional
    ServiceInvoiceMainCommand updateServiceInvoiceCommand(@RequestBody ServiceInvoiceMainCommand newServiceInvoiceCommand, @PathVariable Long serviceInvoiceCode) {

        ServiceInvoiceMainCommand command = serviceInvoiceToServiceInvoiceCommand.convert(serviceInvoiceMainService.updateServiceInvoiceMain(newServiceInvoiceCommand, serviceInvoiceCode));
        return command;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/mainitems/search")
//    @ResponseBody
//    public List<InvoiceMainItem> Search(@RequestParam String keyword) {
//
//        return invoiceMainItemRepository.search(keyword);
//    }

}
