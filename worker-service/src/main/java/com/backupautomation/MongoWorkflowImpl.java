package com.backupautomation;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class MongoWorkflowImpl implements MongoWorkflow{
    private final MongoActivity activity =
            Workflow.newActivityStub(
                    MongoActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .setRetryOptions(
                                    RetryOptions.newBuilder()
                                            .setMaximumAttempts(3)
                                            .build()
                            )
                            .build()
            );

    @Override
    public String execute(String dbName) {
        return activity.backupMongo(dbName);
    }
}
