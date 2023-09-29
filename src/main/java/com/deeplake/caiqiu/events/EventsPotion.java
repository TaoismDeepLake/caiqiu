package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
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
            if (attacker.hasEffect(EffectRegistry.DUSK_SYNDROME.get()) && hurtOne instanceof PlayerEntity)
            {
                //only melee damage
                if (!source.isProjectile() &&
                        !source.isExplosion() &&
                    !source.isFire() &&
                    !source.isMagic())
                {
                    event.setAmount(event.getAmount()+40);
                }
            }
        }
    }

}
