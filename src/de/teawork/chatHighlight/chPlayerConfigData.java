package de.teawork.chatHighlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Sound;

import de.teawork.chatHighlight.var.chVarBase;
import de.teawork.chatHighlight.var.chVarBoolean;
import de.teawork.chatHighlight.var.chVarColor;
import de.teawork.chatHighlight.var.chVarFloat;
import de.teawork.chatHighlight.var.chVarGroup;
import de.teawork.chatHighlight.var.chVarSound;
import net.md_5.bungee.api.ChatColor;

public class chPlayerConfigData {
	
	public Map<String,chVarBase> paramList;
	public ArrayList<String> alias;
	public ArrayList<String> buddy;
	public String Name;
	public boolean odd;
	public boolean copyConfirm;
	public boolean muted;
	public Map<String,Boolean> supportedPlugins;

	public chPlayerConfigData(Map<String,Boolean> sP)
	{
		supportedPlugins = sP;
		freshData();
		alias = new ArrayList<String>();
		buddy = new ArrayList<String>();
		copyConfirm=false;
		muted=false;
	}
	
	public void polify(String name)
	{
		freshData();
		//Add own Name as default alias
		this.Name = name;
		alias.clear();
		alias.add(name);
		buddy.clear();
	}
	
	private void freshData()
	{
		chVarGroup publicGroup = new chVarGroup(this, "settings", null);
		chVarGroup actGroup;
		paramList = new HashMap<String,chVarBase>();
		//highlight
		actGroup=new chVarGroup(this, "chat", publicGroup);
		actGroup.getValue().put("enablePlainColor", new chVarBoolean(this, "enablePlainColor", actGroup, Boolean.FALSE));
		actGroup.getValue().put("color", new chVarColor(this, "color", actGroup));
		actGroup.getValue().put("highlightOwnMessage", new chVarBoolean(this, "highlightOwnMessage", actGroup, Boolean.FALSE));
		actGroup.getValue().put("highlightOwnMessageColor", new chVarColor(this, "highlightOwnMessageColor", actGroup, ChatColor.AQUA));
		publicGroup.getValue().put("chat", actGroup);
		
		actGroup=new chVarGroup(this, "highlight", publicGroup);
		actGroup.getValue().put("enable", new chVarBoolean(this, "enable", actGroup, Boolean.TRUE));
		actGroup.getValue().put("enableForBuddy", new chVarBoolean(this, "enableForBuddy", actGroup, Boolean.TRUE));
		if (supportedPlugins.get("Zw2Afk").booleanValue())
			actGroup.getValue().put("enableWhenAfk", new chVarBoolean(this, "enableWhenAfk", actGroup, Boolean.FALSE));
		actGroup.getValue().put("color", new chVarColor(this, "color", actGroup, ChatColor.RED));
		publicGroup.getValue().put("highlight", actGroup);
		//Sound
		actGroup=new chVarGroup(this, "sound", publicGroup);
		actGroup.getValue().put("enable", new chVarBoolean(this, "enable", actGroup, Boolean.FALSE));
		actGroup.getValue().put("enableForBuddy", new chVarBoolean(this, "enableForBuddy", actGroup, Boolean.TRUE));
		actGroup.getValue().put("sound", new chVarSound(this, "sound", actGroup, Sound.BLOCK_ANVIL_USE));
		actGroup.getValue().put("loudness", new chVarFloat(this, "loudness", actGroup, 1.0f));
		actGroup.getValue().put("pitch", new chVarFloat(this, "pitch", actGroup, 4.0f));
		if (supportedPlugins.get("Zw2Afk").booleanValue())
			actGroup.getValue().put("muteWhenAfk", new chVarBoolean(this, "muteWhenAfk", actGroup, Boolean.FALSE));
		publicGroup.getValue().put("sound", actGroup);
		//oddEven
		actGroup=new chVarGroup(this, "oddEven", publicGroup);
		actGroup.getValue().put("enable", new chVarBoolean(this, "enable", actGroup, Boolean.FALSE));
		actGroup.getValue().put("colorOdd", new chVarColor(this, "colorOdd", actGroup, ChatColor.WHITE));
		actGroup.getValue().put("colorEven", new chVarColor(this, "colorEven", actGroup, ChatColor.GRAY));
		publicGroup.getValue().put("oddEven", actGroup);
		//join Notification
		actGroup=new chVarGroup(this, "joinNotification", publicGroup);
		actGroup.getValue().put("enable", new chVarBoolean(this, "enable", actGroup, false));
		actGroup.getValue().put("enableForBuddy", new chVarBoolean(this, "enableForBuddy", actGroup, Boolean.TRUE));
		actGroup.getValue().put("sound", new chVarSound(this, "sound", actGroup, Sound.BLOCK_ANVIL_LAND));
		actGroup.getValue().put("loudness", new chVarFloat(this, "loudness", actGroup, 1.0f));
		actGroup.getValue().put("pitch", new chVarFloat(this, "pitch", actGroup, 10.0f));
		publicGroup.getValue().put("joinNotification", actGroup);
		//leave Notification
		actGroup=new chVarGroup(this, "leaveNotification", publicGroup);
		actGroup.getValue().put("enable", new chVarBoolean(this, "enable", actGroup, false));
		actGroup.getValue().put("enableForBuddy", new chVarBoolean(this, "enableForBuddy", actGroup, Boolean.TRUE));
		actGroup.getValue().put("sound", new chVarSound(this, "sound", actGroup, Sound.BLOCK_ANVIL_LAND));
		actGroup.getValue().put("loudness", new chVarFloat(this, "loudness", actGroup, 1.0f));
		actGroup.getValue().put("pitch", new chVarFloat(this, "pitch", actGroup, 5.0f));
		publicGroup.getValue().put("leaveNotification", actGroup);
		paramList.put("settings", publicGroup);
	}
	
	public chVarBase get(String path)
	{
		return get(path.split("/"));
	}
	
	public chVarBase get(String[] path)
	{
		chVarBase b = paramList.get("settings");
		for (String s:path)
		{
			if (!(b instanceof chVarGroup))
					return null;
			b=((chVarGroup)b).getValue().get(s);
		}
		return b;
	}
	
	public boolean getBool(String path)
	{
		return ((Boolean)get(path).getValue()).booleanValue();
	}
	
}
