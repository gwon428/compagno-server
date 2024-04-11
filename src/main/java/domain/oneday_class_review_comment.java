package domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity

public class oneday_class_review_comment {

    @Id
    @Column(name = "odc_comment_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odcCommentCode;

    @Column(name = "odc_code")
    private int odcCode;

    @Column(name = "odc_review_code")
    private int odcReviewCode;

    @Column(name = "odc_comment_context")
    private String odcCommentContext;

    @Column(name = "odc_comment_date")
    private Timestamp odcCommentDate;

    @Column(name = "odc_parent_code")
    private int odcParentCode;

    @JoinColumn
    private String userId;

}
