package com.example.dupemod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class DataManager {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("dupemod.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static DupeData data = new DupeData();

    public static void load() {
        File file = CONFIG_PATH.toFile();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                data = GSON.fromJson(reader, DupeData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
