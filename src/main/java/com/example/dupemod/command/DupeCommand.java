package com.example.dupemod.command;

import com.example.dupemod.data.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class DupeCommand {

    public static void register(CommandDispatcher<net.minecraft.server.command.ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("dupe")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
                    ItemStack stack = player.getMainHandStack();

                    if (stack.isEmpty()) {
                        player.sendMessage(Text.literal("❌ Mano vacía"), false);
                        return 0;
                    }

                    String id = net.minecraft.registry.Registries.ITEM.getId(stack.getItem()).toString();

                    if (!DataManager.data.whitelist.contains(id)) {
                        player.sendMessage(Text.literal("❌ Item no permitido"), false);
                        return 0;
                    }

                    int credits = DataManager.data.credits.getOrDefault(player.getUuid(), 0);

                    if (credits <= 0) {
                        player.sendMessage(Text.literal("❌ Sin créditos"), false);
                        return 0;
                    }

                    ItemStack copy = stack.copy();

                    if (!player.getInventory().insertStack(copy)) {
                        player.dropItem(copy, false);
                    }

                    DataManager.data.credits.put(player.getUuid(), credits - 1);
                    DataManager.save();

                    player.sendMessage(Text.literal("✅ Item duplicado (-1 crédito)"), false);

                    return 1;
                })
        );
    }
}
