package fom.pmse.crms.backend.repository;

import fom.pmse.crms.backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<List<Note>> findAllByPartnerIdOrderByCreationTimeDesc(Long partnerId);
}
