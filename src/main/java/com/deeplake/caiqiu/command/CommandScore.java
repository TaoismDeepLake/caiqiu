package com.deeplake.caiqiu.command;

import com.deeplake.caiqiu.system.ScoreManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.UUID;

public class CommandScore {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> commandBuilder = Commands.literal("caiqiu-score")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("help")
                        .executes(CommandScore::printHelp))
                .then(Commands.literal("set")
                        .then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.argument("score", IntegerArgumentType.integer()).executes(CommandScore::setScore))
                        )
                )
                .then(Commands.literal("add")
                        .then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.argument("score", IntegerArgumentType.integer()).executes(CommandScore::addScore))
                        )
                )
                .then(Commands.literal("query")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(CommandScore::checkScore)))
                .then(Commands.literal("check")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(CommandScore::checkScore)))
                .then(Commands.literal("list")
                                .executes(CommandScore::listScore))
                ;

        LiteralCommandNode<CommandSource> command = dispatcher.register(commandBuilder);
        dispatcher.register(Commands.literal("cqfenshu").redirect(command));
        dispatcher.register(Commands.literal("cqscore").redirect(command));
    }

    private static int printHelp(CommandContext<CommandSource> ctx) {
        for (int i = 0; i < 5; i++) {
            ctx.getSource().sendSuccess(new TranslationTextComponent("cqiqiu.command.score.help." + i), false);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int setScore(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgument.getPlayer(ctx, "player");
        int score = IntegerArgumentType.getInteger(ctx, "score");
        UUID uuid = player.getUUID();
        ScoreManager.SetScore(uuid, score);
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.score.set.success", player.getDisplayName(), score), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int checkScore(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgument.getPlayer(ctx, "player");
        UUID uuid = player.getUUID();
        int result = ScoreManager.GetScore(uuid);
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.score.query.success", player.getDisplayName(), result), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int addScore(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgument.getPlayer(ctx, "player");
        int score = IntegerArgumentType.getInteger(ctx, "score");
        UUID uuid = player.getUUID();
        int result = ScoreManager.AddScore(uuid, score);
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.score.add.success", score, player.getDisplayName(), result), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int listScore(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        Entity entity = ctx.getSource().getEntity();
        if (entity instanceof PlayerEntity)
        {
            ScoreManager.PrintScoreListTo((PlayerEntity) entity);
        }
        else {
            ctx.getSource().sendFailure(new TranslationTextComponent("caiqiu.command.score.list.failure"));
        }

        return Command.SINGLE_SUCCESS;
    }
}
