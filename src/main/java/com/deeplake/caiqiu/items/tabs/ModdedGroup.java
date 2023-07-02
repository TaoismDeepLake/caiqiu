package com.deeplake.caiqiu.items.tabs;

import com.deeplake.caiqiu.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModdedGroup extends ItemGroup {
    public ModdedGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.ICON.get());
        //return new ItemStack(ItemRegistry.testIngot2);
    }
}
