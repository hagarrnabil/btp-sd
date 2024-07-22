package com.example.btpsd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IasUser {

    private String value;
    private String familyName;
    private String givenName;
    private String userName;
}
