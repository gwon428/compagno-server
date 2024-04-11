package domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert

public class oneday_class_board_activity_image {

    @Id
    @Column(name = "odc_review_code")
    @GeneratedValue
    private int odcReviewCode;

    @Column(name = "odc_code")
    private int odcCode;

    @Column(name = "odc_active_image")
    private String odcActiveImage;

    @JoinColumn
    private int userId;

}
