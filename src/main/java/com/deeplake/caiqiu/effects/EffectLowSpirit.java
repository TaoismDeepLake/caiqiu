package com.deeplake.caiqiu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;

public class EffectLowSpirit extends BaseEffect{
    public EffectLowSpirit(EffectType effectType, int color) {
        super(effectType, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "ca8e7eab-a925-42b1-a0c0-0f466f391919",
                (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE,
                "ca8e7eab-a925-42b1-a0c0-0f466f391919",
                0.0D,
                AttributeModifier.Operation.ADDITION);
    }

    //copied from vanilla weakness
    public double getAttributeModifierValue(int level, AttributeModifier modifier) {
        if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
        {
            return -4;
        }
        return super.getAttributeModifierValue(level, modifier);
    }

    public void applyEffectTick(LivingEntity livingEntity, int level) {
        //Saturation 1
        if (livingEntity instanceof PlayerEntity) {
            if (!livingEntity.level.isClientSide) {
                ((PlayerEntity)livingEntity).getFoodData().eat(1, 1.0F);
            }
        }
    }
}
