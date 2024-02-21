package com.example.btpsd.services;

import com.example.btpsd.commands.LineTypeCommand;
import com.example.btpsd.converters.LineTypeCommandToLineType;
import com.example.btpsd.converters.LineTypeToLineTypeCommand;
import com.example.btpsd.model.LineType;
import com.example.btpsd.repositories.LineTypeRepository;
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
public class LineTypeServiceImpl implements LineTypeService{

    private final LineTypeRepository lineTypeRepository;
    private final LineTypeToLineTypeCommand lineTypeToLineTypeCommand;
    private final LineTypeCommandToLineType lineTypeCommandToLineType;


    @Override
    @Transactional
    public Set<LineTypeCommand> getLineTypeCommands() {

        return StreamSupport.stream(lineTypeRepository.findAll()
                        .spliterator(), false)
                .map(lineTypeToLineTypeCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public LineType findById(Long l) {

        Optional<LineType> lineTypeOptional = lineTypeRepository.findById(l);

        if (!lineTypeOptional.isPresent()) {
            throw new RuntimeException("Line Type Not Found!");
        }

        return lineTypeOptional.get();

    }

    @Override
    public void deleteById(Long idToDelete) {

        lineTypeRepository.deleteById(idToDelete);

    }

    @Override
    @Transactional
    public LineTypeCommand saveLineTypeCommand(LineTypeCommand command) {

        LineType detachedLineType = lineTypeCommandToLineType.convert(command);
        LineType savedLineType = lineTypeRepository.save(detachedLineType);
        log.debug("Saved LineType Id:" + savedLineType.getLineTypeCode());
        return lineTypeToLineTypeCommand.convert(savedLineType);

    }

    @Override
    public LineType updateLineType(LineTypeCommand newLineTypeCommand, Long l) {

        return lineTypeRepository.findById(l).map(oldLineType -> {
            if (newLineTypeCommand.getCode() != oldLineType.getCode())
                oldLineType.setCode(newLineTypeCommand.getCode());
            if (newLineTypeCommand.getBlanketLine() != oldLineType.getBlanketLine())
                oldLineType.setBlanketLine(newLineTypeCommand.getBlanketLine());
            if (newLineTypeCommand.getInternalLine() != oldLineType.getInternalLine())
                oldLineType.setInternalLine(newLineTypeCommand.getInternalLine());
            if (newLineTypeCommand.getStandardLine() != oldLineType.getStandardLine())
                oldLineType.setStandardLine(newLineTypeCommand.getStandardLine());
            if (newLineTypeCommand.getInformatoryLine() != oldLineType.getInformatoryLine())
                oldLineType.setInformatoryLine(newLineTypeCommand.getInformatoryLine());
            if (newLineTypeCommand.getContingencyLine() != oldLineType.getContingencyLine())
                oldLineType.setContingencyLine(newLineTypeCommand.getContingencyLine());
            if (newLineTypeCommand.getAtpQuantity() != oldLineType.getAtpQuantity())
                oldLineType.setAtpQuantity(newLineTypeCommand.getAtpQuantity());
            if (newLineTypeCommand.getEZ() != oldLineType.getEZ())
                oldLineType.setEZ(newLineTypeCommand.getEZ());
            if (newLineTypeCommand.getHZ() != oldLineType.getHZ())
                oldLineType.setHZ(newLineTypeCommand.getHZ());
            if (newLineTypeCommand.getFZ() != oldLineType.getFZ())
                oldLineType.setFZ(newLineTypeCommand.getFZ());
            if (newLineTypeCommand.getIZ() != oldLineType.getIZ())
                oldLineType.setIZ(newLineTypeCommand.getIZ());
            if (newLineTypeCommand.getNZ() != oldLineType.getNZ())
                oldLineType.setNZ(newLineTypeCommand.getNZ());
            if (newLineTypeCommand.getPZ() != oldLineType.getPZ())
                oldLineType.setPZ(newLineTypeCommand.getPZ());
            return lineTypeRepository.save(oldLineType);
        }).orElseThrow(() -> new RuntimeException("Line Type not found"));

    }

    @Override
    @Transactional
    public LineTypeCommand findLineTypeCommandById(Long l) {

        return lineTypeToLineTypeCommand.convert(findById(l));

    }
}
