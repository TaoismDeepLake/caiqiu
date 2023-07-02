package com.deeplake.caiqiu.items;

import com.deeplake.caiqiu.items.tabs.TabList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class BaseItemFood extends BaseItemIDF {
    static final Food defaultFood = (new Food.Builder().
            saturationMod(8).
            nutrition(4).
            alwaysEat().
            effect(() -> new EffectInstance(Effects.ABSORPTION, 3 * 20, 2), 0.5f))
            .build();

    public static final int buffLen = 180;


    public BaseItemFood(Food food) {
        super(new Properties().tab(TabList.MISC_GROUP).food(food));
    }

    public BaseItemFood(Food food, Rarity rarity) {
        super(new Properties().tab(TabList.MISC_GROUP).food(food).rarity(rarity));
    }

    public BaseItemFood() {
        super(new Properties().tab(ItemGroup.TAB_FOOD).food(defaultFood));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity && !world.isClientSide)
        {
            Food food = stack.getItem().getFoodProperties();
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
        }

        return super.finishUsingItem(stack, world, livingEntity);
    }
}
