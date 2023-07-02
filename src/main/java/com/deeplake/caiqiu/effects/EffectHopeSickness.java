package com.deeplake.caiqiu.effects;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

public class EffectHopeSickness extends BaseEffect{
    public EffectHopeSickness(EffectType effectType, int color) {
        super(effectType, color);
        addAttributeModifier(Attributes.MAX_HEALTH,
                "76ca04b3-7ca6-4597-8e6c-3be5d5501d84",
                1.0D,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE,
                "76ca04b3-7ca6-4597-8e6c-3be5d5501d84",
                0.0D,
                AttributeModifier.Operation.ADDITION);
    }

    //copied from vanilla weakness
    public double getAttributeModifierValue(int level, AttributeModifier modifier) {
        if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
        {
            return -8;
        }
        return super.getAttributeModifierValue(level, modifier);
    }
}
