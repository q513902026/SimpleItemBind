package net.cthuwork.simpleitembind.command;

import java.util.ArrayList;
import java.util.List;

import net.cthuwork.simpleitembind.SimpleItemBind;

import org.bukkit.command.CommandSender;

public abstract class BaseCommand
{
    public String commandName = "";
    public ArrayList<String> aliases = new ArrayList<String>();
    
    public BaseCommand(String commandName, String... aliases)
    {
        this.commandName = commandName.toLowerCase();
        for(String alias : aliases)
        {
           this.aliases.add(alias.toLowerCase());
        }
    }
    
    public abstract void runCommand(CommandSender sender, String caseSensitiveName, String[] args);
    
    public abstract List<String> tabComplete(CommandSender sender, String caseSensitiveName, String[] args);
    
    public String getDescription()
    {
        return SimpleItemBind.instance.setting.getCommandDescription(this.commandName);
    }
    public String getUsage()
    {
        return SimpleItemBind.instance.setting.getCommandUsage(this.commandName);
    }
    public String getPermission()
    {
        return SimpleItemBind.instance.setting.getCommandPermission(this.commandName);
    }
    public String getPermissionMessage()
    {
        return SimpleItemBind.instance.setting.getCommandPermissionMessage(this.commandName);
    }
    
    public void PrintDescription(CommandSender sender)
    {
        sender.sendMessage("§f描述: " + getDescription());
    }
    public void PrintUsage(CommandSender sender)
    {
        sender.sendMessage("§c用法: " + getUsage());
    }
    
    public boolean isAlias(String name)
    {
        if(aliases.contains(name.toLowerCase()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public String aliasesToString()
    {
        StringBuffer aliasesBuffer = new StringBuffer();
        boolean isFirstAlias = true;
        for(String alias : aliases)
        {
            if(isFirstAlias)
            {
                aliasesBuffer.append(alias);
                isFirstAlias = false;
            }
            else
            {
                aliasesBuffer.append(", ");
                aliasesBuffer.append(alias);
            }
        }
        return aliasesBuffer.toString();
    }
}