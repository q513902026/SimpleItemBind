package net.cthuwork.simpleitembind.command;

import java.util.List;

import net.cthuwork.simpleitembind.SimpleItemBind;
import net.cthuwork.simpleitembind.config.SimpleItemBindSetting;

import org.bukkit.command.CommandSender;

public class ReloadCommand extends BaseCommand
{
    public ReloadCommand()
    {
        super("reload","r");
        // TODO 自动生成的构造函数存根
    }
    @Override
    public void runCommand(CommandSender sender, String caseSensitiveName, String[] args)
    {
        if (args.length != 0)
        {
            sender.sendMessage("配置文件载入命令 不允许参数.");
            return;
        }
        SimpleItemBind.instance.reloadConfig();
        
        SimpleItemBind.instance.config = SimpleItemBind.instance.getConfig();
        SimpleItemBind.instance.setting = new SimpleItemBindSetting(SimpleItemBind.instance.config);
        sender.sendMessage("[GiftCode]: 配置文件Reload成功!");
    }
    @Override
    public List<String> tabComplete(CommandSender sender, String caseSensitiveName, String[] args)
    {
        // TODO 自动生成的方法存根
        return null;
    }
}
