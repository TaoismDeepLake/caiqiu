package com.deeplake.caiqiu.registry;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.items.tabs.TabList;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IdlFramework.MOD_ID);

    public static RegistryObject<Block> registerWithItem(final String name, final Supplier<? extends Block> sup)
    {
        return registerWithItem(name, sup, TabList.MISC_GROUP);
    }

    public static RegistryObject<Block> registerWithItem(final String name, final Supplier<? extends Block> sup, ItemGroup tab)
    {
        RegistryObject<Block> block = BLOCKS.register(name, sup);
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
        return block;
    }

// It's legal, but not with much point. The unlocalized name is still without the ITEMBLOCK_STR prefix.
//    public static RegistryObject<Block> registerWithItemSP(final String name, final Supplier<? extends Block> sup)
//    {
//        return registerWithItemSP(name, sup, TabList.MISC_GROUP);
//    }
//
//    public static RegistryObject<Block> registerWithItemSP(final String name, final Supplier<? extends Block> sup, ItemGroup tab)
//    {
//        RegistryObject<Block> block = BLOCKS.register(name, sup);
//        ItemRegistry.ITEMS.register(ITEMBLOCK_STR + name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
//        return block;
//    }

    //public static final RegistryObject<Block> CASTLE_BG = registerWithItem("castle_bg", BlockIndestructible::new);//vy'= (vy- g) * 0.98 //not transparent ver
//    public static final RegistryObject<Block> CASTLE_BG = registerWithItem("castle_bg", BlockWallGlass::new);//vy'= (vy- g) * 0.98

}
