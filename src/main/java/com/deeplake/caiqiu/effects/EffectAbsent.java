package com.deeplake.caiqiu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;

public class EffectAbsent extends BaseEffect{
    public EffectAbsent(EffectType effectType, int color) {
        super(effectType, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "90af16a2-d01d-4c3e-9d6c-7990cea960af",
                (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    //copied from vanilla weakness
    public double getAttributeModifierValue(int level, AttributeModifier modifier) {
        return super.getAttributeModifierValue(level, modifier);
    }

    public void applyEffectTick(LivingEntity livingEntity, int levelMinusOne) {
        //hunger 1
        if (livingEntity instanceof PlayerEntity) {
            ((PlayerEntity) livingEntity).causeFoodExhaustion(0.005F);
        }
    }
}
