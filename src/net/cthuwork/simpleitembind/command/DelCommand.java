package net.cthuwork.simpleitembind.command;

import java.util.List;

import net.cthuwork.simpleitembind.SimpleItemBind;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DelCommand extends BaseCommand
{
    public DelCommand()
    {
        super("delete", "del");
        // TODO 自动生成的构造函数存根
    }
    @Override
    public void runCommand(CommandSender sender, String caseSensitiveName, String[] args)
    {
        if(args.length != 0 )
        {
            PrintUsage(sender);
            return;
        }
        if(!(sender instanceof Player))
        {
            PrintUsage(sender);
            return;
        }
        Player aPlayer = (Player)sender;
        ItemStack itemStack = aPlayer.getItemInHand();
        aPlayer.getInventory().remove(itemStack);
        aPlayer.sendMessage(SimpleItemBind.getLogLevel()+": 物品" + itemStack.getItemMeta().getDisplayName()+"已被消除!");
        
    }
    @Override
    public List<String> tabComplete(CommandSender sender, String caseSensitiveName, String[] args)
    {
        // TODO 自动生成的方法存根
        return null;
    }
}
