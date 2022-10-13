package com.freemanpivo.gremlinapp.graphdb.config;

import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GremlinServer {

    @Bean
    public GraphTraversalSource connect() {
        return AnonymousTraversalSource.traversal().withRemote(DriverRemoteConnection.using("localhost", 8182));
    }

}
