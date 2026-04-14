package com.backupautomation;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class MongoActivityImpl implements MongoActivity {

    @Override
    public String backupMongo(String dbName) {

        try {
            String dumpDir = "/data/mongo-dump/";
            String backupDir = "/data/mongo-backup/";

            // 🔥 Create directories (safe)
            new File(dumpDir).mkdirs();
            new File(backupDir).mkdirs();

            // 🔥 Create dummy dump file (simulate mongodump)
            String fileName = "mongo_" + dbName + "_" + System.currentTimeMillis() + ".json";

            File dumpFile = new File(dumpDir + fileName);
            FileWriter writer = new FileWriter(dumpFile);
            writer.write("{ \"db\": \"" + dbName + "\", \"data\": \"sample backup data\" }");
            writer.close();

            System.out.println("📦 Mongo dump created: " + fileName);

            // 🔥 Simulate failure randomly
            if (new Random().nextInt(3) != 0) {
                throw new RuntimeException("Simulated Mongo failure before backup");
            }

            // 🔥 Simulate mongodump command (REAL WORLD)
            // Process process = new ProcessBuilder(
            //     "mongodump",
            //     "--db=" + dbName,
            //     "--out=" + dumpDir
            // ).inheritIO().start();

            // int dumpExit = process.waitFor();
            // if (dumpExit != 0) {
            //     throw new RuntimeException("mongodump failed");
            // }

            // 🔥 Copy dump to backup location (like rsync)
            Process process = new ProcessBuilder(
                    "rsync", "-av", dumpDir, backupDir
            ).inheritIO().start();

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("Mongo backup rsync failed");
            }

            System.out.println("✅ Mongo backup stored successfully");

            return "✅ Mongo backup completed for " + dbName;

        } catch (Exception e) {
            throw new RuntimeException("❌ Mongo backup failed: " + e.getMessage());
        }
    }
}