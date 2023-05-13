package fom.pmse.crms.backend.payload.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateNoteDto {
    private Long id;
    private String title;
    private String content;
}
