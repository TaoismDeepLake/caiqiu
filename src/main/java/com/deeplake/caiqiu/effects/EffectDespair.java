package com.deeplake.caiqiu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

public class EffectDespair extends BaseEffect{
    public EffectDespair(EffectType effectType, int color) {
        super(effectType, color);
    }

    @Override
    public void applyEffectTick(LivingEntity target, int p_76394_2_) {
//        if (target.isAlive() && target instanceof ServerPlayerEntity)
//            AoAPackets.messagePlayer((ServerPlayerEntity)target, new ScreenOverlayPacket(new ResourceLocation(AdventOfAscension.MOD_ID, "textures/gui/overlay/effect/circles.png"), 150));

    }
}
