package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
public class oneday_class_board {

    // 원데이 클래스 등록시 코드
    @Id
    @Column(name = "odc_code")
    private  int odcCode;

    // 원데이 클래스 제목
    @Column(name = "odc_title")
    private String odcTitle;

    // 원데이 클래스 내용
    @Column(name = "odc_content")
    private String odcContent ;

    // 원데이 클래스 애완동물 동반가능 여부
    @Column(name = "odc_accompaying")
    private char odcAccompaying;

    // 원데이 클래스 등록 날짜
    @Column(name = "odc_regi_date")
    private Timestamp odcRegiDate;

    // 원데이 클래스 시작 날짜
    @Column(name = "odc_start_date")
    private Date odcStartDate;

    // 원데이 클래스 마지막 날짜
    @Column(name = "odc_last_date")
    private Date odcLastDate;

    // 사용자 ID
    @JoinColumn
    private String userId;

}
