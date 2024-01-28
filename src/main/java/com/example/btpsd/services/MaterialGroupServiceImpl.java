package com.example.btpsd.services;

import com.example.btpsd.commands.MaterialGroupCommand;
import com.example.btpsd.converters.MaterialGroupCommandToMaterialGroup;
import com.example.btpsd.converters.MaterialGroupToMaterialGroupCommand;
import com.example.btpsd.model.MaterialGroup;
import com.example.btpsd.repositories.MaterialGroupRepository;
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
public class MaterialGroupServiceImpl implements MaterialGroupService{

    private final MaterialGroupRepository materialGroupRepository;
    private final MaterialGroupToMaterialGroupCommand materialGroupToMaterialGroupCommand;
    private final MaterialGroupCommandToMaterialGroup materialGroupCommandToMaterialGroup;


    @Override
    @Transactional
    public Set<MaterialGroupCommand> getMaterialGroupCommands() {

        return StreamSupport.stream(materialGroupRepository.findAll()
                        .spliterator(), false)
                .map(materialGroupToMaterialGroupCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public MaterialGroup findById(Long l) {

        Optional<MaterialGroup> materialGroupOptional = materialGroupRepository.findById(l);

        if (!materialGroupOptional.isPresent()) {
            throw new RuntimeException("Material Group Not Found!");
        }

        return materialGroupOptional.get();


    }

    @Override
    public void deleteById(Long idToDelete) {

        materialGroupRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public MaterialGroupCommand saveMaterialGroupCommand(MaterialGroupCommand command) {

        MaterialGroup detachedMaterialGroup = materialGroupCommandToMaterialGroup.convert(command);
        MaterialGroup savedMaterialGroup = materialGroupRepository.save(detachedMaterialGroup);
        log.debug("Saved Material Group Id:" + savedMaterialGroup.getMaterialGroupCode());
        return materialGroupToMaterialGroupCommand.convert(savedMaterialGroup);


    }

    @Override
    public MaterialGroup updateMaterialGroup(MaterialGroupCommand newMaterialGroupCommand, Long l) {

        return materialGroupRepository.findById(l).map(oldMaterialGroup -> {
            if (newMaterialGroupCommand.getCode() != oldMaterialGroup.getCode()) oldMaterialGroup.setCode(newMaterialGroupCommand.getCode());
            if (newMaterialGroupCommand.getDescription() != oldMaterialGroup.getDescription()) oldMaterialGroup.setDescription(newMaterialGroupCommand.getDescription());
            return materialGroupRepository.save(oldMaterialGroup);
        }).orElseThrow(() -> new RuntimeException("Material Group not found"));


    }

    @Override
    @Transactional
    public MaterialGroupCommand findMaterialGroupCommandById(Long l) {

        return materialGroupToMaterialGroupCommand.convert(findById(l));

    }
}
