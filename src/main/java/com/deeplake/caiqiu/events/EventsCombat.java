package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.util.AdvancementUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsCombat {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLevel(PlayerXpEvent.LevelChange event)
    {
        World world = event.getEntityLiving().level;
        if (world.isClientSide)
        {
            return;
        }

        event.getPlayer().getFoodData().setFoodLevel(20);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEXP(PlayerXpEvent.XpChange event)
    {
        World world = event.getEntityLiving().level;
        if (world.isClientSide)
        {
            return;
        }

        if (AdvancementUtil.hasAdvancement((ServerPlayerEntity) event.getEntityLiving(), AdvancementUtil.ACHV_JAR))
        {
            event.setAmount(event.getAmount() * 2);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onHit(LivingHurtEvent event)
    {
        World world = event.getEntityLiving().level;
        if (world.isClientSide)
        {
            return;
        }

        LivingEntity hurtOne = event.getEntityLiving();

        if (event.getSource().getEntity() instanceof LivingEntity)
        {
            LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

            if (attacker instanceof ServerPlayerEntity)
            {
                if (AdvancementUtil.hasAdvancement((ServerPlayerEntity) attacker, AdvancementUtil.ACHV_SABRE))
                {
                    event.setAmount(event.getAmount() * 2);
                }
            }
        }

        if (hurtOne instanceof ServerPlayerEntity)
        {
            if (event.getSource() == DamageSource.HOT_FLOOR && AdvancementUtil.hasAdvancement((ServerPlayerEntity) hurtOne, AdvancementUtil.ACHV_BOOTS))
            {
                event.setCanceled(true);
            }
        }

    }
}
