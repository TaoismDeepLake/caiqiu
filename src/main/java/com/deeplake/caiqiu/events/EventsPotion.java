package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.registry.EffectRegistry;
import com.deeplake.caiqiu.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsPotion {
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        LivingEntity hurtOne = event.getEntityLiving();
        if (hurtOne.level.isClientSide)
        {
            return;
        }

        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity)
        {
            LivingEntity attacker = (LivingEntity) source.getEntity();
            //canChangeDimensions = isNonBoss. Don't Ask Why.
            //fishing_bobber is a boss, too.
            if (EntityUtil.isBoss(hurtOne))
            {
                if (attacker.getEffect(EffectRegistry.B_PURE_WATER) != null)
                {
                    event.setAmount(event.getAmount() * 2);
                }
            }

            if (EntityUtil.isBoss(attacker))
            {
                if (hurtOne.getEffect(EffectRegistry.B_MANTLE) != null)
                {
                    event.setAmount(event.getAmount() / 2f);
                }
            }
        }
    }

}
