package com.project.compagnoserver.domain.Parsing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @DynamicInsert
@Builder @Table(name="location")
public class ContentsLocationParsing {

    @Id @Column(name="location_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationCode;

    @Column(name="location_name")
    private String locationName;

    @Column(name="location_parent_code")
    private int locationParentCode;

    @ManyToOne
    @JoinColumn(name = "location_parent_code", referencedColumnName = "location_code", insertable = false, updatable = false)
    private ContentsLocationParsing parent;
}
