package domain;

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
@Table(name="product_board_image")
public class ProductBoardImage {

    @Id
    @Column(name="product_image_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productImageCode;

    @Column(name="product_image")
    private String productImage;

    @ManyToOne
    @JoinColumn(name="product_board_code")
    @JsonIgnore
    private int productBoardCode;

}
