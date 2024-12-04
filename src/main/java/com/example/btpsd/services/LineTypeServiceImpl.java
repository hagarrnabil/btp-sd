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
            if (newLineTypeCommand.getDescription() != oldLineType.getDescription())
                oldLineType.setDescription(newLineTypeCommand.getDescription());
            return lineTypeRepository.save(oldLineType);
        }).orElseThrow(() -> new RuntimeException("Line Type not found"));

    }

    @Override
    @Transactional
    public LineTypeCommand findLineTypeCommandById(Long l) {

        return lineTypeToLineTypeCommand.convert(findById(l));

    }
}
