package fom.pmse.crms.backend.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorDto {
    HttpStatus status;
    String message;
    String details;
    LocalDateTime timestamp;
}
