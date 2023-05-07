package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.request.CreateNoteDto;
import fom.pmse.crms.backend.payload.request.UpdateNoteDto;
import fom.pmse.crms.backend.payload.response.NoteDto;
import fom.pmse.crms.backend.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get all notes for a partner")
    @ApiResponse(responseCode = "200", description = "Notes found")
    @ApiResponse(responseCode = "400", description = "Partner not found")
    public ResponseEntity<List<NoteDto>> getNotesForPartner(@PathVariable Long partnerId) {
        return ResponseEntity.ok(noteService.findAllNotesByPartnerId(partnerId));
    }

    @PostMapping
    @Operation(summary = "Create a note for a partner")
    @ApiResponse(responseCode = "200", description = "Note created")
    @ApiResponse(responseCode = "400", description = "Partner not found")
    public ResponseEntity<NoteDto> createNoteForPartner(@RequestBody CreateNoteDto note) {
        return ResponseEntity.ok(NoteDto.fromNote(noteService.createNoteForPartner(note)));
    }

    @PutMapping
    @Operation(summary = "Update a note")
    @ApiResponse(responseCode = "200", description = "Note updated")
    @ApiResponse(responseCode = "400", description = "Note not found")
    public ResponseEntity<NoteDto> updateNote(@RequestBody UpdateNoteDto noteDto) {
        return ResponseEntity.ok(noteService.updateNote(noteDto));
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "Delete a note")
    @ApiResponse(responseCode = "200", description = "Note deleted")
    @ApiResponse(responseCode = "400", description = "Note not found")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity.ok().build();
    }
}
