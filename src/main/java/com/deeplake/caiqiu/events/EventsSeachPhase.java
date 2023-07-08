package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsSeachPhase {
    public static boolean isSearching = false;

    //if isSearching, stops the player from tossing items
    @SubscribeEvent
    public static void onTossEvent(net.minecraftforge.event.entity.item.ItemTossEvent event) {
        //If you cancel the event, the item will disappear!
        if (isSearching) {
            ItemEntity entity = event.getEntityItem();
            entity.setNoPickUpDelay();
        }
    }

    //if isSearching, stops the player from picking items
    @SubscribeEvent
    public static void onPickEvent(EntityItemPickupEvent event) {
        //note that ItemPickupEvent is not cancelable and is fired after EntityItemPickupEvent
        //and improper handling will cause it to conflict the prev one, which relies on instant picking up.
        if (isSearching && event.getItem().tickCount > 10) {
            event.setResult(Event.Result.DENY);
            event.getItem().setPickUpDelay(100);
        }
    }

    //stops the player from getting items from boxes
    @SubscribeEvent
    public static void onBoxEvent(net.minecraftforge.event.entity.player.PlayerContainerEvent.Open event) {
        if (isSearching) {
            event.setCanceled(true);
        }
    }
}
