package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsClassTrial {
    public enum EnumClassTrialStatus{
        NONE,
        ONGOING,
        PAUSED
    }

    static EnumClassTrialStatus mStatus = EnumClassTrialStatus.NONE;

    public static EnumClassTrialStatus GetTrialStatus()
    {
        return mStatus;
    }

    public static void BeginTrial()
    {
        mStatus = EnumClassTrialStatus.ONGOING;
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
