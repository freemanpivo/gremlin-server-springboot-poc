FROM adoptopenjdk/openjdk11:alpine

MAINTAINER Pedro Ivo de Andrade <@freemanpivo> 

RUN apk update && \
	apk add wget unzip git bash curl ca-certificates dumb-init && \
	update-ca-certificates

RUN wget -O /gremlin.zip https://dlcdn.apache.org/tinkerpop/3.6.1/apache-tinkerpop-gremlin-server-3.6.1-bin.zip && \
        unzip /gremlin.zip -d /gremlin && \
        rm /gremlin.zip

WORKDIR /gremlin/apache-tinkerpop-gremlin-server-3.6.1

RUN mkdir /graph_file

EXPOSE 8182

COPY conf/ .

# RUN cat conf/tinkergraph-empty.properties

ENTRYPOINT ["/usr/bin/dumb-init", "--rewrite", "15:2",  "--"]

RUN chmod 700 startup.sh
CMD ["./startup.sh"]