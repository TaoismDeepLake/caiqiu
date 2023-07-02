package com.deeplake.caiqiu.items.weapons;

import com.deeplake.caiqiu.entities.EntityRedArrow;
import com.deeplake.caiqiu.registry.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.World;

public class ItemRedBow extends BaseMJDSBow {
    public ItemRedBow(Properties p_i50040_1_) {
        super(p_i50040_1_);
    }

    public AbstractArrowEntity newArrow(World world, LivingEntity shooter)
    {
        return new EntityRedArrow(EntityRegistry.RED_ARROW.get(), world, shooter);
    }
}
