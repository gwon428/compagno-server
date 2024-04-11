package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.RegisterPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterPetDAO extends JpaRepository<RegisterPet, Integer> {
}
