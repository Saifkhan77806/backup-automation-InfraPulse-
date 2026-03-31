package com.backupautomation;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class BackupActivityImpl implements BackupActivity {

    @Override
    public String backup(String system) {

        try {
            String sourceDir = "/data/source/";
            String backupDir = "/data/backup/";

            // 🔥 Create dummy file
            String fileName = "file_" + system + "_" + System.currentTimeMillis() + ".txt";

            File file = new File(sourceDir + fileName);
            FileWriter writer = new FileWriter(file);
            writer.write("Backup data for " + system);
            writer.close();

            System.out.println("Created file: " + fileName);

            // 🔥 Simulate failure randomly
            if (new Random().nextInt(3) != 0) {
                throw new RuntimeException("Simulated failure before rsync");
            }

            // 🔥 Run rsync command
            Process process = new ProcessBuilder(
                    "rsync", "-av", sourceDir, backupDir
            ).inheritIO().start();

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("rsync failed");
            }

            return "✅ Backup successful for " + system;

        } catch (Exception e) {
            throw new RuntimeException("❌ Backup failed: " + e.getMessage());
        }
    }
}
