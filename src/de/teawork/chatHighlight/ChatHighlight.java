package de.teawork.chatHighlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import de.teawork.chatHighlight.chPlayerConfigData;
import de.teawork.chatHighlight.commandParser.chCommandParser;
import de.teawork.chatHighlight.var.chVarBase;
import de.teawork.chatHighlight.var.chVarColor;

public class ChatHighlight extends JavaPlugin implements Listener{

	public Map<String, chPlayerConfigData> playerConfigData = new HashMap<String, chPlayerConfigData>();
	private chCommandParser commandParser;
	//public String version = "1.2";	
	public Map<String,Boolean> supportPlugins = new HashMap<String, Boolean>();
	
	public chPlayerConfigData importData(chPlayerConfigData p, String uuid)
	{	
		if (!getConfig().contains("playerData."+uuid+".vars"))
			return p;
		chPlayerConfigData data = p;
		String base = "playerData."+uuid+".vars.";
		data.get("highlight/enable").setByString(getConfig().getString(base+"highlight")); 
		data.get("oddEven/enable").setByString(getConfig().getString(base+"oddEven")); 
		data.get("oddEven/colorOdd").setByString(getConfig().getString(base+"colorOdd")); 
		data.get("oddEven/colorEven").setByString(getConfig().getString(base+"colorEven")); 
		data.get("sound/enable").setByString(getConfig().getString(base+"playSound")); 
		data.get("highlight/color").setByString(getConfig().getString(base+"colorHighlight")); 
		getConfig().set("playerData."+uuid+".vars", null);
		return data;
	}
	
	public chPlayerConfigData loadData(String uuid, String name)
	{
		FileConfiguration config = this.getConfig();
		chPlayerConfigData data = new chPlayerConfigData(supportPlugins);
		//Check for existance
		if (config.contains("playerData."+uuid))
		{
			for (Entry<String,chVarBase> dat : data.paramList.entrySet())
			{
				dat.getValue().loadFromConfig(config, "playerData."+uuid+".param");
			}
			
			data=importData(data, uuid);
			
			//Load known aliases
			data.alias = (ArrayList<String>) config.getStringList("playerData."+uuid+".alias");
			data.buddy = (ArrayList<String>) config.getStringList("playerData."+uuid+".buddy");
			data.Name = name;
		} else
		{
			data.polify(name);
		}
		return data;
	}
	
	
	public void saveData(String uuid, chPlayerConfigData data)
	{
		for (Entry<String,chVarBase> dat : data.paramList.entrySet())
			dat.getValue().saveToConfig(getConfig(), "playerData."+uuid+".param");
		this.getConfig().set("playerData."+uuid+".alias", data.alias);
		this.getConfig().set("playerData."+uuid+".buddy", data.buddy);
		this.getConfig().set("playerData."+uuid+".name", data.Name);
		this.saveConfig();
	}
	
	@Override
	public void onEnable()
	{
		this.getServer().getPluginManager().registerEvents(this, this);
		commandParser = new chCommandParser(this);
		supportPlugins.put("Zw2Afk", getServer().getPluginManager().isPluginEnabled("Zw2Afk"));
		supportPlugins.put("ChatEx", getServer().getPluginManager().isPluginEnabled("ChatEx"));
		supportPlugins.put("CommandBook", getServer().getPluginManager().isPluginEnabled("CommandBook"));
		supportPlugins.put("DiscordSRV", getServer().getPluginManager().isPluginEnabled("DiscordSRV"));
		for (Entry<String,Boolean> e:supportPlugins.entrySet())
			if (e.getValue().booleanValue())
				this.getLogger().info("Enabled Support for "+e.getKey());
		this.getLogger().info("Done loading!");
	}
	
	@EventHandler
	public void onPlayerJoin(AsyncPlayerPreLoginEvent e)
	{
		//Load PlayerData
		playerConfigData.put(e.getUniqueId().toString(), loadData(e.getUniqueId().toString(), e.getName()));
		//Send notification to all Players who want one!
		for (Entry<String,chPlayerConfigData> data:playerConfigData.entrySet())
			if (!data.getValue().muted &&(data.getValue().getBool("joinNotification/enable")|| (data.getValue().getBool("leaveNotification/enableForBuddy") && data.getValue().buddy.contains(e.getUniqueId().toString()))))
			{
				//get the Player!
				Player player = this.getServer().getPlayer(UUID.fromString(data.getKey()));
				if (player==null)
					continue;
				if (supportPlugins.get("Zw2Afk").booleanValue())
				{
					if ((data.getValue().getBool("sound/muteWhenAfk")?!chHelper.isAfk(this, player.getUniqueId()):true))
						player.playSound(player.getLocation(), 
										 (Sound)data.getValue().get("joinNotification/sound").getValue(), 
										 (Float)data.getValue().get("joinNotification/loudness").getValue(), 
										 (Float)data.getValue().get("joinNotification/pitch").getValue());
				} else
					player.playSound(player.getLocation(), 
									 (Sound)data.getValue().get("joinNotification/sound").getValue(), 
									 (Float)data.getValue().get("joinNotification/loudness").getValue(),
									 (Float)data.getValue().get("joinNotification/pitch").getValue());
			}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		//Unload and save Playerdata
		saveData(e.getPlayer().getUniqueId().toString(), playerConfigData.get(e.getPlayer().getUniqueId().toString()));
		//Send notification to all Players who want one!
		for (Entry<String,chPlayerConfigData> data:playerConfigData.entrySet())
			if (!data.getValue().muted && (data.getValue().getBool("leaveNotification/enable")|| (data.getValue().getBool("leaveNotification/enableForBuddy") && data.getValue().buddy.contains(e.getPlayer().getUniqueId().toString()))))
			{
				//get the Player!
				Player player = this.getServer().getPlayer(UUID.fromString(data.getKey()));
				if (player==null)
					continue;
				if (supportPlugins.get("Zw2Afk").booleanValue())
				{
					if ((data.getValue().getBool("sound/muteWhenAfk")?!chHelper.isAfk(this, player.getUniqueId()):true))
						player.playSound(player.getLocation(), 
										 (Sound)data.getValue().get("leaveNotification/sound").getValue(), 
										 (Float)data.getValue().get("leaveNotification/loudness").getValue(),
										 (Float)data.getValue().get("leaveNotification/pitch").getValue());
				} else
					player.playSound(player.getLocation(), 
									 (Sound)data.getValue().get("leaveNotification/sound").getValue(), 
									 (Float)data.getValue().get("leaveNotification/loudness").getValue(), 
									 (Float)data.getValue().get("leaveNotification/pitch").getValue());
			}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{	
		//Send Message to each person individually:
		e.setCancelled(true);
		//log to console for compat:
		Bukkit.getLogger().info(e.getPlayer().getDisplayName()+": "+e.getMessage());
		//if DiscordSRV is present: send it to them
		if (supportPlugins.get("DiscordSRV")) {
			chHelper.sendToDiscordSRV(this, e.getPlayer(), e.getMessage());
		}
		
		chPlayerConfigData data;
		for (Player p : e.getRecipients())
		{
			data = playerConfigData.get(p.getUniqueId().toString());
			String message = e.getMessage();
			boolean mentioned = false;
			if (data.getBool("chat/enablePlainColor"))
			{
				message = ((chVarColor)data.get("chat/color")).getValue()+message;
			}
			if (!p.equals(e.getPlayer()))
			{
				//Find alias, store in mentioned
				for (String a: data.alias)
					mentioned = mentioned || message.toLowerCase().contains(a.toLowerCase());
				
				
				//don't mention when afk Plugin enabled AND option is set
				if (supportPlugins.get("Zw2Afk").booleanValue())
					if (data.getBool("highlight/enableWhenAfk"))
						mentioned = mentioned && !chHelper.isAfk(this, p.getUniqueId());
				//Play Sound when enabled
				if (!data.muted && (mentioned && (data.getBool("sound/enable")|| (data.getBool("sound/enableForBuddy") && data.buddy.contains(e.getPlayer().getUniqueId().toString())))))
					if (supportPlugins.get("Zw2Afk").booleanValue())
					{
						if ((data.getBool("sound/muteWhenAfk")?!chHelper.isAfk(this, p.getUniqueId()):true))
							p.playSound(p.getLocation(),
										(Sound)data.get("sound/sound").getValue(), 
										(Float)data.get("sound/loudness").getValue(),
										(Float)data.get("sound/pitch").getValue());
					} else
						p.playSound(p.getLocation(),
									(Sound)data.get("sound/sound").getValue(), 
									(Float)data.get("sound/loudness").getValue(),
									(Float)data.get("sound/pitch").getValue());
				//highlight when enabled
				if (mentioned && (data.getBool("highlight/enable")|| (data.getBool("highlight/enableForBuddy") && 
							data.buddy.contains(e.getPlayer().getName()))) && (!message.startsWith("@") || !supportPlugins.get("CommandBook").booleanValue()))
					message = data.get("highlight/color").getValue()+message;
				
				//OddEven highlight the messages if enabled
				if (data.getBool("oddEven/enable"))
				{
					message = (data.odd? ((chVarColor)data.get("oddEven/colorOdd")).getValue() : ((chVarColor)data.get("oddEven/colorEven")).getValue()) + message;
					data.odd = !data.odd;
				}
			} else {
				if (data.getBool("chat/highlightOwnMessage")) {
					message = ((chVarColor)data.get("chat/highlightOwnMessageColor")).getValue() + message;
				}
			}
			//send the message :)
			p.sendMessage(String.format(e.getFormat(), e.getPlayer().getDisplayName(), message));
		}
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return commandParser.parseCommand(sender, cmd, label, args);	
	}
	
}
