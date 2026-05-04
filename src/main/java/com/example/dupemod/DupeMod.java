package com.example.dupemod;

import com.example.dupemod.command.DupeCommand;
import com.example.dupemod.command.WhitelistCommand;
import com.example.dupemod.data.DataManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DupeMod implements ModInitializer {
    public static final String MOD_ID = "dupemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Cargar datos desde el archivo JSON al iniciar
        DataManager.load();

        // Registrar los comandos en el servidor
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            DupeCommand.register(dispatcher); // Incluye /dupe y /dupecredit
            WhitelistCommand.register(dispatcher); // Incluye /dupewhitelist
        });

        LOGGER.info("DupeMod: Sistema de duplicacion y creditos inicializado.");
    }
}
