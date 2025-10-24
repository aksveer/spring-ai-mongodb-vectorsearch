package com.example.vectorsearch.utils;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static com.example.vectorsearch.utils.EmbeddingUtils.getDoubleList;

//@Component
//@ConditionalOnProperty(prefix = "app", name = "create-embedding-enabled", havingValue = "true")
public class CommandLineRunnerImpl implements CommandLineRunner {

    public static final String MOVIES_COLLECTION_NAME = "movies";
    private static final Logger log = LoggerFactory.getLogger(CommandLineRunnerImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    OllamaOptions ollamaOptions;

    @Override
    public void run(String... args) throws Exception {
        log.debug("Started created embedding");
        List<Document> movies = mongoTemplate.findAll(Document.class, MOVIES_COLLECTION_NAME);
        for (Document movie : movies) {
            String plot = movie.getString("plot");
            if (plot != null && !plot.isBlank()) {
                EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(plot), ollamaOptions));
                float[] embedding = embeddingResponse.getResult().getOutput();
                List<Double> doubles = getDoubleList(embedding);
                movie.put("embedding", doubles);
                mongoTemplate.save(movie, MOVIES_COLLECTION_NAME);
            }
        }
    }

    @PostConstruct
    public void debugProps() {
        log.info("Command Line runner Bean Created");
    }

}
