package com.example.btpsd.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "modelServiceSpecification")
public class ModelServiceSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String modelServSpec;

    @Builder.Default
    private Boolean blockingIndicator = true;

    @Builder.Default
    private Boolean serviceSelection = true;

    @NonNull
    private String description;

    private String searchItem;

}
