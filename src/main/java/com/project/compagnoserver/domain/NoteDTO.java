package com.project.compagnoserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private String title;
    private String content;
    private String sender;
    private String receiver;
    private boolean deletedSender;
    private boolean deletedReceiver;

    private List<MultipartFile> files;
}
