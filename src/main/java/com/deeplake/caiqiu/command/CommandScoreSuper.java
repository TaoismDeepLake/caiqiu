package com.deeplake.caiqiu.command;

import com.deeplake.caiqiu.system.ScoreManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandScoreSuper {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> commandBuilder = Commands.literal("caiqiu-score-forced")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("help")
                        .executes(CommandScoreSuper::printHelp))
                .then(Commands.literal("forced")
                        .then(Commands.literal("load")
                                .executes(CommandScoreSuper::forceLoad))

                )
                .then(Commands.literal("forced")
                        .then(Commands.literal("save")
                                .executes(CommandScoreSuper::forceSave))

                )
                ;

        LiteralCommandNode<CommandSource> command = dispatcher.register(commandBuilder);
    }

    private static int forceLoad(CommandContext<CommandSource> ctx) {
        ScoreManager.getInstance().save(ctx.getSource().getLevel(), ScoreManager.BACKUP_FILENAME);
        ScoreManager.getInstance().init(ctx.getSource().getLevel());
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.score.force.load.success"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int forceSave(CommandContext<CommandSource> ctx) {
        ScoreManager.getInstance().save(ctx.getSource().getLevel());
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.score.force.save.success"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int printHelp(CommandContext<CommandSource> ctx) {
        for (int i = 0; i < 5; i++) {
            ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.score.force.help." + i), false);
        }
        return Command.SINGLE_SUCCESS;
    }
}
