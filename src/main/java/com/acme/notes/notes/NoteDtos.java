package com.acme.notes.notes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

/** Request/response shapes for the notes feature. Records only; grouped per feature. */
public final class NoteDtos {
    private NoteDtos() {}

    public record CreateNote(@NotBlank @Size(max = 120) String title, @Size(max = 4000) String body) {}

    public record NoteView(long id, String title, String body, Instant createdAt, boolean archived) {}

    public record NoteSummary(long id, String title, Instant createdAt) {}

    public record Paged<T>(List<T> items, long total) {}
}
