package net.cthuwork.simpleitembind;

import net.cthuwork.simpleitembind.command.CommandReceiver;
import net.cthuwork.simpleitembind.command.DelCommand;
import net.cthuwork.simpleitembind.command.HelpCommand;
import net.cthuwork.simpleitembind.command.ReloadCommand;
import net.cthuwork.simpleitembind.config.SimpleItemBindSetting;
import net.cthuwork.simpleitembind.listener.PlayerListener;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleItemBind extends JavaPlugin
{
    public static SimpleItemBind instance;
    public static final String NAME = "SimpleItemBind";
    
    public PlayerListener playerListener;
    public FileConfiguration config;
    public SimpleItemBindSetting setting;
    public PluginCommand mainCommand;
    public CommandReceiver commandReceiver;
    
    @Override
    public void onEnable()
    {
        instance = this;
        
        this.saveDefaultConfig();
        
        config = this.getConfig();
        playerListener = new PlayerListener();
        setting = new SimpleItemBindSetting(config);
        
        mainCommand = this.getCommand("GiftBox");
        commandReceiver = new CommandReceiver();
        mainCommand.setDescription(setting.getMainCommandDescription());
        mainCommand.setExecutor(commandReceiver);
        mainCommand.setUsage(setting.getMainCommandUsage());
        
        commandReceiver.registerCommand(new HelpCommand());
        commandReceiver.registerCommand(new DelCommand());
        commandReceiver.registerCommand(new ReloadCommand());
        
        
        this.getServer().getPluginManager().registerEvents(playerListener, this);
    }
    @Override
    public void onDisable()
    {
        instance = null;
    }
    public static String getLogLevel()
    {
        return "[" + SimpleItemBind.NAME + "]";
    }
}
