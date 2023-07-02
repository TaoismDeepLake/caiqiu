package com.deeplake.caiqiu.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BaseBlockMotor extends BaseBlockMJDS {

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return null;
    }

//    @Override
//    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
//        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
//            ObsidianCounterTileEntity MOTOR_V = (ObsidianCounterTileEntity) worldIn.getTileEntity(pos);
//            int counter = MOTOR_V.increase();
//            TranslationTextComponent translationTextComponent = new TranslationTextComponent("message.neutrino.counter", counter);
//            player.sendStatusMessage(translationTextComponent, false);
//        }
//        return ActionResultType.SUCCESS;
//    }
}
