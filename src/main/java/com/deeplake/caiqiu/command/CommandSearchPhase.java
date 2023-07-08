package com.deeplake.caiqiu.command;

import com.deeplake.caiqiu.events.EventsSeachPhase;
import com.deeplake.caiqiu.util.CommonFunctions;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandSearchPhase {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> commandBuilder = Commands.literal("caiqiu-search")
                .requires(s -> s.hasPermission(2))
//                .then(Commands.literal("help")
//                        .executes(CommandSearchPhase::printHelp))
                .then(Commands.literal("begin").executes(CommandSearchPhase::searchStart))
                .then(Commands.literal("end").executes(CommandSearchPhase::searchEnd))
                ;

        LiteralCommandNode<CommandSource> command = dispatcher.register(commandBuilder);
        dispatcher.register(Commands.literal("cqsearch").redirect(command));
        dispatcher.register(Commands.literal("cqsoucha").redirect(command));
    }

    private static int searchStart(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        CommonFunctions.showTitle(ctx.getSource(),
                new TranslationTextComponent("caiqiu.command.search.start.title"), title
                );
        for (ServerPlayerEntity player: ctx.getSource().getServer().getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(player, "caiqiu.command.search.start.title");
        }

        ctx.getSource().getServer().setPvpAllowed(false);
        EventsSeachPhase.isSearching = true;
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.search.set.success"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int searchEnd(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        for (ServerPlayerEntity player: ctx.getSource().getServer().getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(player, "caiqiu.command.search.end.msg");
        }
        CommonFunctions.showTitle(ctx.getSource(),
                new TranslationTextComponent("caiqiu.command.search.end.msg"), title
        );
        EventsSeachPhase.isSearching = false;
        ctx.getSource().getServer().setPvpAllowed(true);
        return Command.SINGLE_SUCCESS;
    }
}
