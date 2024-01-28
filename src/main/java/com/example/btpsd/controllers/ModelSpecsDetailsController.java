package com.example.btpsd.controllers;

import com.example.btpsd.commands.ModelSpecificationsDetailsCommand;
import com.example.btpsd.converters.ModelSpecDetailsToModelSpecDetailsCommand;
import com.example.btpsd.model.ModelSpecificationsDetails;
import com.example.btpsd.services.ModelSpecsDetailsService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ModelSpecsDetailsController {

    private final ModelSpecsDetailsService modelSpecsDetailsService;
    private final ModelSpecDetailsToModelSpecDetailsCommand modelSpecDetailsToModelSpecDetailsCommand;

    @GetMapping("/modelspecdetails")
    Set<ModelSpecificationsDetailsCommand> all() {
        return modelSpecsDetailsService.getModelSpecDetailsCommands();
    }

    @GetMapping("/modelspecdetails/{modelSpecDetails}")
    public Optional<ModelSpecificationsDetailsCommand> findByIds(@PathVariable @NotNull Long modelSpecDetails) {

        return Optional.ofNullable(modelSpecsDetailsService.findModelSpecDetailsCommandById(modelSpecDetails));

    }

    @PostMapping("/modelspecdetails")
    ModelSpecificationsDetailsCommand newModelSpecDetailsCommand(@RequestBody ModelSpecificationsDetailsCommand newModelSpecDetailsCommand) {

        ModelSpecificationsDetailsCommand savedCommand = modelSpecsDetailsService.saveModelSpecDetailsCommand(newModelSpecDetailsCommand);
        return savedCommand;

    }

    @DeleteMapping("/modelspecdetails/{modelSpecDetails}")
    void deleteModelSpecDetailsCommand(@PathVariable Long modelSpecDetails) {
        modelSpecsDetailsService.deleteById(modelSpecDetails);
    }


    @PutMapping
    @RequestMapping("/modelspecdetails/{modelSpecDetails}")
    @Transactional
    ModelSpecificationsDetailsCommand updateModelSpecDetailsCommand(@RequestBody ModelSpecificationsDetails newModelSpecDetails, @PathVariable Long modelSpecDetails){

        ModelSpecificationsDetailsCommand command = modelSpecDetailsToModelSpecDetailsCommand.convert(modelSpecsDetailsService.updateModelSpecDetails(newModelSpecDetails,modelSpecDetails));
        return command;
    }

}
