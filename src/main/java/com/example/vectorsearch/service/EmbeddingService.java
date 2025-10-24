package com.example.vectorsearch.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmbeddingService {
    private final EmbeddingModel model;

    private final OllamaOptions ollamaOptions;

    public EmbeddingService(EmbeddingModel embedding, OllamaOptions ollamaOptions) {
        this.model = embedding;
        this.ollamaOptions = ollamaOptions;
    }

    public float[] embed(String text) {
        EmbeddingResponse embeddingResponse = model.call(new EmbeddingRequest(List.of(text),
                ollamaOptions));
        return embeddingResponse.getResult().getOutput();
    }
}
