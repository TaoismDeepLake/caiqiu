package com.deeplake.caiqiu.blocks.tileentity;

import com.deeplake.caiqiu.registry.TileEntityRegistry;
import net.minecraft.util.math.vector.Vector3i;

public class MotorTileEntityVertical extends MotorTileEntityBase{
    public MotorTileEntityVertical() {
        super(TileEntityRegistry.MOTOR_V.get());
    }

    public Vector3i getOffset()
    {
        if (isPositiveDirection)
        {
            return new Vector3i(0, 1, 0);
        }
        else {
            return new Vector3i(0, -1, 0);
        }
    }
}
