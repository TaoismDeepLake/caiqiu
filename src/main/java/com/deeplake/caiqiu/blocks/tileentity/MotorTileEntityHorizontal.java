package com.deeplake.caiqiu.blocks.tileentity;

import com.deeplake.caiqiu.registry.TileEntityRegistry;
import net.minecraft.util.math.vector.Vector3i;

public class MotorTileEntityHorizontal extends MotorTileEntityBase {
    public MotorTileEntityHorizontal() {
        super(TileEntityRegistry.MOTOR_H.get());
    }

    public Vector3i getOffset()
    {
        if (isPositiveDirection)
        {
            return new Vector3i(1, 0, 0);
        }
        else {
            return new Vector3i(-1, 0, 0);
        }
    }
}
