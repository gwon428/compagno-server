package com.project.compagnoserver.domain.RegisterPet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicInsert
@Table(name="register_pet_board")
public class RegisterPet {

    @Id
    @Column(name="rp_board_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regiBoardCode;

    @Column(name="rp_inst_name")
    private String regiInstName;

    @Column(name="rp_inst_addr")
    private String regiInstAddr;

    @Column(name="rp_inst_owner")
    private String regiInstOwner;

    @Column(name="rp_inst_phone")
    private String regiInstPhone;

}
