package com.deeplake.caiqiu.command;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.events.EventsClassTrial;
import com.deeplake.caiqiu.util.CommonFunctions;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.server.CustomServerBossInfo;
import net.minecraft.server.CustomServerBossInfoManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;

import static com.deeplake.caiqiu.events.EventsClassTrial.RES_CLASS_TRIAL;

public class CommandClassTrial {

    public static final String TRIAL_START_TITLE = "caiqiu.command.class_trial.start.title";
    public static final String MAX_SET_MSG = "caiqiu.command.class_trial.max.set.msg";
    public static final String CUR_SET_MSG = "caiqiu.command.class_trial.cur.set.msg";
    public static final String TRIAL_PAUSE_MSG = "caiqiu.command.class_trial.pause.msg";
    public static final String TRIAL_RESUME_MSG = "caiqiu.command.class_trial.resume.msg";
    public static final String TRIAL_END_MSG = "caiqiu.command.class_trial.end.msg";

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> commandBuilder = Commands.literal("caiqiu-classtrial")
                .requires(s -> s.hasPermission(2))
//                .then(Commands.literal("help")
//                        .executes(CommandSearchPhase::printHelp))
                .then(Commands.literal("begin").executes(CommandClassTrial::searchStart))
                .then(Commands.literal("pause").executes(CommandClassTrial::searchPause))
                .then(Commands.literal("end").executes(CommandClassTrial::searchEnd))
                .then(Commands.literal("max")
                        .then(Commands.argument("ticks", IntegerArgumentType.integer())
                                .executes(CommandClassTrial::searchSetMax))
                )
                .then(Commands.literal("cur")
                        .then(Commands.argument("ticks", IntegerArgumentType.integer())
                                .executes(CommandClassTrial::searchSetCur))
                )
                ;

        LiteralCommandNode<CommandSource> command = dispatcher.register(commandBuilder);
        dispatcher.register(Commands.literal("cqclasstrial").redirect(command));
        dispatcher.register(Commands.literal("cqcaipan").redirect(command));
    }

    private static int searchStart(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        CommandSource source = ctx.getSource();
        MinecraftServer server = source.getServer();
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        CommonFunctions.showTitle(source,
                new TranslationTextComponent(TRIAL_START_TITLE), title
                );
        for (ServerPlayerEntity player: server.getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW,player, TRIAL_START_TITLE);
        }

        server.setPvpAllowed(false);
        EventsClassTrial.BeginTrial(server);
        ctx.getSource().sendSuccess(new TranslationTextComponent("caiqiu.command.class_trial.set.success"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int searchEnd(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        String key = TRIAL_END_MSG;
        CommandSource source = ctx.getSource();
        MinecraftServer server = source.getServer();

        EventsClassTrial.EndTrial(server);
        server.setPvpAllowed(true);
        ctx.getSource().sendSuccess(new TranslationTextComponent(key),false);
        return Command.SINGLE_SUCCESS;
    }

    private static int searchPause(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        String msg = TRIAL_PAUSE_MSG;
        if (EventsClassTrial.GetTrialStatus() == EventsClassTrial.EnumClassTrialStatus.PAUSED) {
            msg = TRIAL_RESUME_MSG;
        }
        for (ServerPlayerEntity player: ctx.getSource().getServer().getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW,player, msg);
        }
        CommonFunctions.showTitle(ctx.getSource(),
                new TranslationTextComponent(msg), title
        );
        ctx.getSource().sendSuccess(new TranslationTextComponent(msg),false);
        EventsClassTrial.SwitchTrialPause();
        return Command.SINGLE_SUCCESS;
    }

    private static int searchSetCur(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        int ticks = IntegerArgumentType.getInteger(ctx, "ticks");
        EventsClassTrial.SetCurTime(ticks);
        ctx.getSource().sendSuccess(
                new TranslationTextComponent(CUR_SET_MSG,
                        ticks,
                        EventsClassTrial.getHours(ticks),
                        EventsClassTrial.getMin(ticks),
                        EventsClassTrial.getSec(ticks)
                ),false
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int searchSetMax(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        STitlePacket.Type title = STitlePacket.Type.TITLE;
        int ticks = IntegerArgumentType.getInteger(ctx, "ticks");
        EventsClassTrial.SetMaxTime(ticks);
        ctx.getSource().sendSuccess(
                new TranslationTextComponent(MAX_SET_MSG,
                        ticks,
                        EventsClassTrial.getHours(ticks),
                        EventsClassTrial.getMin(ticks),
                        EventsClassTrial.getSec(ticks)
                ),false
        );

        return Command.SINGLE_SUCCESS;
    }

    //--------------------------------------
    public static CustomServerBossInfo getBossBar(MinecraftServer server){
        CustomServerBossInfo customserverbossinfo = server.getCustomBossEvents().get(RES_CLASS_TRIAL);
        if (customserverbossinfo == null) {
            CustomServerBossInfoManager customserverbossinfomanager = server.getCustomBossEvents();
            CustomServerBossInfo info = customserverbossinfomanager.create(RES_CLASS_TRIAL, new TranslationTextComponent(IdlFramework.MOD_ID + ".bossbar.class_trial"));
            info.setColor(BossInfo.Color.WHITE);
            return info;

        } else {
            return customserverbossinfo;
        }
    }
}
