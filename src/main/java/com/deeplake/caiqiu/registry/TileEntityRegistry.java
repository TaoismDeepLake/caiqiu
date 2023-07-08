package com.deeplake.caiqiu.registry;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.blocks.te.TileEntityScoreMeasure;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegistry {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, IdlFramework.MOD_ID);
    public static final RegistryObject<TileEntityType<TileEntityScoreMeasure>> SCORE_MEASURE =
            TILE_ENTITIES.register("te_score_measure",
                    () -> TileEntityType.Builder.of(TileEntityScoreMeasure::new,
                            BlockRegistry.SCORE_MEASURE.get()).build(null));
//
//    public static final RegistryObject<TileEntityType<MotorTileEntityHorizontal>> MOTOR_H =
//            TILE_ENTITIES.register("te_motor_horizontal",
//                    () -> TileEntityType.Builder.of(MotorTileEntityHorizontal::new,
//                            BlockRegistry.BLOCK_MOTOR_X.get()).build(null));
}
