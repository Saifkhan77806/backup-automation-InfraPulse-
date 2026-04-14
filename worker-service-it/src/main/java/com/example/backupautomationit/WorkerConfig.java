package com.example.backupautomationit;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerConfig {

    private WorkerFactory factory;

    @Bean
    public WorkerFactory startWorker() {

        WorkflowServiceStubs service =
                WorkflowServiceStubs.newInstance(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setTarget("host.docker.internal:7233")
                                .build()
                );

        WorkflowClient client = WorkflowClient.newInstance(service);

        factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker("it-ops-queue");

        worker.registerWorkflowImplementationTypes(ITWorkflowImpl.class);
        worker.registerActivitiesImplementations(new ITActivityImpl());

        factory.start();

        System.out.println("🔥 IT Worker started and polling queue: it-ops-queue");

        // 🔥 KEEP APPLICATION ALIVE
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return factory;
    }
}