package com.quac.healerbanner;

import com.quac.healerbanner.Utils.ChatUtils;
import com.quac.healerbanner.Utils.TickDelay;
import com.quac.healerbanner.commands.MainCommand;
import com.quac.healerbanner.config.Config;
import gg.essential.vigilance.Vigilance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Objects;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "HealerBanner";
    public static final String VERSION = "1.0.9.2";
    public static Config config;
    private static GuiScreen guiToOpen;

    public static void setGui(GuiScreen gui) {
        guiToOpen = gui;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Vigilance.initialize();
        config = new Config(new File("./config/config.toml"));
        config.preload();

        ClientCommandHandler.instance.registerCommand(new MainCommand());

        MinecraftForge.EVENT_BUS.register(new Main());
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent e) {
        if(Config.sendHelpMessage) {
            if(e.entity.equals(Minecraft.getMinecraft().thePlayer)) {
                ChatUtils.addMsg("§aYou are using HealerBanner, type '/hb' for more info.");
            }
        }
    }

    @SubscribeEvent
    public void onReceiveMessage(ClientChatReceivedEvent e) {
        String msg = e.message.getFormattedText();
        if(Config.enabled) {
            //System.out.println(msg);
            if(msg.startsWith("§dDungeon Finder §r§f> §r") && msg.contains("joined the dungeon group")) {
                String nameWithMessage = msg.substring(27);
                //System.out.println(nameWithMessage);
                int index = nameWithMessage.indexOf(" ");
                String name = (String) nameWithMessage.subSequence(0, index); // Just The Name
                String fullMessageWithoutName = nameWithMessage.substring(index); // "Joined the dungeon group" including LEVEL

                String classMessage = (String) fullMessageWithoutName.subSequence(fullMessageWithoutName.indexOf("("), fullMessageWithoutName.indexOf(")"));
                String classMessageFixed1 = classMessage.substring(5);
                System.out.println("Test " + classMessageFixed1);
                String classFinal = (String) classMessageFixed1.subSequence(0, classMessageFixed1.indexOf("§")); // Final Class Info
                System.out.println("Test2 " + classFinal);
                String levelText = classFinal.substring(classFinal.indexOf("Level "));
                int lvl = Integer.parseInt(levelText.substring(levelText.indexOf(" ")).replaceAll(" ", ""));
                System.out.println("Level " + lvl);

                //System.out.println("Messages N/C: " + name + " | " + classFinal);

                if(classFinal.contains("Healer")) {
                    boolean shouldKick = false;
                    if(Config.maxLevel == 0 ) {
                        shouldKick = true;
                    } else if(lvl <= Config.maxLevel) {
                        shouldKick = true;
                    }
                    if(shouldKick) {
                        if(!Objects.equals(Config.kickMessage, " ")) {
                            Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc " + Config.kickMessage);
                        }
                        Runnable runnable = () -> {
                            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p kick " + name);
                            if(Config.ignore) {
                                Minecraft.getMinecraft().thePlayer.sendChatMessage("/ignore add " + name);
                            }
                        };
                        new TickDelay(runnable, 20);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) throws MalformedURLException {
        if(!event.phase.equals(TickEvent.Phase.START)) return;

        if (guiToOpen != null) {
            try {
                System.out.println("Opening GUI...");
                Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
            } catch (Exception e) {
                e.printStackTrace();
                ChatUtils.addMsg("&cError while opening GUI. Check log for more details");
            }
            guiToOpen = null;
        }
    }
}
