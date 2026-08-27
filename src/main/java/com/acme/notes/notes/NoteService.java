package com.acme.notes.notes;

import com.acme.notes.shared.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** In-memory store: enough to demonstrate the conventions, nothing more. */
@Service
public class NoteService {

    private record Note(long id, String title, String body, Instant createdAt, boolean archived) {
        Note archive() { return new Note(id, title, body, createdAt, true); }
    }

    private final Map<Long, Note> notes = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong();

    public NoteDtos.NoteView create(NoteDtos.CreateNote request) {
        long id = ids.incrementAndGet();
        Note note = new Note(id, request.title().trim(), request.body(), Instant.now(), false);
        notes.put(id, note);
        return toView(note);
    }

    public NoteDtos.Paged<NoteDtos.NoteSummary> list(int page, int size) {
        List<Note> live = notes.values().stream()
                .filter(n -> !n.archived())
                .sorted(Comparator.comparing(Note::createdAt).reversed())
                .toList();
        List<NoteDtos.NoteSummary> items = live.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .map(n -> new NoteDtos.NoteSummary(n.id(), n.title(), n.createdAt()))
                .toList();
        return new NoteDtos.Paged<>(items, live.size());
    }

    public NoteDtos.NoteView get(long id) {
        Note note = notes.get(id);
        if (note == null || note.archived()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "NOTE_NOT_FOUND", "note " + id + " does not exist");
        }
        return toView(note);
    }

    /** Notes are never deleted; they are archived (CLAUDE.md: soft delete only). */
    public NoteDtos.NoteView archive(long id) {
        Note note = notes.get(id);
        if (note == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "NOTE_NOT_FOUND", "note " + id + " does not exist");
        }
        Note archived = note.archive();
        notes.put(id, archived);
        return toView(archived);
    }

    private static NoteDtos.NoteView toView(Note n) {
        return new NoteDtos.NoteView(n.id(), n.title(), n.body(), n.createdAt(), n.archived());
    }
}
