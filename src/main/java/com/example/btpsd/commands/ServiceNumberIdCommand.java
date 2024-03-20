package com.example.btpsd.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
@NoArgsConstructor
public class ServiceNumberIdCommand implements Serializable {

    private Long serviceNumberCode;

    private Long noServiceNumber;

}
