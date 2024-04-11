package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
public class oneday_class_board_main_image {

    @Id
    @Column(name = "odc_image_code")
    private int odcImageCode;

    @Column(name = "odc_code")
    private int odcCode;

    @Column(name = "odc_main_image")
    private String odcMainImage;

    @JoinColumn
    private int userId;

}
