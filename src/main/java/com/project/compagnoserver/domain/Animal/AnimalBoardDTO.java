package com.project.compagnoserver.domain.Animal;

import com.project.compagnoserver.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalBoardDTO {

//    private List<MultipartFile> files;
    private UserDTO user; // 유저정보
    private String animalMainImage;
    private String animalBoardTitle;
    private String animalBoardContent;
    private int animalBoardView;
    private Date animalBoardDate;
    private int animalBoardCode;
    private int animalCategoryCode; // 게시글 작성시 코드 받아오기
    private AnimalCategory animalCategory; // 게시글 볼때(detail/edit) 정보 보내기

    // 자유게시판 글쓰기 DTO
}
