package com.backupautomation;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface BackupActivity {
    String backup(String system);
}
