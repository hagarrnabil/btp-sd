package com.example.btpsd.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ExecutionOrderSubCommand implements Serializable {

    private Long executionOrderSubCode;
}
