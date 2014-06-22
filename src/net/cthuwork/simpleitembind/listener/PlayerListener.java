package net.cthuwork.simpleitembind.listener;

import java.util.List;

import net.cthuwork.simpleitembind.SimpleItemBind;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event)
    {
        final Item item = event.getItem();
        ItemStack itemstack = item.getItemStack();
        boolean itemIsBind = SimpleItemBind.instance.setting.itemIsPickupBind(itemstack, item.getWorld());
        if (!(itemIsBind))
        {
            return;
        }
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.getLore().add(SimpleItemBind.instance.setting.getItemBindLabel());
        event.getItem().getItemStack().setItemMeta(itemMeta);
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerEquipItem(InventoryClickEvent event)
    {
        if (!(event.getClick() == ClickType.DOUBLE_CLICK))
        {
            return;
        }
        ItemStack itemstack = event.getCurrentItem();
        boolean itemIsBind = SimpleItemBind.instance.setting.itemIsEquipBind(itemstack, event.getWhoClicked().getWorld());
        if (!(itemIsBind))
        {
            return;
        }
        if (!(event.getWhoClicked().getType() == EntityType.PLAYER))
        {
            return;
        }
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.getLore().add(SimpleItemBind.instance.setting.getItemBindLabel());
        Player aPlayer = (Player) event.getWhoClicked();
        aPlayer.sendMessage(SimpleItemBind.getLogLevel() + ": 物品" + itemMeta.getDisplayName() + "已经和您绑定!");
        itemMeta.getLore().add("拥有者 :" + aPlayer.getDisplayName());
        event.getCurrentItem().setItemMeta(itemMeta);
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent event)
    {
        ItemStack itemstack = event.getItemDrop().getItemStack();
        boolean itemIsBind = SimpleItemBind.instance.setting.itemIsBind(itemstack);
        if (!(itemIsBind || itemstack.getItemMeta().getLore().contains(SimpleItemBind.instance.setting.getItemBindLabel())))
        {
            return;
        }
        event.setCancelled(true);
        Player aPlayer = event.getPlayer();
        aPlayer.sendMessage(SimpleItemBind.getLogLevel() + ": 你不能丢弃已经被绑定的物品 ,请手持被绑定物品使用/sib delete 来消除此道具");
    }
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        List<ItemStack> itemStackList = event.getDrops();
        List<ItemStack> tempItemStackList = SimpleItemBind.instance.setting.hasBindItem(itemStackList);
        for (ItemStack item : tempItemStackList)
        {
            event.getDrops().remove(item);
        }
    }
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemDragInventory(InventoryDragEvent event)
    {
        if (!(event.getWhoClicked().getType() == EntityType.PLAYER))
        {
            return;
        }
        Player aPlayer = (Player) event.getWhoClicked();
        ItemStack itemstack = event.getCursor();
        boolean itemIsBind = SimpleItemBind.instance.setting.itemIsBind(itemstack);
        if (!(itemIsBind || itemstack.getItemMeta().getLore().contains(SimpleItemBind.instance.setting.getItemBindLabel())))
        {
            return;
        }
        if (itemstack.getItemMeta().getLore().contains("拥有者 :" + aPlayer.getDisplayName()))
        {
            return;
        }
        event.setCancelled(true);
        aPlayer.sendMessage(SimpleItemBind.getLogLevel() + ": 物品" + itemstack.getItemMeta().getDisplayName() + "已经绑定!");
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerPickupBindItem(PlayerPickupItemEvent event)
    {
        final Item item = event.getItem();
        ItemStack itemstack = item.getItemStack();
        Player aPlayer = event.getPlayer();
        boolean itemIsBind = SimpleItemBind.instance.setting.itemIsBind(itemstack);
        if (!(itemIsBind || itemstack.getItemMeta().getLore().contains(SimpleItemBind.instance.setting.getItemBindLabel())))
        {
            return;
        }
        if (itemstack.getItemMeta().getLore().contains("拥有者 :" + aPlayer.getDisplayName()))
        {
            return;
        }
        event.setCancelled(true);
        aPlayer.sendMessage(SimpleItemBind.getLogLevel() + ": 物品" + itemstack.getItemMeta().getDisplayName() + "已经绑定!");
    }
}
