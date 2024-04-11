package com.project.compagnoserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Note {

    @Id
    @Column(name="note_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteCode;

    @Column(name="note_title")
    private String noteTitle;

    @Column(name="note_content")
    private String noteContent;

    @Column
    private String sender;

    @Column
    private String receiver;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="note_regi_date")
    private Date noteRegiDate;

    @Column(name="deleted_by_sender")
    private Boolean deletedBySender;  // 기본 false

    @Column(name="deleted_by_receiver")
    private Boolean deletedByReceiver;

    @OneToMany(mappedBy = "noteCode")
    private List<NoteFIle> files;
}
