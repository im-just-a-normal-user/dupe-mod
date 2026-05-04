package com.example.dupemod.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DupeData {
    // Lista de items en la whitelist
    public List<String> whitelist = new ArrayList<>();
    
    // Mapa de créditos por jugador (UUID -> Cantidad)
    public Map<UUID, Integer> playerCredits = new HashMap<>();
}
