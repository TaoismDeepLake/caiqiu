package com.deeplake.caiqiu.datagen;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Function;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(DataGenerator generator, String modid, String folder, Function factory, ExistingFileHelper existingFileHelper) {
        super(generator, modid, folder, factory, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ResourceLocation texture = new ResourceLocation(IdlFramework.MOD_ID, "achv_box");

        for (RegistryObject<Block> reg :
                BlockRegistry.BLOCKS.getEntries()) {
            cubeAll(reg.getId().toString(), texture);
        }
    }

    @Override
    public String getName() {
        return "Models";
    }
}
