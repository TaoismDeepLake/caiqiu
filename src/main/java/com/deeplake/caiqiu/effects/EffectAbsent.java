package com.deeplake.caiqiu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

public class EffectAbsent extends BaseEffect{
    public EffectAbsent(EffectType effectType, int color) {
        super(effectType, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "90af16a2-d01d-4c3e-9d6c-7990cea960af",
                (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void applyEffectTick(LivingEntity livingEntity, int levelMinusOne) {
        //hunger 1
        if (livingEntity instanceof PlayerEntity) {
            ((PlayerEntity) livingEntity).causeFoodExhaustion(0.005F);
            livingEntity.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 0, false, false));
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }
}
