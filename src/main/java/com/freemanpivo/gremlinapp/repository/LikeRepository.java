package com.freemanpivo.gremlinapp.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.T;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeRepository implements ILikeRepository {
    private final GraphTraversalSource g;
    private static final String PERSON_LABEL = "PERSON";
    private static final String LIKES_LABEL = "LIKES";

    @Override
    public void save(String entityIdPersonWhoLiked, String entityIdPersonWhoWasLiked) {
        final var originId = PERSON_LABEL.concat("#").concat(entityIdPersonWhoLiked);
        final var targetId = PERSON_LABEL.concat("#").concat(entityIdPersonWhoWasLiked);
        final var edgeId = entityIdPersonWhoLiked.concat("#LIKES#").concat(entityIdPersonWhoWasLiked);

        try {
            g.addE(LIKES_LABEL).property(T.id, edgeId).from(__.V(originId)).to(__.V(targetId)).next();
        } catch (CompletionException e) {
            log.warn("already exists this edge!");
        }
    }
}
