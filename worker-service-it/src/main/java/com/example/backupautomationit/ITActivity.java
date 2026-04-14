package com.example.backupautomationit;


import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ITActivity {
    String createFile(String name);
    String copyFiles();
    String compressFiles();
}
