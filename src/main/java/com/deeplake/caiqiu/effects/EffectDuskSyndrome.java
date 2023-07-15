package com.deeplake.caiqiu.effects;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

import java.util.UUID;

public class EffectDuskSyndrome extends BaseEffect{

    public static final String UUID_STR = "7cf25a1c-6768-4836-8d24-ec64ed2a4ef7";
    public static final UUID UUID = java.util.UUID.fromString(UUID_STR);

    public EffectDuskSyndrome(EffectType effectType, int color) {
        super(effectType, color);
        //
        addAttributeModifier(Attributes.MAX_HEALTH,
                UUID_STR,
                (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
