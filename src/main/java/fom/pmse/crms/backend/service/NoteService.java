package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.model.Note;
import fom.pmse.crms.backend.model.Partner;
import fom.pmse.crms.backend.payload.request.CreateNoteDto;
import fom.pmse.crms.backend.payload.request.UpdateNoteDto;
import fom.pmse.crms.backend.payload.response.NoteDto;
import fom.pmse.crms.backend.repository.NoteRepository;
import fom.pmse.crms.backend.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;
    private final PartnerRepository partnerRepository;


    public Note createNoteForPartner(CreateNoteDto note) {
        Partner partner = partnerRepository.findById(note.getPartnerId())
                .orElseThrow(() -> {
                    log.error("Partner with ID {} does not exist", note.getPartnerId());
                    return new IllegalArgumentException("Partner mit dieser ID existiert nicht");
                });
        Note newNote = Note.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .partner(partner)
                .build();
        return noteRepository.save(newNote);
    }

    public NoteDto updateNote(UpdateNoteDto note) {
        Note noteToUpdate = noteRepository.findById(note.getId())
                .orElseThrow(() -> {
                    log.error("Note with ID {} does not exist", note.getId());
                    return new IllegalArgumentException("Notiz mit dieser ID existiert nicht");
                });
        noteToUpdate.setTitle(note.getTitle());
        noteToUpdate.setContent(note.getContent());
        return NoteDto.fromNote(
                noteRepository.save(noteToUpdate)
        );
    }

    public List<NoteDto> findAllNotesByPartnerId(Long partnerId) {
        log.info("Building list of all Notes for Partner with ID {}", partnerId);
        List<Note> notes = noteRepository.findAllByPartnerIdOrderByCreationTimeDesc(partnerId)
                .orElseThrow(() -> {
                    log.error("Partner with ID {} does not exist", partnerId);
                    return new IllegalArgumentException("Partner mit dieser ID existiert nicht");
                });
        ArrayList<NoteDto> noteDtos = new ArrayList<>();
        notes.forEach(note -> noteDtos.add(NoteDto.fromNote(note)));
        return noteDtos;
    }

    public void deleteNoteById(Long id) {
        log.info("Deleting Note with ID {}", id);
        noteRepository.deleteById(id);
    }
}
