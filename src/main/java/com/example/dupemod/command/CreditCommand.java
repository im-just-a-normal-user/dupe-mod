  package com.example.dupemod.command;

import com.example.dupemod.data.DataManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class CreditCommand {

    public static void register(CommandDispatcher<net.minecraft.server.command.ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("dupecreditadd")
                .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(ctx -> {

                            ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");

                            int credits = DataManager.data.credits.getOrDefault(target.getUuid(), 0);
                            DataManager.data.credits.put(target.getUuid(), credits + 1);
                            DataManager.save();

                            ctx.getSource().sendMessage(Text.literal("✅ Crédito añadido"));
                            return 1;
                        })
                )
        );

        dispatcher.register(CommandManager.literal("dupecreditdel")
                .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(ctx -> {

                            ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");

                            int credits = DataManager.data.credits.getOrDefault(target.getUuid(), 0);
                            credits = Math.max(0, credits - 1);

                            DataManager.data.credits.put(target.getUuid(), credits);
                            DataManager.save();

                            ctx.getSource().sendMessage(Text.literal("✅ Crédito eliminado"));
                            return 1;
                        })
                )
        );
    }
}
