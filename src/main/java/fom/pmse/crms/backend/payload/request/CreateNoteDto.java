package fom.pmse.crms.backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNoteDto {
    private String title;
    private String content;
    private Long partnerId;
}
