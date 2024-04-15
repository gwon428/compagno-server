package com.project.compagnoserver.domain.OneDayClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassBoardDTO {

    private int odcCode;
    private String odcTitile;
    private int odcContent;
    private MultipartFile file;
    private int cateCode;
}
