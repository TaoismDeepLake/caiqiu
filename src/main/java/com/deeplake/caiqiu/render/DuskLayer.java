package com.deeplake.caiqiu.render;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.effects.EffectDuskSyndrome;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;

public class DuskLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    public final IEntityRenderer<T, M> rendererPublic;

    public static final ResourceLocation TEXTURE = new ResourceLocation(IdlFramework.MOD_ID, "textures/gui/overlay/effect/dusk.png");

    public DuskLayer(IEntityRenderer<T, M> renderer) {
        super(renderer);
        rendererPublic = renderer;
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getAttribute(Attributes.MAX_HEALTH).getModifier(EffectDuskSyndrome.UUID) == null) { //Movement speed
            return;
        }
        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.entitySolid(TEXTURE));
        this.getParentModel().renderToBuffer(stack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}

