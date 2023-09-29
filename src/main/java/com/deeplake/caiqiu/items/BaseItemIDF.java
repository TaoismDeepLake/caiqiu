package com.deeplake.caiqiu.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BaseItemIDF extends Item {
    //Idealland Style
    public Properties properties;

    public BaseItemIDF() {
        this(new Properties().tab(ItemGroup.TAB_BREWING));
    }

    public BaseItemIDF(Properties p_i48487_1_) {
        super(p_i48487_1_);
        properties = p_i48487_1_;
    }

    public void activateCooldown(ItemStack stack, PlayerEntity playerEntity)
    {
        playerEntity.getCooldowns().addCooldown(stack.getItem(), 5);
    }
}
