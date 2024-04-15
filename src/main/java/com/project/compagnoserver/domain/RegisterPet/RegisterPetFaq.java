package com.project.compagnoserver.domain.RegisterPet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name="register_pet_faq")
public class RegisterPetFaq {

    @Id
    @Column(name="rp_faq_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regiFaqCode;

    @Column(name="rp_faq_question")
    private String regiFaqQuestion;

    @Column(name = "rp_faq_answer")
    private String regiFaqAnswer;

    @Column(name = "rp_faq_status")
    private String regiFaqStatus;

}
