package com.backupautomation;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class BackupWorkflowImpl implements BackupWorkflow {

    private final BackupActivity activity =
            Workflow.newActivityStub(
                    BackupActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(5))
                            .setRetryOptions(
                                    RetryOptions.newBuilder()
                                            .setMaximumAttempts(5)
                                            .build()
                            )
                            .build()
            );

    @Override
    public String execute(String system) {
        return activity.backup(system);
    }
}
