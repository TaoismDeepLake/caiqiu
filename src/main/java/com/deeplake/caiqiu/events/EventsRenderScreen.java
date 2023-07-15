package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.registry.EffectRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID, value = Dist.CLIENT)
public class EventsRenderScreen {
//    private static final ConcurrentHashMap<ResourceLocation, Integer> overlays = new ConcurrentHashMap<ResourceLocation, Integer>();

    @SubscribeEvent
    public static void onOverlayRender(final RenderGameOverlayEvent.Post event) {

        if (event.getType() != RenderGameOverlayEvent.ElementType.HELMET)
            return;

        Minecraft mc = Minecraft.getInstance();
        MainWindow window = mc.getWindow();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableAlphaTest();

        List<ResourceLocation> overlays = new ArrayList<ResourceLocation>();
        //red
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(EffectRegistry.DESPAIR_SICKNESS.get())){
            overlays.add(new ResourceLocation(IdlFramework.MOD_ID, "textures/gui/overlay/effect/red.png"));
        }

        //blue
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(EffectRegistry.SWAMP_CURSE.get())){
            overlays.add(new ResourceLocation(IdlFramework.MOD_ID, "textures/gui/overlay/effect/blue.png"));
        }

        //halo
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(EffectRegistry.DUSK_SYNDROME.get())){
            overlays.add(new ResourceLocation(IdlFramework.MOD_ID, "textures/gui/overlay/effect/halo.png"));
        }

        for (ResourceLocation entry : overlays) {
            mc.getTextureManager().bind(entry);
            RenderSystem.color4f(1, 1, 1, 1.0f);//RGBA
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.vertex(0, window.getGuiScaledHeight(), -90).uv(0, 1).endVertex();
            buffer.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90).uv(1, 1).endVertex();
            buffer.vertex(window.getGuiScaledWidth(), 0, -90).uv(1, 0).endVertex();
            buffer.vertex(0, 0, -90).uv(0, 0).endVertex();
            tessellator.end();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1, 1, 1, 1);
    }
}
