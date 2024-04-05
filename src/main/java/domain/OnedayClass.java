package domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.security.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
public class OnedayClass {

    @Id
    @Column(name = "odc_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odcNum;

    @Column(name = "odc_title")
    private String odcTitle;

    @Column(name = "odc_content")
    private String odcContent;

    @Column(name ="odc_accompaying")
    private int adcAccompaying;

    @Column(name = "odc_regi_date")
    private Timestamp odcRegiDate;

    @Column(name = "odc_start_date")
    private Date odcStartDate;

    @Column(name = "odc_last_date")
    private Date odcLastDate;

//    @Column(name = "user_code")
//    private User user;


}
