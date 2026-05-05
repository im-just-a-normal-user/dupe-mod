package com.example.dupemod.command;

import com.example.dupemod.data.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class WhitelistCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("dupewhitelist")
                .requires(src -> src.hasPermissionLevel(2))

                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("item_id", IdentifierArgumentType.identifier())
                                .executes(ctx -> {
                                    // Usamos un identificador simple para evitar el error de carga
                                    Identifier id = IdentifierArgumentType.getIdentifier(ctx, "item_id");
                                    String idString = id.toString();

                                    if (!DataManager.data.whitelist.contains(idString)) {
                                        DataManager.data.whitelist.add(idString);
                                        DataManager.save();
                                    }

                                    ctx.getSource().sendMessage(Text.literal("✔ [DupeMod] ")
                                            .append(Text.literal(idString + " añadido a la whitelist").formatted(Formatting.WHITE))
                                            .formatted(Formatting.GREEN));
                                    return 1;
                                })
                        )
                )

                .then(CommandManager.literal("del")
                        .then(CommandManager.argument("item_id", IdentifierArgumentType.identifier())
                                .executes(ctx -> {
                                    Identifier id = IdentifierArgumentType.getIdentifier(ctx, "item_id");
                                    String idString = id.toString();

                                    if (DataManager.data.whitelist.remove(idString)) {
                                        DataManager.save();
                                        ctx.getSource().sendMessage(Text.literal("✔ [DupeMod] ")
                                                .append(Text.literal(idString + " eliminado de la whitelist").formatted(Formatting.WHITE))
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
