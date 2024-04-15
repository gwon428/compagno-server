package com.project.compagnoserver.domain.NoticeBoard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name="notice_board_image")
public class NoticeBoardImage {
    @Id
    @Column(name="notice_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeImageCode;

    @Column(name="notice_image")
    private String noticeImage;

    @ManyToOne
    @JoinColumn(name="notice_board_code")
    @JsonIgnore
    private NoticeBoard noticeBoard;
}
