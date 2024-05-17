package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.NoticeBoard.NoticeBoard;
import com.project.compagnoserver.domain.NoticeBoard.NoticeBoardImage;
import com.project.compagnoserver.domain.NoticeBoard.QNoticeBoard;
import com.project.compagnoserver.domain.NoticeBoard.QNoticeBoardImage;
import com.project.compagnoserver.repo.Notice.NoticeBoardDAO;
import com.project.compagnoserver.repo.Notice.NoticeBoardImageDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class NoticeBoardService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private NoticeBoardDAO board;

    @Autowired
    private NoticeBoardImageDAO image;

    private final QNoticeBoard qNoticeBoard = QNoticeBoard.noticeBoard;

    private final QNoticeBoardImage qNoticeBoardImage = QNoticeBoardImage.noticeBoardImage;

    public NoticeBoard viewBoard(int code){
        return board.findById(code).orElse(null);
    }

    public List<NoticeBoardImage> viewImage(int code) {
        return queryFactory.selectFrom(qNoticeBoardImage)
                .where(qNoticeBoardImage.noticeBoard.noticeBoardCode.eq(code))
                .fetch();
    }

    // 게시판 작성
    public NoticeBoard createBoard(NoticeBoard vo) {
        return board.save(vo);
    }

    // 이미지 저장
    public void createImage(NoticeBoardImage vo) {
        image.save(vo);
    }

    // 게시판 삭제
    public void deleteBoard(int code) {
        if(board.existsById(code)) {
            board.deleteById(code);
        }
    }

    // 한개의 게시판 이미지 삭제
    @Transactional
    public void deleteImage(int code) {
        queryFactory.delete(qNoticeBoardImage)
                .where(qNoticeBoardImage.noticeImageCode.eq(code))
                .execute();
    }

    // 댓글 한개 삭제
    public void deleteOneImage(int code) {
        image.deleteById(code);
    }

    // 게시판 수정
    public NoticeBoard updateBoard(NoticeBoard vo) {
        if(board.existsById(vo.getNoticeBoardCode())) {
            return board.save(vo);
        }
        return null;
    }
    // 조회수
    @Transactional
    public void viewCountUp(int code) {
        queryFactory.update(qNoticeBoard)
                .set(qNoticeBoard.noticeBoardViewCount, qNoticeBoard.noticeBoardViewCount.add(1))
                .where(qNoticeBoard.noticeBoardCode.eq(code))
                .execute();
    }

//    public Page<NoticeBoard> searchNoticeBoard(String select, String keyword, Pageable pageable)
}
