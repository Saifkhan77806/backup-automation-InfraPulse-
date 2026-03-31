package com.backupautomation;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerConfig {

    @Bean
    public WorkerFactory workerFactory() {

        WorkflowServiceStubs service =
                WorkflowServiceStubs.newInstance(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setTarget("host.docker.internal:7233") // 🔥 SAME SERVER
                                .build()
                );

        WorkflowClient client = WorkflowClient.newInstance(service);

        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker("backup-queue");

        worker.registerWorkflowImplementationTypes(BackupWorkflowImpl.class);
        worker.registerActivitiesImplementations(new BackupActivityImpl());

        factory.start();

        return factory;
    }
}
