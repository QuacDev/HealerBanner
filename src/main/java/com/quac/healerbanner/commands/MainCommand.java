package com.quac.healerbanner.commands;

import com.quac.healerbanner.Main;
import com.quac.healerbanner.Utils.ChatUtils;
import com.quac.healerbanner.Utils.TickDelay;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "healerbanner";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/healerbanner";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("hb");
        aliases.add("healerignorer");
        aliases.add("healer");
        return aliases;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0 || args[0].equalsIgnoreCase("config")) {
            Runnable runnable = () -> Main.setGui(Main.config.gui());
            new TickDelay(runnable, 1);
        }
    }
}
