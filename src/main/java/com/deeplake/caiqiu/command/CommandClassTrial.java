package com.deeplake.caiqiu.command;

import com.deeplake.caiqiu.events.EventsClassTrial;
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

public class CommandClassTrial {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> commandBuilder = Commands.literal("caiqiu-classtrial")
                .requires(s -> s.hasPermission(2))
//                .then(Commands.literal("help")
//                        .executes(CommandSearchPhase::printHelp))
                .then(Commands.literal("begin").executes(CommandClassTrial::searchStart))
                .then(Commands.literal("pause").executes(CommandClassTrial::searchPause))
                .then(Commands.literal("end").executes(CommandClassTrial::searchEnd))
                ;

        LiteralCommandNode<CommandSource> command = dispatcher.register(commandBuilder);
        dispatcher.register(Commands.literal("cqclasstrial").redirect(command));
        dispatcher.register(Commands.literal("cqcaipan").redirect(command));
    }

    private static int searchStart(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        CommonFunctions.showTitle(ctx.getSource(),
                new TranslationTextComponent("caiqiu.command.class_trial.start.title"), title
                );
        for (ServerPlayerEntity player: ctx.getSource().getServer().getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(player, "caiqiu.command.class_trial.start.title");
        }

        ctx.getSource().getServer().setPvpAllowed(false);
        EventsClassTrial.BeginTrial();
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.class_trial.set.success"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int searchEnd(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        for (ServerPlayerEntity player: ctx.getSource().getServer().getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(player, "caiqiu.command.class_trial.end.msg");
        }
        CommonFunctions.showTitle(ctx.getSource(),
                new TranslationTextComponent("caiqiu.command.class_trial.end.msg"), title
        );
        EventsClassTrial.EndTrial();
        ctx.getSource().getServer().setPvpAllowed(true);
        return Command.SINGLE_SUCCESS;
    }

    private static int searchPause(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        String msg = "caiqiu.command.class_trial.pause.msg";
        if (EventsClassTrial.GetTrialStatus() == EventsClassTrial.EnumClassTrialStatus.PAUSED) {
            msg = "caiqiu.command.class_trial.resume.msg";
        }
        for (ServerPlayerEntity player: ctx.getSource().getServer().getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(player, msg);
        }
        CommonFunctions.showTitle(ctx.getSource(),
                new TranslationTextComponent(msg), title
        );
        EventsClassTrial.SwitchTrialPause();
        return Command.SINGLE_SUCCESS;
    }
}
