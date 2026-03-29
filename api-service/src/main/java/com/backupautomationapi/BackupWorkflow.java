package com.backupautomationapi;


import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BackupWorkflow {
    @WorkflowMethod
    String execute(String system);
}
