package com.example.btpsd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ServiceNumberId implements Serializable {

    private Long serviceNumberCode;

    private Long noServiceNumber;
}
