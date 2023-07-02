package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.items.weapons.MongolianSword;
import com.deeplake.caiqiu.registry.BlockRegistry;
import com.deeplake.caiqiu.util.AdvancementUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsAchv {

    @SubscribeEvent
    public static void onPlayerDig(BlockEvent.BreakEvent event)
    {
        PlayerEntity playerEntity = event.getPlayer();
        if (playerEntity.level.isClientSide)
        {
            return;
        }

        Block block = event.getState().getBlock();
        boolean isCorrectBlock =
                block == BlockRegistry.BREAKABLE_WALL.get() ||
                block == BlockRegistry.COVERED_WALL.get();

        if (playerEntity.getMainHandItem().getItem() instanceof MongolianSword && isCorrectBlock)
        {
            AdvancementUtil.giveAdvancement(event.getPlayer(), AdvancementUtil.ACHV_BREAKABLE_WALL);
        }
    }
}
