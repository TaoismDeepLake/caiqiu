package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsClassTrial {
    public enum EnumClassTrialStatus{
        NONE,
        ONGOING,
        PAUSED
    }

    private static HashMap<UUID, Integer> accuseDict = new HashMap<UUID, Integer>();

    static EnumClassTrialStatus mStatus = EnumClassTrialStatus.NONE;

    public static EnumClassTrialStatus GetTrialStatus()
    {
        return mStatus;
    }

    public static void BeginTrial()
    {
        mStatus = EnumClassTrialStatus.ONGOING;
        accuseDict.clear();
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

    public static void EndTrial()
    {
        mStatus = EnumClassTrialStatus.NONE;
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
}
