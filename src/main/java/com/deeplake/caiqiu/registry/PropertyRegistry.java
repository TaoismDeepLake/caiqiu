package com.deeplake.caiqiu.registry;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.util.IDLNBTDef;
import com.deeplake.caiqiu.util.IDLNBTUtil;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        IdlFramework.Log("Registering property");
        event.enqueueWork(() -> ItemModelsProperties.register(ItemRegistry.TELEPORT_COMMAND.get(),
                new ResourceLocation(IdlFramework.MOD_ID, IDLNBTDef.ANCHOR_READY),
                (itemStack, clientWorld, livingEntity) ->
                    IDLNBTUtil.GetBoolean(itemStack, IDLNBTDef.ANCHOR_READY) ? 1 : 0)
                //IDLNBTUtil.GetIntAuto(livingEntity, NBTString.MJDS_EGO, 0)
        );
    }
}