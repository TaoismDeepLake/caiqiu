package com.deeplake.caiqiu.blocks;

import com.deeplake.caiqiu.blocks.tileentity.MotorTileEntityVertical;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockMotorY extends BaseBlockMotor {
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MotorTileEntityVertical();
    }
}
