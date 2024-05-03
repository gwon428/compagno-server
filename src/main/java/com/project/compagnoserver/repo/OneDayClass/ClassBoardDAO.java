package com.project.compagnoserver.repo.OneDayClass;

import com.project.compagnoserver.domain.OneDayClass.ClassBoard;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassBoardDAO extends JpaRepository<ClassBoard, Integer> {

}
