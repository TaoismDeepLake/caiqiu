package com.deeplake.caiqiu.render;

import com.deeplake.caiqiu.IdlFramework;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = IdlFramework.MOD_ID)
public class ClientSetup {
    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent evt) {
        Minecraft.getInstance().getEntityRenderDispatcher().renderers.values().forEach(r -> {
            if (r instanceof LivingRenderer) {
                attachRenderLayers((LivingRenderer<?, ?>) r);
            }
        });
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values().forEach(ClientSetup::attachRenderLayers);
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void attachRenderLayers(LivingRenderer<T, M> renderer) {
        renderer.addLayer(new DuskLayer<>(renderer));
    }
}
