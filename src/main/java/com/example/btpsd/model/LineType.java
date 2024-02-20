package com.example.btpsd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lineType")
public class LineType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lineTypeCode;

    @Column(unique = true, columnDefinition = "char(225)", nullable = false)
    @Length(max = 225)
    private String code;

    private Boolean standardLine;

    private Boolean blanketLine;

    private Boolean contingencyLine;

    private Boolean atpQuantity;

    private Boolean informatoryLine;

    private Boolean internalLine;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "lineType")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    public LineType(String code, Boolean standardLine, Boolean blanketLine, Boolean contingencyLine, Boolean atpQuantity, Boolean informatoryLine, Boolean internalLine,
                    Set<ModelSpecificationsDetails> modelSpecificationsDetails)
    {
        this.code = code;
        this.standardLine = standardLine;
        this.blanketLine = blanketLine;
        this.contingencyLine = contingencyLine;
        this.atpQuantity = atpQuantity;
        this.informatoryLine = informatoryLine;
        this.internalLine = internalLine;
        this.modelSpecificationsDetails = modelSpecificationsDetails;
    }

    public LineType addModelSpecDetails(ModelSpecificationsDetails modelSpecificationsDetails) {
        modelSpecificationsDetails.setLineType(this);
        this.modelSpecificationsDetails.add(modelSpecificationsDetails);
        return this;
    }
}
