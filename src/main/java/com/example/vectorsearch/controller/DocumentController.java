package com.example.vectorsearch.controller;

import com.example.vectorsearch.service.DocumentService;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/similar")
    public List<Document> searchSimilar(@RequestParam String query,
                                        @RequestParam(defaultValue = "3") int limit) {
        return documentService.findSimilar(query, limit);
    }
}
