package com.deeplake.caiqiu.entities.renderer;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.entities.EntityMJDSMonsterBush;
import com.deeplake.caiqiu.entities.model.ModelMajouPlant;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BushRenderer extends MobRenderer<EntityMJDSMonsterBush, ModelMajouPlant> {
    private static ResourceLocation BUSH_RES =
            new ResourceLocation(IdlFramework.MOD_ID, "textures/block/mjds_plant.png");

    public BushRenderer(EntityRendererManager p_i50961_1_, ModelMajouPlant p_i50961_2_, float p_i50961_3_) {
        super(p_i50961_1_, p_i50961_2_, 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMJDSMonsterBush p_110775_1_) {
        return BUSH_RES;
    }
}
