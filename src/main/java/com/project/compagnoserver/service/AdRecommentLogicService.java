package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AdRecommendLogic;
import com.project.compagnoserver.domain.Animal.QAdRecommendLogic;
import com.project.compagnoserver.domain.user.User;
import com.project.compagnoserver.repo.Animal.AdRecommendLogicDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdRecommentLogicService {
    @Autowired
    private AdRecommendLogicDAO logicDAO;

    @Autowired
    private JPAQueryFactory queryFactory;
    private final QAdRecommendLogic qAdRecommendLogic = QAdRecommendLogic.adRecommendLogic;
    public List<AdRecommendLogic> getCurrentPoint(String userId){
        return queryFactory.selectFrom(qAdRecommendLogic)
                .where(qAdRecommendLogic.user.userId.eq(userId))
                .fetch();
    }
}
