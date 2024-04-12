package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.Product.ProductBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBoardImageDAO extends JpaRepository<ProductBoardImage, Integer> {
}
