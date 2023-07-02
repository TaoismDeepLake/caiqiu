package com.deeplake.caiqiu.effects;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import java.util.ArrayList;
import java.util.List;

public class BaseEffect extends Effect {
    public BaseEffect(EffectType effectType, int color) {
        super(effectType, color);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<ItemStack>();
    }
}
