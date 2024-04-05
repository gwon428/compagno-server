package repo;

import com.querydsl.core.BooleanBuilder;
import domain.OnedayClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OnedayClassDAO extends JpaRepository<OnedayClass, Integer> {
    // 특정 카테고리의 모든 상품 조회 !!
    // 어떤 컬럼을 가저올지 ??
    @Query(value ="SELECT * FROM onedayclass WHERE cate_code= :code" , nativeQuery = true)
    // 명은 아무거나가능 !
    Page<OnedayClass> findByCateCode(@Param("code") Integer code, Pageable pageable);
}
