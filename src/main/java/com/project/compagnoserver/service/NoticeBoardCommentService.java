package com.project.compagnoserver.service;

import com.project.compagnoserver.domain.NoticeBoard.NoticeBoardComment;
import com.project.compagnoserver.domain.NoticeBoard.QNoticeBoardComment;
import com.project.compagnoserver.repo.Notice.NoticeBoardCommentDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class NoticeBoardCommentService {

    @Autowired
    private NoticeBoardCommentDAO dao;

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QNoticeBoardComment qNoticeBoardComment = QNoticeBoardComment.noticeBoardComment;

    // 댓글 작성
    public NoticeBoardComment create(NoticeBoardComment vo) {
        return dao.save(vo);}

    // 댓글 수정
    @Transactional
    public void update(NoticeBoardComment vo) {
        if(dao.existsById(vo.getNoticeCommentCode())) {
            queryFactory.update(qNoticeBoardComment)
                    .set(qNoticeBoardComment.noticeCommentContent, vo.getNoticeCommentContent())
                    .where(qNoticeBoardComment.noticeCommentCode.eq(vo.getNoticeCommentCode()))
                    .execute();
        }
    }

    // 댓글 삭제
    @Transactional
    public void delete(int code) {
        if(dao.existsById(code)) {
            queryFactory.update(qNoticeBoardComment)
                    .set(qNoticeBoardComment.noticeCommentDelete, 'Y')
                    .where(qNoticeBoardComment.noticeCommentCode.eq(code))
                    .execute();
        }
    }

    // 댓글 조회
    public List<NoticeBoardComment> getTopLevelComments(int code) {

        return queryFactory.selectFrom(qNoticeBoardComment)
                .where(qNoticeBoardComment.noticeParentCode.eq(0))
                .where(qNoticeBoardComment.noticeBoard.noticeBoardCode.eq(code))
                .orderBy(qNoticeBoardComment.noticeCommentRegiDate.asc())
                .fetch();
    }

    // 대댓글 조회
    public List<NoticeBoardComment> getBottomComments(int parent, int code) {

        return queryFactory.selectFrom(qNoticeBoardComment)
                .where(qNoticeBoardComment.noticeParentCode.eq(parent))
                .where(qNoticeBoardComment.noticeBoard.noticeBoardCode.eq(code))
                .orderBy(qNoticeBoardComment.noticeCommentRegiDate.asc())
                .fetch();

    }
}
