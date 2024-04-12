package com.project.compagnoserver.repo.ProductBoard;

import com.project.compagnoserver.domain.ProductBoard.ProductBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBoardImageDAO extends JpaRepository<ProductBoardImage, Integer> {
}
