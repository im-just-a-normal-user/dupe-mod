package com.example.dupemod.command;

import com.example.dupemod.data.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DupeCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // COMANDO /dupe
        dispatcher.register(CommandManager.literal("dupe")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (player == null) return 0;

                    ItemStack stack = player.getMainHandStack();
                    if (stack.isEmpty()) {
                        player.sendMessage(Text.literal("✖ [DupeMod] ").formatted(Formatting.RED)
                                .append(Text.literal("No tienes nada en la mano").formatted(Formatting.WHITE)), false);
                        return 0;
                    }

                    String id = Registries.ITEM.getId(stack.getItem()).toString();
                    if (!DataManager.data.whitelist.contains(id)) {
                        player.sendMessage(Text.literal("✖ [DupeMod] ").formatted(Formatting.RED)
                                .append(Text.literal("Este ítem no está en la whitelist").formatted(Formatting.WHITE)), false);
                        return 0;
                    }

                    int credits = DataManager.data.playerCredits.getOrDefault(player.getUuid(), 0);
                    if (credits <= 0) {
                        player.sendMessage(Text.literal("✖ [DupeMod] ").formatted(Formatting.RED)
                                .append(Text.literal("No tienes créditos suficientes").formatted(Formatting.WHITE)), false);
                        return 0;
                    }

                    // Duplicar ítem
                    ItemStack clone = stack.copy();
                    if (player.getInventory().insertStack(clone)) {
                        DataManager.data.playerCredits.put(player.getUuid(), credits - 1);
                        DataManager.save();
                        player.sendMessage(Text.literal("✔ [DupeMod] ").formatted(Formatting.GREEN)
                                .append(Text.literal("Ítem duplicado con éxito (-1 crédito)").formatted(Formatting.WHITE)), false);
                    } else {
                        player.sendMessage(Text.literal("✖ [DupeMod] ").formatted(Formatting.RED)
                                .append(Text.literal("Inventario lleno").formatted(Formatting.WHITE)), false);
                    }
                    return 1;
                })
        );

        // COMANDO /dupecredit (add/del)
        dispatcher.register(CommandManager.literal("dupecredit")
                .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                        .executes(ctx -> {
                                            ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                            int current = DataManager.data.playerCredits.getOrDefault(target.getUuid(), 0);
                                            
                                            DataManager.data.playerCredits.put(target.getUuid(), current + amount);
                                            DataManager.save();
                                            
                                            ctx.getSource().sendMessage(Text.literal("✔ [DupeMod] ").formatted(Formatting.GREEN)
                                                    .append(Text.literal("Se añadieron " + amount + " créditos a " + target.getName().getString()).formatted(Formatting.WHITE)));
                                            return 1;
                                        }))))
                .then(CommandManager.literal("del")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                                        .executes(ctx -> {
                                            ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                                            int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                            int current = DataManager.data.playerCredits.getOrDefault(target.getUuid(), 0);
                                            
                                            DataManager.data.playerCredits.put(target.getUuid(), Math.max(0, current - amount));
                                            DataManager.save();
                                            
                                            ctx.getSource().sendMessage(Text.literal("✔ [DupeMod] ").formatted(Formatting.GREEN)
                                                    .append(Text.literal("Se quitaron " + amount + " créditos a " + target.getName().getString()).formatted(Formatting.WHITE)));
                                            return 1;
                                        }))))
        );
    }
}
