package com.backupautomation;

import java.util.Random;

public class BackupActivityImpl  implements BackupActivity {

    @Override
    public String backup(String system) {

        System.out.println("Processing: " + system);

        if (new Random().nextInt(3) != 0) {
            throw new RuntimeException("❌ Failed: " + system);
        }

        System.out.println("Backup Success");

        return "✅ Backup success: " + system;
    }
}
