package com.project.compagnoserver.repo.RegisterPet;

import com.project.compagnoserver.domain.RegisterPet.RegisterPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterPetDAO extends JpaRepository<RegisterPet, Integer> {
}
