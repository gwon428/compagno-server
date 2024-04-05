package domain;

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
@Table(name="animal_category")
public class AnimalCategory {

    @Id
    @Column(name = "animal_category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int animalCategoryCode;

    @Column(name = "animal_type")
    private String animalType;
}
