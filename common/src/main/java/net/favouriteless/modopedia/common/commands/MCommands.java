package net.favouriteless.modopedia.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
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

import java.util.Optional;

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
                                                        .executes(ctx -> openCategory(ctx, ResourceLocationArgument.getId(ctx, "book_id")))
                                                )
                                        )
                                        .then(Commands.argument("category_id", StringArgumentType.string())
                                                .executes(ctx -> openCategory(ctx, null))
                                        )
                                )
                                .then(Commands.literal("entry")
                                        .then(Commands.argument("book_id", ResourceLocationArgument.id())
                                                .then(Commands.argument("entry_id", StringArgumentType.string())
                                                        .executes(ctx -> openEntry(ctx, ResourceLocationArgument.getId(ctx, "book_id")))
                                                )
                                        )
                                        .then(Commands.argument("entry_id", StringArgumentType.string())
                                                .executes(ctx -> openEntry(ctx, null))
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

    public static int openCategory(CommandContext<CommandSourceStack> context, ResourceLocation id) {
        ServerPlayer player = context.getSource().getPlayer();
        if(player == null)
            return 0;

        CommonServices.NETWORK.sendToPlayer(new OpenCategoryPayload(
                Optional.ofNullable(id),
                StringArgumentType.getString(context, "category_id")
        ), player);
        return 1;
    }

    public static int openEntry(CommandContext<CommandSourceStack> context, ResourceLocation id) {
        ServerPlayer player = context.getSource().getPlayer();
        if(player == null)
            return 0;

        CommonServices.NETWORK.sendToPlayer(new OpenEntryPayload(
                Optional.ofNullable(id),
                StringArgumentType.getString(context, "entry_id")
        ), player);
        return 1;
    }

}
