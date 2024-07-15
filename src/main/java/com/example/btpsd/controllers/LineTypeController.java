package com.example.btpsd.controllers;

import com.example.btpsd.commands.LineTypeCommand;
import com.example.btpsd.converters.LineTypeToLineTypeCommand;
import com.example.btpsd.model.LineType;
import com.example.btpsd.repositories.LineTypeRepository;
import com.example.btpsd.services.LineTypeService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class LineTypeController {

    private final LineTypeRepository lineTypeRepository;

    private final LineTypeService lineTypeService;

    private final LineTypeToLineTypeCommand lineTypeToLineTypeCommand;

    @GetMapping("/linetypes")
    Set<LineTypeCommand> all() {
        return lineTypeService.getLineTypeCommands();
    }

    @GetMapping("/linetypes/{lineTypeCode}")
    public Optional<LineTypeCommand> findByIds(@PathVariable @NotNull Long lineTypeCode) {

        return Optional.ofNullable(lineTypeService.findLineTypeCommandById(lineTypeCode));
    }

    @PostMapping("/linetypes")
    LineTypeCommand newLineTypeCommand(@RequestBody LineTypeCommand newLineTypeommand) {

        LineTypeCommand savedCommand = lineTypeService.saveLineTypeCommand(newLineTypeommand);
        return savedCommand;

    }

    @DeleteMapping("/linetypes/{lineTypeCode}")
    void deleteLineTypeCommand(@PathVariable Long lineTypeCode) {
        lineTypeService.deleteById(lineTypeCode);
    }

    @PatchMapping
    @RequestMapping("/linetypes/{lineTypeCode}")
    @Transactional
    LineTypeCommand updateLineTypeCommand(@RequestBody LineTypeCommand newLineTypeCommand, @PathVariable Long lineTypeCode) {

        LineTypeCommand command = lineTypeToLineTypeCommand.convert(lineTypeService.updateLineType(newLineTypeCommand, lineTypeCode));
        return command;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/linetypes/search")
    @ResponseBody
    public List<LineType> Search(@RequestParam String keyword) {

        return lineTypeRepository.search(keyword);
    }
}
