package domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert

public class oneday_class_review {

    @Id
    @Column(name = "odc_review_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odcReviewCode;

    @Column(name = "odc_code")
    private int odcCode;

    @Column(name = "odc_review_title")
    private String odcReviewTitle;

    @Column(name = "odc_review_content")
    private String odcReviewContent;

    @Column(name = "odc_review_date")
    private Timestamp odcReviewDate;

    @Column(name = "odc_review_count")
    private int odcReviewCount;

    @JoinColumn
    private String userId;
}
