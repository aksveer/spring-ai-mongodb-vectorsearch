package com.example.vectorsearch.config;

import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingConfiguration {

    @Bean
    OllamaOptions createOllamaOptions() {
        return OllamaOptions.builder().model("nomic-embed-text")
                .truncate(false)
                .build();
    }
}
