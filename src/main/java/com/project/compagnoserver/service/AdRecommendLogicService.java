package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AdRecommendLogic;
import com.project.compagnoserver.domain.Animal.AdRecommendLogicDTO;
import com.project.compagnoserver.domain.Animal.AdTargetDTO;
import com.project.compagnoserver.domain.Animal.QAdRecommendLogic;
import com.project.compagnoserver.domain.ProductBoard.ProductBoard;
import com.project.compagnoserver.domain.ProductBoard.QProductBoard;
import com.project.compagnoserver.repo.Animal.AdRecommendLogicDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AdRecommendLogicService {
    @Autowired
    private AdRecommendLogicDAO logicDAO;

    @Autowired
    private JPAQueryFactory queryFactory;
    private final QAdRecommendLogic qAdRecommendLogic = QAdRecommendLogic.adRecommendLogic;
    private final QProductBoard qProductBoard =QProductBoard.productBoard;

    // 현재 포인트 가져오기
    public List<AdRecommendLogic> getCurrentPoint(String userId){
//        log.info("point id : " + userId);
        return queryFactory.selectFrom(qAdRecommendLogic)
                .where(qAdRecommendLogic.user.userId.eq(userId))
                .fetch();
    }

    // 상품 가져오기
    public List<ProductBoard> getReviews(){
        return queryFactory.selectFrom(qProductBoard).fetch();
    }

    // update : inputValue(x_value) / totalScore
    // Detail 클릭시 포인트 증가
    @Transactional
    public void addPoint(AdTargetDTO target, Double response, Double inputValue){
        queryFactory.update(qAdRecommendLogic)
                .set(qAdRecommendLogic.totalScore, response)
                .set(qAdRecommendLogic.inputValue, inputValue)
                .where(qAdRecommendLogic.animalCategory.animalCategoryCode.eq(target.getAnimalCategory().getAnimalCategoryCode()))
                .where(qAdRecommendLogic.user.userId.eq(target.getUser().getUserId()))
                .execute();
    }
    // Detail 클릭시 포인트 감소
    @Transactional
    public void delPoint(AdTargetDTO exception, Double response, Double inputValue){
//        for(AdRecommendLogic exception : exceptions){
            queryFactory.update(qAdRecommendLogic)
                    .set(qAdRecommendLogic.totalScore, response)
                    .set(qAdRecommendLogic.inputValue, inputValue)
                    .where(qAdRecommendLogic.animalCategory.animalCategoryCode.eq(exception.getAnimalCategory().getAnimalCategoryCode()))
                    .where(qAdRecommendLogic.user.userId.eq(exception.getUser().getUserId()))
                    .execute();
//        }
    }
}
