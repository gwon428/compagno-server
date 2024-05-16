package com.project.compagnoserver.domain.NoticeBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeBoardDTO {
    private int noticeBoardCode;

    private String noticeBoardTitle;

    private String noticeBoardContent;

    private String userId;

    private List<MultipartFile> files;

    private List<String> images;
}
