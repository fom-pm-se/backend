package fom.pmse.crms.backend.payload.response;

import fom.pmse.crms.backend.model.Note;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoteDto extends AuditableDto {
    private Long id;
    private String title;
    private String content;
    private Long partnerId;

    public static NoteDto fromNote(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setTitle(note.getTitle());
        noteDto.setContent(note.getContent());
        noteDto.setPartnerId(note.getPartner().getId());
        noteDto.setCreatedByUsername(note.getCreatedBy());
        noteDto.setUpdatedByUsername(note.getUpdatedByUser());
        noteDto.setCreationTime(note.getCreationTime());
        noteDto.setUpdateTime(note.getUpdateTime());
        return noteDto;
    }
}
