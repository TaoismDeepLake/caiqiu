package com.deeplake.caiqiu.registry;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.effects.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class EffectRegistry {
//    public static List<Effect> EFFECT_LIST = new ArrayList<>();
//    public static final Effect B_PURE_WATER = new BaseEffect(EffectType.BENEFICIAL, 0x777733);
//    public static final Effect B_MANTLE = new BaseEffect(EffectType.BENEFICIAL, 0x777733);
    public static final DeferredRegister<Effect> EFFECT_LIST = DeferredRegister.create(ForgeRegistries.POTIONS, IdlFramework.MOD_ID);

    public static final RegistryObject<Effect> DESPAIR_SICKNESS = register("despair", () -> new BaseEffect(EffectType.HARMFUL,0x777733));
    public static final RegistryObject<Effect> SWAMP_CURSE = register("swamp_curse", () -> new BaseEffect(EffectType.HARMFUL,0x777733));
    public static final RegistryObject<Effect> DUSK_SYNDROME = register("dusk_syndrome", () -> new EffectDuskSyndrome(EffectType.HARMFUL,0x777733));
    public static final RegistryObject<Effect> RECALL_LIGHT = register("recall_light", () -> new BaseEffect(EffectType.HARMFUL,0x777733));
    public static final RegistryObject<Effect> HOPE_SICKNESS = register("hope_sickness", () -> new EffectHopeSickness(EffectType.HARMFUL,0x777733));
    public static final RegistryObject<Effect> INCUBATION = register("incubation", () -> new BaseEffect(EffectType.HARMFUL,0x777733));

    public static final RegistryObject<Effect> LOW_SPIRIT = register("low_spirit", () -> new EffectLowSpirit(EffectType.HARMFUL,0x777733));
    public static final RegistryObject<Effect> ABSENT = register("absent", () -> new EffectAbsent(EffectType.HARMFUL,0x777733));

    public static void registerAllPotion(RegistryEvent.Register<Effect> event)
    {
        IForgeRegistry<Effect> registry = event.getRegistry();
//        RegistryManager.register(registry, "pure_water", B_PURE_WATER);
//        RegistryManager.register(registry, "mantle", B_MANTLE);
    }

    public static RegistryObject<Effect> register(final String name, final Supplier<? extends Effect> sup) {
        RegistryObject<Effect> effectRegistryObject = EFFECT_LIST.register(name, sup);
        return effectRegistryObject;
    }

}
