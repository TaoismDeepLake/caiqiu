package com.deeplake.caiqiu.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsPickupItem {
    @SubscribeEvent
    public static void onPickUp(EntityItemPickupEvent event)
    {
        World world = event.getItem().level;
        if (world.isClientSide)
        {
            return;
        }

        ItemStack stack = event.getItem().getItem();
        Item itemType = stack.getItem();
        PlayerEntity playerEntity = event.getPlayer();

    }
}
