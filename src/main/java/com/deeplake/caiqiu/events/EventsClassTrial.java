package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.command.CommandClassTrial;
import com.deeplake.caiqiu.util.CommonDef;
import com.deeplake.caiqiu.util.CommonFunctions;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.server.CustomServerBossInfo;
import net.minecraft.server.CustomServerBossInfoManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.UUID;

import static com.deeplake.caiqiu.command.CommandClassTrial.TRIAL_END_MSG;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsClassTrial {
    public enum EnumClassTrialStatus{
        NONE,
        ONGOING,
        PAUSED
    }

    public static final int DEFAULT_TIME = 3600 * 20;
    static int curTimeLeft = 0;
    static int curTimeMax = DEFAULT_TIME;

    private static HashMap<UUID, Integer> accuseDict = new HashMap<UUID, Integer>();

    static EnumClassTrialStatus mStatus = EnumClassTrialStatus.NONE;

    public static EnumClassTrialStatus GetTrialStatus()
    {
        return mStatus;
    }

    public static final ResourceLocation RES_CLASS_TRIAL = new ResourceLocation(IdlFramework.MOD_ID,"class_trial_bar");
    public static final String BOSSBAR_NAME = IdlFramework.MOD_ID + ".boss_bar.class_trial";
    public static final String BOSSBAR_PAUSED = IdlFramework.MOD_ID + ".boss_bar.class_trial.paused";

    public static void Init(MinecraftServer server)
    {
        CustomServerBossInfoManager customserverbossinfomanager = server.getCustomBossEvents();
        if (customserverbossinfomanager.get(RES_CLASS_TRIAL) == null) {
            CustomServerBossInfo info = customserverbossinfomanager.create(RES_CLASS_TRIAL, new TranslationTextComponent(IdlFramework.MOD_ID + ".bossbar.class_trial"));
            info.setColor(BossInfo.Color.WHITE);
        }
    }

    public static void SetMaxTime(int maxTime)
    {
        curTimeMax = maxTime;
    }

    public static void SetCurTime(int time)
    {
        curTimeLeft = time;
    }

    public static void BeginTrial(MinecraftServer server)
    {
        mStatus = EnumClassTrialStatus.ONGOING;
        curTimeLeft = curTimeMax;
        accuseDict.clear();
        RefreshBossBar(server);
    }

    public static int GetAccuseCount(UUID uuid)
    {
        return accuseDict.getOrDefault(uuid, 0);
    }

    public static void AddAccuseCount(UUID uuid)
    {
        int count = accuseDict.getOrDefault(uuid, 0);
        count++;
        accuseDict.put(uuid, count);
    }

    public static void EndTrial(MinecraftServer server)
    {
        String key = TRIAL_END_MSG;
        for (ServerPlayerEntity player: server.getPlayerList().getPlayers()) {
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW,player, key);
        }
        try {
            CommonFunctions.showTitle(server,
                    new TranslationTextComponent(key), STitlePacket.Type.TITLE
            );
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
        mStatus = EnumClassTrialStatus.NONE;
        RefreshBossBar(server);
    }

    public static void PauseTrial()
    {
        mStatus = EnumClassTrialStatus.PAUSED;
    }

    public static void ResumeTrial()
    {
        mStatus = EnumClassTrialStatus.ONGOING;
    }

    public static void SwitchTrialPause()
    {
        if (GetTrialStatus() == EnumClassTrialStatus.ONGOING)
        {
            PauseTrial();
        }
        else if (GetTrialStatus() == EnumClassTrialStatus.PAUSED)
        {
            ResumeTrial();
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER)
//                && event.world.dimensionType() == DEFAULT_OVERWORLD)
        {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            TickInTrial(server);
        }
    }


    public static void TickInTrial(MinecraftServer server)
    {
        if (server == null)
        {
            return;
        }

        if (mStatus == EnumClassTrialStatus.ONGOING)
        {
            if (curTimeLeft > 0)
            {
                curTimeLeft--;
            }
            else {
                EndTrial(server);
            }
            RefreshBossBar(server);
        }
    }

    static final int TICK_PER_HOUR = 3600 * CommonDef.TICK_PER_SECOND;
    static final int TICK_PER_MIN = 60 * CommonDef.TICK_PER_SECOND;

    public static void RefreshBossBar(MinecraftServer server)
    {
        CustomServerBossInfo bossInfo = CommandClassTrial.getBossBar(server);
        if (bossInfo!=null)
        {
            boolean showBar = curTimeLeft > 0 && mStatus != EnumClassTrialStatus.NONE;
            bossInfo.setVisible(showBar);
            if (showBar)
            {
                bossInfo.setPlayers(server.getPlayerList().getPlayers());
                //could use some optimization here
                String nameKey = mStatus == EnumClassTrialStatus.ONGOING ? BOSSBAR_NAME : BOSSBAR_PAUSED;
                int hours = getHours(curTimeLeft);
                int min = getMin(curTimeLeft);
                int sec = getSec(curTimeLeft);
                bossInfo.setName(new TranslationTextComponent(nameKey, hours, min, sec));
                bossInfo.setMax(curTimeMax);
                bossInfo.setValue(curTimeMax-curTimeLeft);
            }
        }
    }

    public static int getSec(int time) {
        return time % TICK_PER_MIN / CommonDef.TICK_PER_SECOND;
    }

    public static int getMin(int time) {
        return (time % TICK_PER_HOUR) / TICK_PER_MIN;
    }

    public static int getHours(int time) {
        return time / TICK_PER_HOUR;
    }

}
