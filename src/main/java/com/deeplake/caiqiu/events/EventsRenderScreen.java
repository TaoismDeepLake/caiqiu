package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.registry.EffectRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID, value = Dist.CLIENT)
public class EventsRenderScreen {
    //makes the screen red
    @SubscribeEvent
    public static void onRender(net.minecraftforge.client.event.RenderGameOverlayEvent.Pre event) {
//        if (EventsSeachPhase.isSearching) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(EffectRegistry.DESPAIR_SICKNESS.get())){
//            event.getMatrixStack().pushPose();
            //make the screen red
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(1.0F, 0.0F, 0.0F, 0.3F);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
//            event.getMatrixStack().popPose();
        }
    }
}
