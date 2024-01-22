package com.example.btpsd.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "excavationModel")
public class ExcavationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
