package com.example.btpsd.commands;

import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@NoArgsConstructor
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ServiceNumberIdCommand implements Serializable {

    private Long serviceNumberCode;

    private Long noServiceNumber;

}
