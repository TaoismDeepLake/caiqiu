package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.util.CommonFunctions;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.ParsedCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsNoWhisper {
    public static final String NO_CHAT_ALLOWED = IdlFramework.MOD_ID + ".msg.no_chat_allowed";
    //subscribe CommandEvent and stops the player from use "/w","/tell" and "/msg" command
    @SubscribeEvent
    public static void onCommandEvent(net.minecraftforge.event.CommandEvent event) {
        ParseResults<CommandSource> parseResults = event.getParseResults();
        if (!parseResults.getContext().getNodes().isEmpty()) {
//        if (parseResults.getContext().getRange().getStart() == 0) {
            ParsedCommandNode<CommandSource> node = parseResults.getContext().getNodes().get(0);
            IdlFramework.Log("using: "+node.getNode().getName());

            String command = parseResults.getReader().getString();
            if (command.startsWith("/w ") || command.startsWith("/tell ") || command.startsWith("/msg ") || command.startsWith("/tellraw ")) {
                event.setCanceled(true);
                Entity sender = parseResults.getContext().getSource().getEntity();
                if (sender instanceof PlayerEntity)
                {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (LivingEntity) sender, NO_CHAT_ALLOWED);
                }
            }
        }
    }
}
