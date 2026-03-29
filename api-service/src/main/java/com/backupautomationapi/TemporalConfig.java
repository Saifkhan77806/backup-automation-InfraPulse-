package com.backupautomationapi;


import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {
    @Bean
    public WorkflowClient workflowClient() {
        return WorkflowClient.newInstance(
                WorkflowServiceStubs.newInstance(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setTarget("0.0.0.0:7233") // 🔥 TEMPORAL IP
                                .build()
                )
        );
    }

}
