package com.deeplake.caiqiu.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.CustomServerBossInfo;

import java.util.Collection;

public class DesignUtil {

    public static boolean isNearBoss(PlayerEntity entity) {
        //should improve. not good

        if (entity.getServer() != null)
        {
            Collection<CustomServerBossInfo> collection = entity.getServer().getCustomBossEvents().getEvents();
//            for (CustomServerBossInfo info: collection
//                 ) {
//                if (info.shouldPlayBossMusic())
//            } player list is private, darn

            return  !collection.isEmpty();
        }
        return false;
    }

    public static boolean isInMJDS(Entity entity)
    {
        return false;
    }

}
