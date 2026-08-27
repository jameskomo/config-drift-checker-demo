package com.acme.notes.notes;

import com.acme.notes.shared.ApiResponse;
import com.acme.notes.shared.PageMeta;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private static final int MAX_PAGE_SIZE = 100;

    private final NoteService notes;

    public NoteController(NoteService notes) {
        this.notes = notes;
    }

    @GetMapping
    public ApiResponse<List<NoteDtos.NoteSummary>> list(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        int pageNo = Math.max(page, 1);
        int pageSize = Math.clamp(size, 1, MAX_PAGE_SIZE);
        var result = notes.list(pageNo, pageSize);
        return ApiResponse.ok(result.items(), PageMeta.of(pageNo, pageSize, result.total()));
    }

    @GetMapping("/{id}")
    public ApiResponse<NoteDtos.NoteView> get(@PathVariable long id) {
        return ApiResponse.ok(notes.get(id));
    }

    @PostMapping
    public ApiResponse<NoteDtos.NoteView> create(@Valid @RequestBody NoteDtos.CreateNote request) {
        return ApiResponse.ok(notes.create(request));
    }

    @PostMapping("/{id}/archive")
    public ApiResponse<NoteDtos.NoteView> archive(@PathVariable long id) {
        return ApiResponse.ok(notes.archive(id));
    }
}
