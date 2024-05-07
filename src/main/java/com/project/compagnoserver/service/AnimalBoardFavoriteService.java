package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.Animal.AnimalBoardFavorite;
import com.project.compagnoserver.domain.Animal.AnimalBoardFavoriteDTO;
import com.project.compagnoserver.domain.Animal.QAnimalBoard;
import com.project.compagnoserver.domain.Animal.QAnimalBoardFavorite;
import com.project.compagnoserver.repo.Animal.AnimalBoardFavoriteDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AnimalBoardFavoriteService {

    @Autowired
    private AnimalBoardFavoriteDAO favoriteDAO;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final QAnimalBoard qAnimalBoard = QAnimalBoard.animalBoard;
    private final QAnimalBoardFavorite qAnimalBoardFavorite = QAnimalBoardFavorite.animalBoardFavorite;

    // 좋아요 여부 초기값 확인
    public Boolean checkFavorite(int boardCode, String userId){
        AnimalBoardFavorite fav =favoriteDAO.checkFavorite(boardCode, userId);
//        log.info("service fav : " + fav);
        // 찜안했으면 false, 했으면 true
        return  fav == null ? false : true;
    }

    // 좋아요 클릭
    public AnimalBoardFavorite favoriteBoard(AnimalBoardFavorite favorite){
        return favoriteDAO.save(favorite);
    }

    // 좋아요 클릭 해제
    @Transactional
    public void deleteFavorite(AnimalBoardFavoriteDTO dto){
        jpaQueryFactory.delete(qAnimalBoardFavorite)
                        .where(qAnimalBoardFavorite.animalBoard.animalBoardCode.eq(dto.getAnimalBoardCode()))
                                .where(qAnimalBoardFavorite.user.userId.eq(dto.getUserId())).execute();
    }

    // 좋아요 수
    @Transactional
    public void favCount(AnimalBoardFavoriteDTO dto){
        log.info("service boolean : " + dto.isCheckBoolean());
        if(dto.isCheckBoolean()){
            jpaQueryFactory.update(qAnimalBoard)
                    .set(qAnimalBoard.animalBoardFavoriteCount, qAnimalBoard.animalBoardFavoriteCount.add(1))
                    .where(qAnimalBoard.animalBoardCode.eq(dto.getAnimalBoardCode()))
                    .execute();

        }else{
          jpaQueryFactory.update(qAnimalBoard)
                    .set(qAnimalBoard.animalBoardFavoriteCount, qAnimalBoard.animalBoardFavoriteCount.add(-1))
                    .where(qAnimalBoard.animalBoardCode.eq(dto.getAnimalBoardCode()))
                    .execute();
        }

//        if(dto.isCheckBoolean()){
//            favoriteDAO.plusFavCount(dto.getAnimalBoardCode());
//        }else{
//            favoriteDAO.minusFavCount(dto.getAnimalBoardCode());
//        }

    }
}
