package net.cthuwork.simpleitembind.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class CommandReceiver implements TabExecutor
{
    private ArrayList<BaseCommand> registeredCommand = new ArrayList<BaseCommand>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args)
    {
        if(args.length == 0)
        {
            return false;
        }
        BaseCommand cbdCommand = getCommand(args[0]);
        if(cbdCommand != null)
        {
            if(sender.hasPermission(cbdCommand.getPermission()))
            {
                String[] cbdArgs = new String[args.length - 1];
                System.arraycopy(args, 1, cbdArgs, 0, args.length - 1);
                cbdCommand.runCommand(sender, args[0], cbdArgs);
            }
            else
            {
                sender.sendMessage(cbdCommand.getPermissionMessage());
            }
            return true;
        }
        return false;
    }
    public BaseCommand getCommand(String name)
    {
        for(BaseCommand command : registeredCommand)
        {
            if(command.commandName.equals(name.toLowerCase()) || command.aliases.contains(name.toLowerCase()))
            {
                return command;
            }
        }
        return null;
    }
    public BaseCommand getCommandByClass(Class<? extends BaseCommand> clazz)
    {
        for(BaseCommand command : registeredCommand)
        {
            if(command.getClass() == clazz)
            {
                return command;
            }
        }
        return null;
    }
    public ArrayList<BaseCommand> getCommands()
    {
        return registeredCommand;
    }
    public boolean registerCommand(BaseCommand command)
    {
        if(registeredCommand.contains(command))
        {
            return false;
        }
        else
        {
            registeredCommand.add(command);
            return true;
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if(args.length == 0)
        {
            return null;
        }
        else if(args.length == 1)
        {
            ArrayList<String> matchedCommand = new ArrayList<String>();
            for(BaseCommand cbdCommand : registeredCommand)
            {
                if(cbdCommand.commandName.startsWith(args[0]))
                {
                    matchedCommand.add(cbdCommand.commandName);
                }
                for(String cbdAlias : cbdCommand.aliases)
                {
                    if(cbdAlias.startsWith(args[0].toLowerCase()))
                    {
                        matchedCommand.add(cbdAlias);
                    }
                }
            }
            return matchedCommand;
        }
        else
        {
            BaseCommand cbdCommand = getCommand(args[0]);
            if(cbdCommand != null)
            {
                String[] cbdArgs = new String[args.length - 1];
                System.arraycopy(args, 1, cbdArgs, 0, args.length - 1);
                return cbdCommand.tabComplete(sender, args[0], cbdArgs);
            }
            return null;
        }
    }
}
