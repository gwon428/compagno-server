package com.project.compagnoserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name="note_file")
public class NoteFIle {

    @Id
    @Column(name="note_file_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteFileCode;

    @ManyToOne
    @JoinColumn(name="note_code")
    @JsonIgnore
    private Note noteCode;

    @Column(name="note_file_url")
    private String noteFileUrl;
}
