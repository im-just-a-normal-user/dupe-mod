package com.example.dupemod.command;

import com.example.dupemod.data.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WhitelistCommand {

    public static void register(CommandDispatcher<net.minecraft.server.command.ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("dupewhitelist")
                .requires(src -> src.hasPermissionLevel(2))

                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("item", StringArgumentType.string())
                                .executes(ctx -> {

                                    String id = StringArgumentType.getString(ctx, "item");

                                    if (!Registries.ITEM.containsId(new Identifier(id))) {
                                        ctx.getSource().sendMessage(Text.literal("❌ Item inválido"));
                                        return 0;
                                    }

                                    DataManager.data.whitelist.add(id);
                                    DataManager.save();

                                    ctx.getSource().sendMessage(Text.literal("✅ Añadido a whitelist"));
                                    return 1;
                                })
                        )
                )

                .then(CommandManager.literal("del")
                        .then(CommandManager.argument("item", StringArgumentType.string())
                                .executes(ctx -> {

                                    String id = StringArgumentType.getString(ctx, "item");

                                    DataManager.data.whitelist.remove(id);
                                    DataManager.save();

                                    ctx.getSource().sendMessage(Text.literal("✅ Eliminado de whitelist"));
                                    return 1;
                                })
                        )
                )
        );
    }
}
