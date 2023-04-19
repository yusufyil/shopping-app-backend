package edu.marmara.shoppingappbackend.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticsearchConfig {
    @Value("${application.network.name}")
    String host;

    @Bean
    RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration =
                ClientConfiguration.builder()
                        //.connectedTo(host + ":9200")
                        .connectedTo("elasticsearch:9200")
                        .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
