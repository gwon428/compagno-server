package com.project.compagnoserver.domain.Note;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
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

    @Column(name="note_regi_date")
    private Timestamp noteRegiDate;

    @Column(name="deleted_by_sender")
    private Boolean deletedBySender;  // 기본 false

    @Column(name="deleted_by_receiver")
    private Boolean deletedByReceiver;

    @Column(name="star_sender")
    private Boolean starSender;

    @Column(name="star_receiver")
    private Boolean starReceiver;

    @OneToMany(mappedBy = "noteCode", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NoteFIle> files;
}
