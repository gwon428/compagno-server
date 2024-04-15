package com.project.compagnoserver.repo.ProductBoard;

import com.project.compagnoserver.domain.ProductBoard.ProductBoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBoardBookmarkDAO extends JpaRepository<ProductBoardBookmark, Integer> {
}
