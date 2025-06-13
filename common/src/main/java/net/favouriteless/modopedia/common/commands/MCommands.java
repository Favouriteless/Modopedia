package net.favouriteless.modopedia.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.sun.jdi.connect.Connector.StringArgument;
import net.favouriteless.modopedia.common.network.packets.client.OpenBookPayload;
import net.favouriteless.modopedia.common.network.packets.client.OpenCategoryPayload;
import net.favouriteless.modopedia.common.network.packets.client.OpenEntryPayload;
import net.favouriteless.modopedia.common.network.packets.client.ReloadBookContentPayload;
import net.favouriteless.modopedia.platform.CommonServices;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class MCommands {

    public static void load(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(
                Commands.literal("modopedia")
                        .then(Commands.literal("open")
                                .then(Commands.literal("book")
                                        .then(Commands.argument("book_id", ResourceLocationArgument.id())
                                                .executes(MCommands::openBook))
                                )
                                .then(Commands.literal("category")
                                        .then(Commands.argument("book_id", ResourceLocationArgument.id())
                                                .then(Commands.argument("category_id", StringArgumentType.string())
                                                        .executes(MCommands::openCategory))
                                        )
                                )
                                .then(Commands.literal("entry")
                                        .then(Commands.argument("book_id", ResourceLocationArgument.id())
                                                .then(Commands.argument("entry_id", StringArgumentType.string())
                                                        .executes(MCommands::openEntry))
                                        )
                                )
                        )
                        .then(Commands.literal("reload").executes(MCommands::reloadContent))
        );
    }

    public static int reloadContent(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if(player == null)
            return 0;

        CommonServices.NETWORK.sendToPlayer(new ReloadBookContentPayload(), player);
        return 1;
    }

    public static int openBook(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if(player == null)
            return 0;

        CommonServices.NETWORK.sendToPlayer(new OpenBookPayload(ResourceLocationArgument.getId(context, "book_id")), player);
        return 1;
    }

    public static int openCategory(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if(player == null)
            return 0;

        CommonServices.NETWORK.sendToPlayer(new OpenCategoryPayload(
                ResourceLocationArgument.getId(context, "book_id"),
                StringArgumentType.getString(context, "category_id")
        ), player);
        return 1;
    }

    public static int openEntry(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if(player == null)
            return 0;

        CommonServices.NETWORK.sendToPlayer(new OpenEntryPayload(
                ResourceLocationArgument.getId(context, "book_id"),
                StringArgumentType.getString(context, "entry_id")
        ), player);
        return 1;
    }

}
