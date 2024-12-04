package com.example.btpsd.controllers;

import com.example.btpsd.commands.ModelSpecificationsCommand;
import com.example.btpsd.converters.ModelSpecificationsToModelSpecificationsCommand;
import com.example.btpsd.model.Currency;
import com.example.btpsd.model.ModelSpecifications;
import com.example.btpsd.repositories.ModelSpecificationRepository;
import com.example.btpsd.services.ModelSpecsService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ModelSpecsController {

    private final ModelSpecificationRepository modelSpecificationRepository;

    private final ModelSpecsService modelSpecsService;

    private final ModelSpecificationsToModelSpecificationsCommand modelSpecificationsToModelSpecificationsCommand;

    @GetMapping("/modelspecs")
    Set<ModelSpecificationsCommand> all() {
        return modelSpecsService.getModelSpecsCommands();
    }

    @GetMapping("/modelspecs/{modelSpecCode}")
    public Optional<ModelSpecificationsCommand> findByIds(@PathVariable @NotNull Long modelSpecCode) {

        return Optional.ofNullable(modelSpecsService.findModelSpecsCommandById(modelSpecCode));

    }

    @PostMapping("/modelspecs")
    ModelSpecificationsCommand newModelSpecsCommand(@RequestBody ModelSpecificationsCommand newModelSpecsCommand) {

        ModelSpecificationsCommand savedCommand = modelSpecsService.saveModelSpecsCommand(newModelSpecsCommand);
        return savedCommand;

    }

    @DeleteMapping("/modelspecs/{modelSpecCode}")
    void deleteModelSpecsCommand(@PathVariable Long modelSpecCode) {
        modelSpecsService.deleteById(modelSpecCode);
    }


    @PatchMapping
    @RequestMapping("/modelspecs/{modelSpecCode}")
    @Transactional
    ModelSpecificationsCommand updateModelSpecsCommand(@RequestBody ModelSpecifications newModelSpecs, @PathVariable Long modelSpecCode){

        ModelSpecificationsCommand command = modelSpecificationsToModelSpecificationsCommand.convert(modelSpecsService.updateModelSpecs(newModelSpecs,modelSpecCode));
        return command;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/modelspecs/search")
    @ResponseBody
    public List<ModelSpecifications> Search(@RequestParam String keyword) {

        return modelSpecificationRepository.search(keyword);
    }

}
