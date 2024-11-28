package com.example.btpsd.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private boolean primary;
    private String value;

    // public EmailDto() {}
    //
    // public EmailDto(boolean primary, String value) {
    // this.primary = primary;
    // this.value = value;
    // }
    //
    // public void setPrimary(boolean primary) {
    // this.primary = primary;
    // }
    //
    // public void setValue(String value) {
    // this.value = value;
    // }
}
