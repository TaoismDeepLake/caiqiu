package com.deeplake.caiqiu.registry;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.items.BaseItemIDF;
import com.deeplake.caiqiu.items.ItemTeleportCommand;
import com.deeplake.caiqiu.items.tabs.TabList;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IdlFramework.MOD_ID);

    public static final Item.Properties UNCOMMON_PROP = new Item.Properties().rarity(Rarity.UNCOMMON).tab(TabList.MISC_GROUP);
    public static final Item.Properties RARE_PROP = new Item.Properties().rarity(Rarity.RARE).tab(TabList.MISC_GROUP);
    public static final Item.Properties EPIC_PROP = new Item.Properties().rarity(Rarity.EPIC).tab(TabList.MISC_GROUP);

    public static final RegistryObject<Item> ICON = ITEMS.register("icon", () -> new BaseItemIDF(UNCOMMON_PROP));
    public static final RegistryObject<Item> VOTE_REMOTE = ITEMS.register("vote_remote", () -> new BaseItemIDF(UNCOMMON_PROP));
    public static final RegistryObject<Item> TELEPORT_COMMAND = ITEMS.register("teleport_command", () -> new ItemTeleportCommand(EPIC_PROP));
    public static final RegistryObject<Item> RECALL_LIGHT = ITEMS.register("recall_light", () -> new BaseItemIDF(UNCOMMON_PROP));
}
