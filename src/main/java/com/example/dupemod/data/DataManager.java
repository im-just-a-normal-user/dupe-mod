package com.example.dupemod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class DataManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Path FILE = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("dupemod.json");

    public static DupeData data = new DupeData();

    public static void load() {
        try {
            if (!FILE.toFile().exists()) {
                save();
                return;
            }

            try (Reader r = new FileReader(FILE.toFile())) {
                data = GSON.fromJson(r, DupeData.class);
            }

            if (data == null) {
                data = new DupeData();
            }

            if (data.whitelist == null) data.whitelist = new java.util.HashSet<>();
            if (data.credits == null) data.credits = new java.util.HashMap<>();

        } catch (Exception e) {
            e.printStackTrace();
            data = new DupeData();
        }
    }

    public static void save() {
        try {
            FILE.toFile().getParentFile().mkdirs();

            try (Writer w = new FileWriter(FILE.toFile())) {
                GSON.toJson(data, w);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
