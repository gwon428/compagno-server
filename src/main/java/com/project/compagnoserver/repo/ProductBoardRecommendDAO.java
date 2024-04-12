package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.Product.ProductBoardRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBoardRecommendDAO extends JpaRepository<ProductBoardRecommend, Integer> {
}
