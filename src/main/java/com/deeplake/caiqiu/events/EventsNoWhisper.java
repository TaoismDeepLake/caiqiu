package com.deeplake.caiqiu.events;

import com.deeplake.caiqiu.IdlFramework;
import com.deeplake.caiqiu.gui.ModifiedChatScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IdlFramework.MOD_ID)
public class EventsNoWhisper {
    public static final String NO_CHAT_ALLOWED = IdlFramework.MOD_ID + ".msg.no_chat_allowed";
    //subscribe CommandEvent and stops the player from use "/w","/tell" and "/msg" command
//    @SubscribeEvent
//    public static void onCommandEvent(net.minecraftforge.event.CommandEvent event) {
//        ParseResults<CommandSource> parseResults = event.getParseResults();
//        if (!parseResults.getContext().getNodes().isEmpty()) {
////        if (parseResults.getContext().getRange().getStart() == 0) {
//            ParsedCommandNode<CommandSource> node = parseResults.getContext().getNodes().get(0);
//            Entity sender = parseResults.getContext().getSource().getEntity();
//            if (sender instanceof PlayerEntity)
//            {
//                String command = parseResults.getReader().getString();
//                IdlFramework.Log("using: "+command+"[END]");
//                if (command.startsWith("/w ") || command.startsWith("/tell ") || command.startsWith("/msg ") || command.startsWith("/tellraw ") ||
//                        command.startsWith("w ") || command.startsWith("tell ") || command.startsWith("msg ") || command.startsWith("tellraw ")
//                ) {
//                    event.setCanceled(true);
//                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (LivingEntity) sender, NO_CHAT_ALLOWED);
//                }
//            }
//        }
//    }

    @OnlyIn(value = Dist.CLIENT)
    @SubscribeEvent
    public static void onLoginOrOut(ClientChatReceivedEvent event)
    {
        ITextComponent component = event.getMessage();
        if (component instanceof TranslationTextComponent)
        {
            TranslationTextComponent translationTextComponent = (TranslationTextComponent) component;
            String key = translationTextComponent.getKey();
            if (key.equals("multiplayer.player.left") || key.equals("multiplayer.player.joined"))
            {
                PlayerEntity playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null && (playerEntity.isCreative() || playerEntity.isSpectator())) {
                }
                else {
                    event.setCanceled(true);
                }
            }
        }
    }

    @OnlyIn(value = Dist.CLIENT)
    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event)
    {
        Screen screen = event.getGui();
        if (screen instanceof ChatScreen)
        {
            if (!Minecraft.getInstance().player.isCreative())
            {
                ChatScreen chatScreen = (ChatScreen) screen;
                //private problem get solved but will cause NPE
//                chatScreen.commandSuggestions.setAllowSuggestions(false);
                event.setGui(new ModifiedChatScreen(((ChatScreen) screen).initial));
            }
        }
    }
}
