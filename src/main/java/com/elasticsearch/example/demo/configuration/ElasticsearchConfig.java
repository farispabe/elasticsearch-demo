package com.elasticsearch.example.demo.configuration;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.net.InetAddress;

@Configuration
@EnableJpaRepositories(basePackages = "com.elasticsearch.example.demo.repository")
@EnableElasticsearchRepositories(basePackages = "com.elasticsearch.example.demo.elasticrepository")
public class ElasticsearchConfig {
    @Value("${elasticsearch.clustername}")
    private String clusterName;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private String port;

    @Bean
    public Client client() throws IOException {
        Settings elasticsearchSettings = Settings.builder()
                .put("cluster.name", clusterName).build();
        TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchOperations() throws IOException{
        return new ElasticsearchTemplate(client());
    }
}
