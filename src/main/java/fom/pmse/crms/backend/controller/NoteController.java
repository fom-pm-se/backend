package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.request.CreateNoteDto;
import fom.pmse.crms.backend.payload.request.UpdateNoteDto;
import fom.pmse.crms.backend.payload.response.NoteDto;
import fom.pmse.crms.backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    @GetMapping("/{partnerId}")
    public ResponseEntity<List<NoteDto>> getNotesForPartner(@PathVariable Long partnerId) {
        return ResponseEntity.ok(noteService.findAllNotesByPartnerId(partnerId));
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNoteForPartner(@RequestBody CreateNoteDto note) {
        return ResponseEntity.ok(NoteDto.fromNote(noteService.createNoteForPartner(note)));
    }

    @PutMapping
    public ResponseEntity<NoteDto> updateNote(@RequestBody UpdateNoteDto noteDto) {
        return ResponseEntity.ok(noteService.updateNote(noteDto));
    }
}
