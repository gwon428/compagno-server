package com.project.compagnoserver.repo;

import com.project.compagnoserver.domain.Product.ProductBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBoardBookmarkDAO extends JpaRepository<ProductBoardBookmark, Integer> {
}
