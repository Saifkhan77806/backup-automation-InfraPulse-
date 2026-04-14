package com.backupautomationapi;


import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ITWorkflow {

    @WorkflowMethod
    String run(String system);
}
