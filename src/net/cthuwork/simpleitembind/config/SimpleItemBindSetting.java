package net.cthuwork.simpleitembind.config;

import java.util.ArrayList;
import java.util.List;

import net.cthuwork.simpleitembind.SimpleItemBind;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class SimpleItemBindSetting extends PluginSetting
{
    public ConfigurationSection commandSetting;
    public ConfigurationSection commonSetting;
    
    public SimpleItemBindSetting(FileConfiguration config)
    {
        super(config);
        commandSetting = config.getConfigurationSection(SimpleItemBind.NAME + ".commandSetting");
        commonSetting = config.getConfigurationSection(SimpleItemBind.NAME + ".commonSetting");
    }
    @Override
    public void save()
    {
        SimpleItemBind.instance.saveConfig();
    }
    public boolean itemIsPickupBind(ItemStack itemstack, World world)
    {
        List<?> itemListp = commonSetting.getList("bindItemList|Pickup");
        List<?> worldList = commonSetting.getList("bindWorld");
        if (itemListp == null)
        {
            return false;
        }
        if (itemListp.isEmpty())
        {
            return false;
        }
        if (!(worldList.contains(world.getName())))
        {
            return false;
        }
        if (!(itemListp.contains(itemstack.getType().toString())))
        {
            return false;
        }
        return true;
    }
    public boolean itemIsEquipBind(ItemStack itemstack, World world)
    {
        List<?> itemListe = commonSetting.getList("bindItemList|Equip");
        List<?> worldList = commonSetting.getList("bindWorld");
        if (itemListe == null)
        {
            return false;
        }
        if (itemListe.isEmpty())
        {
            return false;
        }
        if (!(worldList.contains(world.getName())))
        {
            return false;
        }
        if (!(itemListe.contains(itemstack.getType().toString())))
        {
            return false;
        }
        return true;
    }
    public boolean itemIsBind(ItemStack item)
    {
        List<?> itemListe = commonSetting.getList("bindItemList|Equip");
        List<?> itemListp = commonSetting.getList("bindItemList|Pickup");
        String key = item.getType().toString();
        if (!(itemListe.contains(key) || itemListp.contains(key)))
        {
            return false;
        }
        return true;
    }
    public String getItemBindLabel()
    {
        return commonSetting.getString("Label", "已被绑定");
    }
    public List<ItemStack> hasBindItem(List<ItemStack> itemStackList)
    {
        List<ItemStack> temp = new ArrayList();
        for (ItemStack item : itemStackList)
        {
            if(item.getItemMeta().getLore().contains(getItemBindLabel()))
            {
                temp.add(item);
            }
        }
        return temp;
    }
    public String getMainCommandUsage()
    {
        return commandSetting.getString("usage", "/<SimpleItemBind|SIB> [子命令]");
    }
    public String getMainCommandDescription()
    {
        return commandSetting.getString("description", "使用此命令来操作SimpleItemBind插件");
    }
    public String getCommandUsage(String commandName)
    {
        return commandSetting.getString(commandName + ".usage", "/<SimpleItemBind|SIB> <" + commandName + "> [参数[ ...]]");
    }
    public String getCommandDescription(String commandName)
    {
        return commandSetting.getString(commandName + ".description", "SimpleItemBind插件" + commandName + "指令");
    }
    public String getCommandPermission(String commandName)
    {
        return commandSetting.getString(commandName + ".permission", "");
    }
    public String getCommandPermissionMessage(String commandName)
    {
        return commandSetting.getString(commandName + ".permission-message", "");
    }
}
