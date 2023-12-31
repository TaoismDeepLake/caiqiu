package com.deeplake.caiqiu;

import com.deeplake.caiqiu.blocks.INeedInit;
import com.deeplake.caiqiu.command.CommandClassTrial;
import com.deeplake.caiqiu.command.CommandScore;
import com.deeplake.caiqiu.command.CommandScoreSuper;
import com.deeplake.caiqiu.command.CommandSearchPhase;
import com.deeplake.caiqiu.registry.RegistryManager;
import com.deeplake.caiqiu.worldgen.infra.InitWorldGen;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(IdlFramework.MOD_ID)
public class IdlFramework {
    public static final String MOD_ID = "caiqiu";

    public static final Logger logger = LogManager.getLogger();
    public static final boolean SHOW_WARN = true;

    public IdlFramework(){
        RegistryManager.RegisterAll();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, InitWorldGen::onBiomeLoading);
        MinecraftForge.EVENT_BUS.register(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(this::registerCommands);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        for (INeedInit elem : RegistryManager.NEED_LIST) {
            elem.init();
        }

        event.enqueueWork(() ->
        {
//            GlobalEntityTypeAttributes.put(EntityRegistry.MJDS_SKELETON.get(), EntityMJDSSkeleton.createAttributes().build());
//            GlobalEntityTypeAttributes.put(EntityRegistry.MJDS_SLIME.get(), MonsterEntity.createMonsterAttributes().build());
//            GlobalEntityTypeAttributes.put(EntityRegistry.MJDS_BAT.get(), MonsterEntity.createMonsterAttributes().build());
//            GlobalEntityTypeAttributes.put(EntityRegistry.MJDS_BUSH.get(), MonsterEntity.createMonsterAttributes().build());
//            GlobalEntityTypeAttributes.put(EntityRegistry.MJDS_WORM.get(), MonsterEntity.createMonsterAttributes().build());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        logger.info("FMLClientSetupEvent:Got game settings {}", event.getMinecraftSupplier().get().options);
//        RenderTypeLookup.setRenderLayer(BlockRegistry.SP_GLASS.get(), RenderType.translucent());
        //RenderTypeLookup.setRenderLayer(BlockRegistry.LADDER.get(), RenderType.cutout());

        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        //int getColor(BlockState block, @Nullable IBlockDisplayReader p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_);
//        blockColors.register((blockState, iBlockDisplayReader, pos, i) -> iBlockDisplayReader != null && pos != null ? BiomeColors.getAverageGrassColor(iBlockDisplayReader, pos) : -1, BlockRegistry.CASTLE_BG.get());
    }

    public static void LogWarning(String str, Object...args)
    {
        if (SHOW_WARN)
        {
            logger.warn(String.format(str, args));
        }
    }

    public static void LogWarning(String str)
    {
        if (SHOW_WARN)
        {
            logger.warn(str);
        }
    }

    public static void Log(String str)
    {
        //if (ModConfig.GeneralConf.LOG_ON)
        {
            logger.info(str);
        }
    }

    public static void Log(String str, Object...args)
    {
        //if (ModConfig.GeneralConf.LOG_ON)
        {
            logger.info(String.format(str, args));
        }
    }

    public void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        CommandScore.register(dispatcher);
        CommandScoreSuper.register(dispatcher);
        CommandSearchPhase.register(dispatcher);
        CommandClassTrial.register(dispatcher);
    }
}
