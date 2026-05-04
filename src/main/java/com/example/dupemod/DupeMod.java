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

        LOGGER.info("DupeMod cargado correctamente");

        // 🔥 Cargar datos JSON
        DataManager.load();

        // 🔥 Registrar comandos
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            DupeCommand.register(dispatcher);
            WhitelistCommand.register(dispatcher);
        });
    }
}
