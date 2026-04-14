package com.backupautomationapi;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WorkflowController {

    private final WorkflowClient client;

    public WorkflowController(WorkflowClient client) {
        this.client = client;
    }

    @GetMapping("/start")
    public String start(@RequestParam String system) {

        BackupWorkflow workflow =
                client.newWorkflowStub(
                        BackupWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setTaskQueue("backup-queue")
                                .setWorkflowId("wf-" + UUID.randomUUID())
                                .build()
                );

        // 🔥 BACKGROUND PROCESS
        WorkflowClient.start(workflow::execute, system);

        return "🔥 Started backup for " + system;
    }

    @GetMapping("/mongo")
    public String startMongo(@RequestParam String dbName) {

        ITWorkflow workflow =
                client.newWorkflowStub(
                        ITWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setTaskQueue("it-ops-queue") // 🔥 Mongo queue
                                .setWorkflowId("it-ops-" + UUID.randomUUID())
                                .build()
                );

        WorkflowClient.start(workflow::run, dbName);

        return "🔥 Mongo backup started for " + dbName;
    }
}
