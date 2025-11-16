package com.example.config;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TracingRestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(Tracer tracer, Propagator propagator) {

        RestTemplate template = new RestTemplate();

        template.getInterceptors().add((request, body, execution) -> {
            // Get current trace context
            TraceContext context = tracer.currentSpan().context();

            // Inject trace headers automatically (Brave uses B3 format)
            propagator.inject(context, request.getHeaders(),
                    (headers, key, value) -> headers.add(key, value));

            return execution.execute(request, body);
        });

        return template;
    }
}
