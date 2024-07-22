package com.example.btpsd.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "executionOrderSub")
public class ExecutionOrderSub implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long executionOrderSubCode;


}
