package com.deeplake.caiqiu.datagen;

import com.deeplake.caiqiu.IdlFramework;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGen {

    @SubscribeEvent
    public static void gen(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        //if (event.includeClient()) {
            IdlFramework.LogWarning("CLIENT DATAGEN");
            generator.addProvider(new ModLangProvider(generator, IdlFramework.MOD_ID, "zh_cn"));
            //generator.addProvider(new ModModelProvider(generator, IdlFramework.MOD_ID, "models/block"));
        //}

        //generator.addProvider();

        //event.getGenerator().addProvider();
    }
}
