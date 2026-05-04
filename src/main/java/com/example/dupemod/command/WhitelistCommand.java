package com.example.dupemod.command;

import com.example.dupemod.data.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.registry.Registries;

public class WhitelistCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("dupewhitelist")
                .requires(src -> src.hasPermissionLevel(2))

                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(null))
                                .executes(ctx -> {
                                    // Obtiene el ID completo (ej: minecraft:stick o create:cogwheel)
                                    String id = Registries.ITEM.getId(ItemStackArgumentType.getItemStackArgument(ctx, "item").getItem()).toString();

                                    if (!DataManager.data.whitelist.contains(id)) {
                                        DataManager.data.whitelist.add(id);
                                        DataManager.save();
                                    }

                                    ctx.getSource().sendMessage(Text.literal("✔ [DupeMod] ")
                                            .append(Text.literal(id + " añadido a la whitelist").formatted(Formatting.WHITE))
                                            .formatted(Formatting.GREEN));
                                    return 1;
                                })
                        )
                )

                .then(CommandManager.literal("del")
                        .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(null))
                                .executes(ctx -> {
                                    String id = Registries.ITEM.getId(ItemStackArgumentType.getItemStackArgument(ctx, "item").getItem()).toString();

                                    if (DataManager.data.whitelist.remove(id)) {
                                        DataManager.save();
                                        ctx.getSource().sendMessage(Text.literal("✔ [DupeMod] ")
                                                .append(Text.literal(id + " eliminado de la whitelist").formatted(Formatting.WHITE))
                                                .formatted(Formatting.GREEN));
                                    } else {
                                        ctx.getSource().sendMessage(Text.literal("✖ [DupeMod] ")
                                                .append(Text.literal("El ítem no estaba en la whitelist").formatted(Formatting.WHITE))
                                                .formatted(Formatting.RED));
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }
}
