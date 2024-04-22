package com.project.compagnoserver.repo.RegisterPet;

import com.project.compagnoserver.domain.RegisterPet.RegisterPetFaq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisterPetFaqDAO extends JpaRepository<RegisterPetFaq, Integer> {
    // 공개상태(status: Y)인 faq 조회
//    @Query(value = "SELECT * FROM register_pet_faq WHERE rp_faq_status = 'Y'", nativeQuery = true)
//    List<RegisterPetFaq> findByregiFaqStatus (String regiFaqStatus);
}
