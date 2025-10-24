package com.example.vectorsearch.service;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.vectorsearch.utils.EmbeddingUtils.getDoubleList;

@Service
public class DocumentService {

    private final MongoTemplate mongoTemplate;
    private final EmbeddingService embeddingService;

    public DocumentService(MongoTemplate mongoTemplate, EmbeddingService embeddingService) {
        this.mongoTemplate = mongoTemplate;
        this.embeddingService = embeddingService;
    }

    public List<Document> findSimilar(String query, int limit) {
        float[] queryVector = embeddingService.embed(query);
        List<Double> doubles = getDoubleList(queryVector);
        Document vectorSearch = new Document("$vectorSearch", new Document()
                .append("index", "vector_index")
            .append("queryVector", doubles)
            .append("path", "embedding")
            .append("numCandidates", 10)
            .append("limit", limit));
        Aggregation agg = Aggregation.newAggregation(Aggregation.stage(vectorSearch),
                Aggregation.project("title", "plot")
                        .andExpression("meta.vectorSearchScore").as("score"));
        return mongoTemplate.aggregate(agg, "movies", Document.class).getMappedResults();
    }
}
