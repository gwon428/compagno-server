package com.project.compagnoserver.domain.Note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private String noteTitle;
    private String noteContent;
    private String sender;
    private String receiver;
    private boolean deletedSender;
    private boolean deletedReceiver;
    private String noteRegiDate;
    private boolean star;
    private List<MultipartFile> files;
}
