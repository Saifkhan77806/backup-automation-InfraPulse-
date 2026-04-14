package com.example.backupautomationit;

import java.io.File;
import java.io.FileWriter;

public class ITActivityImpl implements ITActivity{
    private final String baseDir = "/data/it/";

    @Override
    public String createFile(String name) {
        try {
            new File(baseDir).mkdirs();

            FileWriter writer = new FileWriter(baseDir + name + ".txt");
            writer.write("IT file: " + name);
            writer.close();

            return "File created";
        } catch (Exception e) {
            throw new RuntimeException("Error creating file");
        }
    }

    @Override
    public String copyFiles() {
        try {
            new File("/data/it-copy/").mkdirs();

            Process process = new ProcessBuilder(
                    "rsync", "-av", "/data/it/", "/data/it-copy/"
            ).inheritIO().start();

            process.waitFor();

            return "Files copied";
        } catch (Exception e) {
            throw new RuntimeException("Copy failed");
        }
    }

    @Override
    public String compressFiles() {
        try {
            Process process = new ProcessBuilder(
                    "tar", "-czf", "/data/it-backup.tar.gz", "-C", "/data", "it"
            ).inheritIO().start();

            process.waitFor();

            return "Files compressed";
        } catch (Exception e) {
            throw new RuntimeException("Compression failed");
        }
    }
}
