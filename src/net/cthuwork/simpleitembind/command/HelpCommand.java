package net.cthuwork.simpleitembind.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cthuwork.simpleitembind.SimpleItemBind;

import org.bukkit.command.CommandSender;

public class HelpCommand extends BaseCommand
{
    public ArrayList<CommandGrop> commandGrops = new ArrayList<CommandGrop>();
    public HelpCommand()
    {
        super("help");
        commandGrops.add(new CommandGrop("Aliases", "命令别名表")
        {
            @Override
            public void printGropCommandsHelp(CommandSender sender, String[] gropArgs)
            {
                sender.sendMessage("§e---------§f SimpleItemBind帮助: 别名 §e--------------------");
                for(BaseCommand command : SimpleItemBind.instance.commandReceiver.getCommands())
                {
                    for(String alias : command.aliases)
                    {
                        sender.sendMessage("§6" + alias + ": §e别名属于§f " + command.commandName);
                    }
                }
            }
        });
    }
    @Override
    public void runCommand(CommandSender sender, String caseSensitiveName, String[] args)
    {
        if(args.length == 0)
        {
            ArrayList<BaseCommand> commands = SimpleItemBind.instance.commandReceiver.getCommands();
            sender.sendMessage("§e---------§f SimpleItemBind帮助: 索引 §e--------------------");
            for(CommandGrop commandGrop : commandGrops)
            {
                sender.sendMessage("§6" + commandGrop.gropName + ":§f " + commandGrop.gropDescription);
            }
            for(BaseCommand command : commands)
            {
                sender.sendMessage("§6" + command.commandName + ":§f " + command.getDescription());
            }
        }
        else if(args.length == 1)
        {
            CommandGrop commandGrop = getCommandGrop(args[0]);
            if(commandGrop != null)
            {
                String[] gropArgs = new String[args.length - 1];
                System.arraycopy(args, 1, gropArgs, 0, args.length - 1);
                commandGrop.printGropCommandsHelp(sender, gropArgs);
            }
            else
            {
                BaseCommand command = SimpleItemBind.instance.commandReceiver.getCommand(args[0]);
                if(command != null)
                {
                    if(command.isAlias(args[0]))
                    {
                        sender.sendMessage("§e---------§f SimpleItemBind帮助: " + args[0].toLowerCase() + " §e" + getEndFixString(args[0].toLowerCase().length()));
                        sender.sendMessage("§e别名属于§f " + command.commandName);
                    }
                    else
                    {
                        sender.sendMessage("§e---------§f SimpleItemBind帮助: " + command.commandName + " §e" + getEndFixString(command.commandName.length()));
                    }
                    sender.sendMessage("§6描述:§f " + command.getDescription());
                    sender.sendMessage("§6用法:§f " + command.getUsage());
                    if(command.aliases.size() > 0)
                    {
                        sender.sendMessage("§6别名:§f " + command.aliasesToString());
                    }
                }
                else
                {
                    if(!printPossibleMatches(sender, args[0]))
                    {
                        sender.sendMessage("§c没有关于 " + args[0] + " 的帮助文档");
                    }
                }
            }
        }
        else
        {
            PrintUsage(sender);
        }
    }
    @Override
    public List<String> tabComplete(CommandSender sender, String caseSensitiveName, String[] args)
    {
        if(args.length == 0)
        {
            return null;
        }
        else if(args.length == 1)
        {
            ArrayList<String> matchedCommand = new ArrayList<String>();
            for(BaseCommand command : SimpleItemBind.instance.commandReceiver.getCommands())
            {
                if(command.commandName.startsWith(args[0].toLowerCase()))
                {
                    matchedCommand.add(command.commandName);
                }
                for(String alias : command.aliases)
                {
                    if(alias.startsWith(args[0].toLowerCase()))
                    {
                        matchedCommand.add(alias);
                    }
                }
            }
            return matchedCommand;
        }
        else
        {
            return null;
        }
    }
    private String getEndFixString(int cutSize)
    {
        int size = 24 - cutSize;
        if(size < 0)
        {
            size = 0;
        }
        return "------------------------".substring(0, size);
    }
    private CommandGrop getCommandGrop(String gropName)
    {
        for(CommandGrop commandGrop : commandGrops)
        {
            if(commandGrop.gropName.equalsIgnoreCase(gropName))
            {
                return commandGrop;
            }
        }
        return null;
    }
    private boolean printPossibleMatches(CommandSender sender, String searchString)
    {
        int maxDistance = (searchString.length() / 5) + 3;
        boolean havePossibleMatches = false;
        
        for(CommandGrop commandGrop : commandGrops)
        {
            String commandName = commandGrop.gropName;
            
            if (commandName.length() < searchString.length())
            {
                continue;
            }
            if (Character.toLowerCase(commandName.charAt(0)) != Character.toLowerCase(searchString.charAt(0)))
            {
                continue;
            }
            if (damerauLevenshteinDistance(searchString, commandName.substring(0, searchString.length())) < maxDistance)
            {
                if(!havePossibleMatches)
                {
                    havePossibleMatches = true;
                    sender.sendMessage("§e---------§f SimpleItemBind帮助: 搜索 §e--------------------");
                    sender.sendMessage("搜索匹配: " + searchString);
                }
                sender.sendMessage("§6" + commandName + ":§f " + commandGrop.gropDescription);
            }
        }
        for(BaseCommand command : SimpleItemBind.instance.commandReceiver.getCommands())
        {
            String commandName = command.commandName;
            
            if (commandName.length() < searchString.length())
            {
                continue;
            }
            if (Character.toLowerCase(commandName.charAt(0)) != Character.toLowerCase(searchString.charAt(0)))
            {
                continue;
            }
            if (damerauLevenshteinDistance(searchString, commandName.substring(0, searchString.length())) < maxDistance)
            {
                if(!havePossibleMatches)
                {
                    havePossibleMatches = true;
                    sender.sendMessage("§e---------§f SimpleItemBind帮助: 搜索 §e--------------------");
                    sender.sendMessage("搜索匹配: " + searchString);
                }
                sender.sendMessage("§6" + commandName + ":§f " + command.getDescription());
            }
            for(String alias : command.aliases)
            {
                if (alias.length() < searchString.length())
                {
                    continue;
                }
                if (Character.toLowerCase(alias.charAt(0)) != Character.toLowerCase(searchString.charAt(0)))
                {
                    continue;
                }
                if (damerauLevenshteinDistance(searchString, alias.substring(0, searchString.length())) < maxDistance)
                {
                    if(!havePossibleMatches)
                    {
                        havePossibleMatches = true;
                        sender.sendMessage("§e---------§f SimpleItemBind帮助: 搜索 §e--------------------");
                        sender.sendMessage("搜索匹配: " + searchString);
                    }
                    sender.sendMessage("§6" + alias + ": §e别名属于§f " + command.commandName);
                }
            }
        }
        return havePossibleMatches;
    }
    private int damerauLevenshteinDistance(String s1, String s2)
    {
        if (s1 == null && s2 == null)
        {
            return 0;
        }
        if (s1 != null && s2 == null)
        {
            return s1.length();
        }
        if (s1 == null && s2 != null)
        {
            return s2.length();
        }
        int s1Len = s1.length();
        int s2Len = s2.length();
        int[][] H = new int[s1Len + 2][s2Len + 2];
        int INF = s1Len + s2Len;
        H[0][0] = INF;
        for (int i = 0; i <= s1Len; i++)
        {
            H[i + 1][1] = i;
            H[i + 1][0] = INF;
        }
        for (int j = 0; j <= s2Len; j++)
        {
            H[1][j + 1] = j;
            H[0][j + 1] = INF;
        }
        Map<Character, Integer> sd = new HashMap<Character, Integer>();
        for (char Letter : (s1 + s2).toCharArray())
        {
            if (!sd.containsKey(Letter))
            {
                sd.put(Letter, 0);
            }
        }
        for (int i = 1; i <= s1Len; i++)
        {
            int DB = 0;
            for (int j = 1; j <= s2Len; j++)
            {
                int i1 = sd.get(s2.charAt(j - 1));
                int j1 = DB;
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                {
                    H[i + 1][j + 1] = H[i][j];
                    DB = j;
                }
                else
                {
                    H[i + 1][j + 1] = Math.min(H[i][j], Math.min(H[i + 1][j], H[i][j + 1])) + 1;
                }
                H[i + 1][j + 1] = Math.min(H[i + 1][j + 1], H[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1));
            }
            sd.put(s1.charAt(i - 1), i);
        }
        return H[s1Len + 1][s2Len + 1];
    }
    public abstract class CommandGrop
    {
        public String gropName = "";
        public String gropDescription = "";
        
        public CommandGrop(String gropName, String gropDescription)
        {
            this.gropName = gropName;
            this.gropDescription = gropDescription;
        }
        
        public abstract void printGropCommandsHelp(CommandSender sender, String[] gropArgs);
    }
}
