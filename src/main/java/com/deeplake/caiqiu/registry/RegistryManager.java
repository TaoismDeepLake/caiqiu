package com.deeplake.caiqiu.registry;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.blocks.INeedInit;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class RegistryManager {
    public static List<INeedInit> NEED_LIST = new ArrayList<>();
    public static void RegisterAll()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.BLOCKS.register(eventBus);
        ItemRegistry.ITEMS.register(eventBus);
        EffectRegistry.EFFECT_LIST.register(eventBus);
        EntityRegistry.ENTITY_TYPES.register(eventBus);
        TileEntityRegistry.TILE_ENTITIES.register(eventBus);

        eventBus.addGenericListener(Effect.class, EffectRegistry::registerAllPotion);
    }

    public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> forgeRegistry, ResourceLocation resourceLocation, IForgeRegistryEntry<T> entry) {
        forgeRegistry.register(entry.setRegistryName(resourceLocation));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String id, IForgeRegistryEntry<V> entry) {
        register(reg, new ResourceLocation(IdlFramework.MOD_ID, id), entry);
    }

}
