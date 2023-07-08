package com.deeplake.caiqiu.blocks;

import com.deeplake.caiqiu.blocks.te.TileEntityScoreMeasure;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockScoreMeasure extends BaseBlockIDF{
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityScoreMeasure();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isClientSide)
        {
            return ActionResultType.SUCCESS;
        }
        else {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof TileEntityScoreMeasure)
            {
                ((TileEntityScoreMeasure) te).interact(playerEntity);
            }
            return ActionResultType.SUCCESS;
        }
    }
}
