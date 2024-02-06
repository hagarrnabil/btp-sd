package com.example.btpsd.controllers;

import com.example.btpsd.commands.MaterialGroupCommand;
import com.example.btpsd.converters.MaterialGroupToMaterialGroupCommand;
import com.example.btpsd.model.MaterialGroup;
import com.example.btpsd.repositories.MaterialGroupRepository;
import com.example.btpsd.services.MaterialGroupService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class MaterialGroupController {

    private final MaterialGroupRepository materialGroupRepository;

    private final MaterialGroupService materialGroupService;

    private final MaterialGroupToMaterialGroupCommand materialGroupToMaterialGroupCommand;

    @GetMapping("/materialgroups")
    Set<MaterialGroupCommand> all() {
        return materialGroupService.getMaterialGroupCommands();
    }

    @GetMapping("/materialgroups/{materialGroupCode}")
    public Optional<MaterialGroupCommand> findByIds(@PathVariable @NotNull Long materialGroupCode) {

        return Optional.ofNullable(materialGroupService.findMaterialGroupCommandById(materialGroupCode));
    }

    @PostMapping("/materialgroups")
    MaterialGroupCommand newMaterialGroupCommand(@RequestBody MaterialGroupCommand newMaterialGroupCommand) {

        MaterialGroupCommand savedCommand = materialGroupService.saveMaterialGroupCommand(newMaterialGroupCommand);
        return savedCommand;

    }

    @DeleteMapping("/materialgroups/{materialGroupCode}")
    void deleteMaterialGroupCommand(@PathVariable Long materialGroupCode) {
        materialGroupService.deleteById(materialGroupCode);
    }

    @PutMapping
    @RequestMapping("/materialgroups/{materialGroupCode}")
    @Transactional
    MaterialGroupCommand updateMaterialGroupCommand(@RequestBody MaterialGroupCommand newMaterialGroupCommand, @PathVariable Long materialGroupCode) {

        MaterialGroupCommand command = materialGroupToMaterialGroupCommand.convert(materialGroupService.updateMaterialGroup(newMaterialGroupCommand, materialGroupCode));
        return command;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/materialgroups/search")
    @ResponseBody
    public List<MaterialGroup> Search(@RequestParam String keyword) {

        return materialGroupRepository.search(keyword);
    }
}
