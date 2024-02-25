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

    @Column(columnDefinition="VARCHAR")
    private String nz;

    @Column(columnDefinition="VARCHAR")
    private String pz;

    @Column(columnDefinition="VARCHAR")
    private String ez;

    @Column(columnDefinition="VARCHAR")
    private String fz;

    @Column(columnDefinition="VARCHAR")
    private String hz;

    @Column(columnDefinition="VARCHAR")
    private String iz;

    private String standardLine;

    private String blanketLine;

    private String contingencyLine;

    private String atpQuantity;

    private String informatoryLine;

    private String internalLine;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "lineType")
    @JsonIgnore
    private Set<ModelSpecificationsDetails> modelSpecificationsDetails = new HashSet<>();

    public LineType(String code, String nz, String pz, String ez, String fz, String hz, String iz, String standardLine, String blanketLine, String contingencyLine,
                    String atpQuantity, String informatoryLine, String internalLine, Set<ModelSpecificationsDetails> modelSpecificationsDetails)
    {
        this.code = code;
        this.nz = nz;
        this.pz = pz;
        this.ez = ez;
        this.fz = fz;
        this.hz = hz;
        this.iz = iz;
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
