package com.example.dupemod;

import com.example.dupemod.data.DataManager;
import net.fabricmc.api.ModInitializer;

public class DupeMod implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("[DupeMod] Mod iniciando...");

        DataManager.load();

        System.out.println("[DupeMod] Datos cargados correctamente");
    }
}
