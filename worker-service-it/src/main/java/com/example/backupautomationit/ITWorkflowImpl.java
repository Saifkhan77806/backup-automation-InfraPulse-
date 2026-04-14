package com.example.backupautomationit;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class ITWorkflowImpl implements ITWorkflow{
    private final ITActivity activity =
            Workflow.newActivityStub(
                    ITActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .build()
            );

    @Override
    public String run(String name) {

        activity.createFile(name);
        activity.copyFiles();
        activity.compressFiles();

        return "IT workflow completed";
    }

}
