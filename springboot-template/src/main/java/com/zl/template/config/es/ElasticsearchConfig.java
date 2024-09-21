package com.zl.template.config.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        //创建RestClient
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();
        //使用RestClient创建Elasticsearch客户端
        return new ElasticsearchClient(
                new RestClientTransport(restClient,new JacksonJsonpMapper())
        );
    }
}
