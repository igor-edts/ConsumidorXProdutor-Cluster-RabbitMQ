package com.example.orchestrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.consumer.ConsumerApplication;
import com.example.producer1.Producer1Application;

public class ClusterOrchestratorApplication {

    private final List<ConfigurableApplicationContext> contexts = new ArrayList<>();

    public static void main(String[] args) {
        ClusterOrchestratorApplication orchestrator = new ClusterOrchestratorApplication();
        orchestrator.startConsumers(4);
        orchestrator.startProducers(2);

        Runtime.getRuntime().addShutdownHook(new Thread(orchestrator::stopAll));
    }

    private void startProducers(int quantity) {
        for (int i = 1; i <= quantity; i++) {
            String id = "producer-" + i;
            contexts.add(
                new SpringApplicationBuilder(Producer1Application.class)
                    .properties(baseProps(id, Map.of("producer.id", id)))
                    .web(WebApplicationType.NONE)
                    .logStartupInfo(false)
                    .run()
            );
        }
    }

    private void startConsumers(int quantity) {
        for (int i = 1; i <= quantity; i++) {
            String id = "consumer-" + i;
            contexts.add(
                new SpringApplicationBuilder(ConsumerApplication.class)
                    .properties(baseProps(id, Map.of("consumer.id", id)))
                    .web(WebApplicationType.NONE)
                    .logStartupInfo(false)
                    .run()
            );
        }
    }

    private Map<String, Object> baseProps(String appName, Map<String, Object> extra) {
        Map<String, Object> props = new HashMap<>();
        props.put("spring.application.name", appName);
        props.put("server.port", "0");
        props.put("spring.main.web-application-type", "none");
        props.putAll(extra);
        return props;
    }

    private void stopAll() {
        contexts.forEach(ConfigurableApplicationContext::close);
    }
}
