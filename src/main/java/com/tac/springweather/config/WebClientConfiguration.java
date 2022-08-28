package com.tac.springweather.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.List;

@Slf4j
@Configuration
public class WebClientConfiguration {

    private static final String REQUEST_LOG = "Request: {} {}";
    private static final String RESPONSE_LOG = "Response: {}";
    private static final String HEADER_LOG = "{}={}";
    private static final Integer REQUEST_TIMEOUT = 10000;
    private static final Integer READ_WRITE_TIMEOUT = 10000;

    @Bean
    public WebClient.Builder webClientBuilder(final ObjectMapper objectMapper) throws Exception {
        return WebClient.builder()
                .clientConnector(getClientHttpConnector())
                .exchangeStrategies(ExchangeStrategies.builder().codecs((configurer) -> {
                    configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                    configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                }).build())
                .filter(logRequest())
                .filter(logResponse());
    }

    private ClientHttpConnector getClientHttpConnector() {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, REQUEST_TIMEOUT)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(READ_WRITE_TIMEOUT))
                                        .addHandlerLast(new WriteTimeoutHandler(READ_WRITE_TIMEOUT))));
        return new ReactorClientHttpConnector(httpClient.wiretap(true));
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info(RESPONSE_LOG, clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders()
                    .forEach(this::logHeader);
            return Mono.just(clientResponse);
        });
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info(REQUEST_LOG, clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach(this::logHeader);
            return next.exchange(clientRequest);
        };
    }

    private void logHeader(String name, List<String> values) {
        values.forEach(value -> log.info(HEADER_LOG, name, value));
    }
}