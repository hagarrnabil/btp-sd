package com.example.btpsd.services;


import com.example.btpsd.commands.LineTypeCommand;
import com.example.btpsd.model.LineType;

import java.util.Set;

public interface LineTypeService {

    Set<LineTypeCommand> getLineTypeCommands();

    LineType findById(Long l);

    void deleteById(Long idToDelete);

    LineTypeCommand saveLineTypeCommand(LineTypeCommand command);
    LineType updateLineType(LineTypeCommand newLineTypeCommand, Long l);

    LineTypeCommand findLineTypeCommandById(Long l);

}
